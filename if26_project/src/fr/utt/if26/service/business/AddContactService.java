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
	
	
	
	
	public AddContactService(Activity currentActivity, User user) {
		this.currentActivity = currentActivity;
				currentActivity.getApplicationContext();
		this.user=user;
	}
		
	public void findContact(String pseudo){
		if (InternetConnectionVerificator.isNetworkAvailable(currentActivity)) {
			String urlRequest = WebService.SERVICE_URL + "addFriendRequest.php?token="+user.getToken()+"&pseudo="+pseudo;
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
				.displayInfoMessage("Friend request has been sent");
				
			}else{
				((AddContactActivity) currentActivity)
				.displayInfoMessage(result.getString("description"));
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
}
