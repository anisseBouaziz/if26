package fr.utt.if26.service.business;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import fr.utt.if26.activity.AddContactActivity;
import fr.utt.if26.activity.HomePageActivity;
import fr.utt.if26.fragment.InfoDialogFragment;
import fr.utt.if26.model.Contact;
import fr.utt.if26.model.User;
import fr.utt.if26.parser.ContactParser;
import fr.utt.if26.persistence.MessengerDBHelper;
import fr.utt.if26.service.IAddFriendService;
import fr.utt.if26.service.web.WebService;
import fr.utt.if26.service.web.WebServiceAddFriend;
import fr.utt.if26.util.InternetConnectionVerificator;

public class AddContactService implements IAddFriendService {
	private Activity currentActivity;
	private User user;
	private static final String SERVICE_URL = "http://192.168.0.20/messenger/"; //$NON-NLS-1$
	private MessengerDBHelper sqlHelper;
	
	public AddContactService(Activity currentActivity) {
			this.currentActivity = currentActivity;
			sqlHelper = new MessengerDBHelper(
			currentActivity.getApplicationContext());
	}
	
	public AddContactService(Activity currentActivity, User user) {
		super();
		this.currentActivity = currentActivity;
		sqlHelper = new MessengerDBHelper(
				currentActivity.getApplicationContext());
		this.user=user;
	}
		
	public void findContact(String pseudo, String token){
		if (InternetConnectionVerificator.isNetworkAvailable(currentActivity)) {
			String urlRequest = SERVICE_URL + "addFriendRequest.php?token="+token+"&pseudo="+pseudo;
			WebService webService = new WebServiceAddFriend(this);
			webService.execute(urlRequest);
		} else {
			new InfoDialogFragment("Internet connection unavailable").show(
					currentActivity.getFragmentManager(),
					"Internet connection unavailable");
		}
	}	

	public void instanciateFriendRequest(JSONObject result) {
	
		try {
			if (!result.getBoolean("error")) {
				((AddContactActivity) currentActivity)
				.displayErrorMessage("Friend Request send");
				goToHomePageActivity();
			}else{
				((AddContactActivity) currentActivity)
				.displayErrorMessage(result.getString("description"));
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
