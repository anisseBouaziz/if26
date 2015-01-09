package fr.utt.if26.service.business;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import fr.utt.if26.activity.AddContactActivity;
import fr.utt.if26.activity.PossibleContactAroundActivity;
import fr.utt.if26.fragment.InfoDialogFragment;
import fr.utt.if26.model.FriendRequest;
import fr.utt.if26.model.User;
import fr.utt.if26.service.IAddFriendService;
import fr.utt.if26.service.web.WebService;
import fr.utt.if26.service.web.WebServiceAddFriend;
import fr.utt.if26.service.web.WebServiceFindUsersInArea;
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

	@Override
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
	
	public void findPossibleContactsInArea(){
		if (InternetConnectionVerificator.isNetworkAvailable(currentActivity)) {
			String urlRequest = WebService.SERVICE_URL + "findUserInRadius.php?token="+user.getToken();
			WebService webService = new WebServiceFindUsersInArea(this);
			webService.execute(urlRequest);
		} else {
			new InfoDialogFragment("Internet connection unavailable").show(
					currentActivity.getFragmentManager(),
					"Internet connection unavailable");
		}
	}

	@Override
	public void usersInAreaFound(JSONObject jsonResult) {
		try {
			JSONArray requestsArray = jsonResult.getJSONArray("users");
			for (int i=0;i<requestsArray.length();i++){
				String pseudo=requestsArray.getJSONObject(i).getString("pseudo");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Intent intent = new Intent(currentActivity, PossibleContactAroundActivity.class);
		currentActivity.startActivity(intent);
	}	
	
	
	
}
