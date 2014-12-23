package fr.utt.if26.service.business;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import fr.utt.if26.activity.FriendRequestActivity;
import fr.utt.if26.fragment.InfoDialogFragment;
import fr.utt.if26.model.FriendRequest;
import fr.utt.if26.model.User;
import fr.utt.if26.parser.FriendRequestParser;
import fr.utt.if26.service.IManageFriendRequestService;
import fr.utt.if26.service.web.WebService;
import fr.utt.if26.service.web.WebServiceFriendRequest;
import fr.utt.if26.util.InternetConnectionVerificator;

public class FriendRequestManagementService implements IManageFriendRequestService {

	private FriendRequestActivity currentActivity;
	private User user;

	public FriendRequestManagementService(FriendRequestActivity activity, User user) {
		this.currentActivity = activity;
		this.user=user;
	}

	public void retrieveFriendRequest() {
		if (InternetConnectionVerificator.isNetworkAvailable(currentActivity)) {
			String urlRequest = WebService.SERVICE_URL + "getFriendRequests.php?token="+user.getToken();
			WebService webService = new WebServiceFriendRequest(this);
			webService.execute(urlRequest);
		} else {
			new InfoDialogFragment("Internet connection unavailable").show(
					currentActivity.getFragmentManager(),
					"Internet connection unavailable");
		}
	}
	
	@Override
	public void displayFriendRequestsList(JSONObject result) {
		try {
			if(!result.getBoolean("error")){
				List<FriendRequest> friendRequestsList = FriendRequestParser.jsonToMessageList(result);
				currentActivity.displayFriendRequests(friendRequestsList);
			}
			else{
				currentActivity.setErrorText("Couldnt retrieve requests");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	public void acceptFriendRequest(String askerPseudo) {
		if (InternetConnectionVerificator.isNetworkAvailable(currentActivity)) {
			String urlRequest = WebService.SERVICE_URL + "addContact.php?token="+user.getToken()+"&pseudo="+askerPseudo;
			WebService webService = new WebService(this);
			webService.execute(urlRequest);
		} else {
			new InfoDialogFragment("Internet connection unavailable").show(
					currentActivity.getFragmentManager(),
					"Internet connection unavailable");
		}
	}

	public void deleteFriendRequest(Integer requestFriendId) {
		if (InternetConnectionVerificator.isNetworkAvailable(currentActivity)) {
			String urlRequest = WebService.SERVICE_URL + "deleteFriendRequest.php?id="+requestFriendId;
			WebService webService = new WebService(this);
			webService.execute(urlRequest);
		} else {
			new InfoDialogFragment("Internet connection unavailable").show(
					currentActivity.getFragmentManager(),
					"Internet connection unavailable");
		}
	}

	
}
