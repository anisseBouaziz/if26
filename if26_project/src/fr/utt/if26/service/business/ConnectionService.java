package fr.utt.if26.service.business;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import fr.utt.if26.activity.ConnectionActivity;
import fr.utt.if26.activity.HomePageActivity;
import fr.utt.if26.fragment.InfoDialogFragment;
import fr.utt.if26.model.Contact;
import fr.utt.if26.model.User;
import fr.utt.if26.parser.ContactParser;
import fr.utt.if26.persistence.MessengerDBHelper;
import fr.utt.if26.service.web.IConnectionService;
import fr.utt.if26.service.web.IRetrieveContactsService;
import fr.utt.if26.service.web.WebService;
import fr.utt.if26.service.web.WebServiceConnection;
import fr.utt.if26.service.web.WebServiceContacts;
import fr.utt.if26.util.InternetConnectionVerificator;

public class ConnectionService implements IConnectionService,
		IRetrieveContactsService {

	private Activity currentActivity;
	private User user;
	private static final String SERVICE_URL = "http://192.168.56.1/messenger/"; //$NON-NLS-1$
	private MessengerDBHelper sqlHelper;

	public ConnectionService(Activity currentActivity) {
		this.currentActivity = currentActivity;
		sqlHelper = new MessengerDBHelper(
				currentActivity.getApplicationContext());
	}
	
	public ConnectionService(Activity currentActivity, User user) {
		super();
		this.currentActivity = currentActivity;
		sqlHelper = new MessengerDBHelper(
				currentActivity.getApplicationContext());
		this.user=user;
	}


	/**
	 * Verify if the pseudo and the password are correct
	 */
	public void connectUser(String email, String password) {
		if (InternetConnectionVerificator.isNetworkAvailable(currentActivity)) {
			String urlRequest = SERVICE_URL + "login.php?email=" + email
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
				((ConnectionActivity) currentActivity)
						.displayIncorrectLoginErrorMessage();
			} else {
				((ConnectionActivity) currentActivity)
						.hideIncorrectLoginErrorMessage();
				String token = result.getString("token"); //$NON-NLS-1$
				user = new User(token);
				sqlHelper.persistUser(user);
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
			user.setContactList(sqlHelper.retrieveContactsInDB());
			goToHomePageActivity();
		}
	}

	private void retrieveContactsFromServer() {
		WebService webService = new WebServiceContacts(this);
		String urlRequest = SERVICE_URL
				+ "contacts.php?token=" + user.getToken(); //$NON-NLS-1$
		webService.execute(urlRequest);
	}

	/**
	 * Method called by the WebService after the server returned the wanted JSON
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
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void goToHomePageActivity() {
		Intent intent = new Intent(currentActivity, HomePageActivity.class);
		intent.putExtra("user", user);
		currentActivity.startActivity(intent);
	}

}
