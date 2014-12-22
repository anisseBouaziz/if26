package fr.utt.if26.service.business;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import fr.utt.if26.activity.ConnectionActivity;
import fr.utt.if26.activity.InscriptionActivity;
import fr.utt.if26.activity.HomePageActivity;
import fr.utt.if26.fragment.InfoDialogFragment;
import fr.utt.if26.model.Contact;
import fr.utt.if26.model.User;
import fr.utt.if26.parser.ContactParser;
import fr.utt.if26.persistence.MessengerDBContract;
import fr.utt.if26.persistence.MessengerDBHelper;
import fr.utt.if26.service.IConnectionService;
import fr.utt.if26.service.IRetrieveContactsService;
import fr.utt.if26.service.web.WebService;
import fr.utt.if26.service.web.WebServiceConnection;
import fr.utt.if26.service.web.WebServiceContacts;
import fr.utt.if26.util.InternetConnectionVerificator;

public class InscriptionService implements IConnectionService,
	IRetrieveContactsService {

		private Activity currentActivity;
		private User user;
		private static final String SERVICE_URL = "http://192.168.0.20/messenger/"; //$NON-NLS-1$
		private MessengerDBHelper sqlHelper;
		
		public InscriptionService(Activity currentActivity) {
				this.currentActivity = currentActivity;
				sqlHelper = new MessengerDBHelper(
				currentActivity.getApplicationContext());
		}
		
		public InscriptionService(Activity currentActivity, User user) {
			super();
			this.currentActivity = currentActivity;
			sqlHelper = new MessengerDBHelper(
					currentActivity.getApplicationContext());
			this.user=user;
		}
		
		
		/**
		* Verify if the pseudo and the password are correct
		*/
		public void connectUser(String pseudo, String email, String password) {
			if (InternetConnectionVerificator.isNetworkAvailable(currentActivity)) {
				String urlRequest = SERVICE_URL + "inscription.php?pseudo="+pseudo+"&email=" + email
						+ "&password=" + password;
				WebService webService = new WebServiceConnection(this);
				webService.execute(urlRequest);
			} else {
				new InfoDialogFragment("Internet connection unavailable").show(
						currentActivity.getFragmentManager(),
						"Internet connection unavailable");
			}
		}
		
		/**
		* If the combination email/password given by the user is correct then
		* create the user with the token returned by the server
		*/
		public void instanciateUser(JSONObject result) {
			try {
				if (result.getBoolean("error")) { //$NON-NLS-1$
					((InscriptionActivity) currentActivity)
							.displayIncorrectInscriptionErrorMessage("Inscription failed");
				} else {
					((InscriptionActivity) currentActivity).hideIncorrectInscriptionErrorMessage("Inscription Succeed");
					String token = result.getString("token"); //$NON-NLS-1$
					user = new User(token);
				//	sqlHelper.persistUser(user); jai commenter comme ca c plus facile a tester, pas besoin de dessinstaller lappli haha
					initializeUserInformations();
			
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		/**
		* After getting the token, retrieve all informations about the user (contacts ...)
		*/
		public void initializeUserInformations() {
			if (InternetConnectionVerificator.isNetworkAvailable(currentActivity)) {
				retrieveContactsFromServer();
			} else { // If offline mode then get info from the local database
			//	user.setContactList(sqlHelper.retrieveContactsInDB());
				goToHomePageActivity();
			}
		}
		
		private void retrieveContactsFromServer() {
			sqlHelper.getWritableDatabase().delete(MessengerDBContract.ContactTable.TABLE_NAME, null, null);
			sqlHelper.getWritableDatabase().delete(MessengerDBContract.MessageTable.TABLE_NAME, null, null);
			WebService webService = new WebServiceContacts(this);
			String urlRequest = SERVICE_URL
					+ "contacts.php?token=" + user.getToken(); //$NON-NLS-1$
			webService.execute(urlRequest);
		}
		
		/**
		* Method called by the WebService after the server returned the wanted JSON contening contacts
		*/
		@Override
		public void retrieveContacts(JSONObject result) {
			try {
				if (!result.getBoolean("error")) {
					List<Contact> contactList = ContactParser
							.jsonToListContact(result);
					user.setContactList(contactList);
					sqlHelper.persistOrUpdateContacts(contactList);
					goToHomePageActivity();
				}else{
					sqlHelper.getWritableDatabase().delete(MessengerDBContract.UserTable.TABLE_NAME, null, null);
					goToConnectionActivity();
				}
			
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		private void goToConnectionActivity() {
			Intent intent = new Intent(currentActivity, ConnectionActivity.class);
			currentActivity.startActivity(intent);		
		}
		
		private void goToHomePageActivity() {
			Intent intent = new Intent(currentActivity, HomePageActivity.class);
			intent.putExtra("user", user);
			currentActivity.startActivity(intent);
		}
		
}


