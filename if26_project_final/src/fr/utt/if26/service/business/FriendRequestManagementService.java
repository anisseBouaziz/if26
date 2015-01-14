package fr.utt.if26.service.business;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import fr.utt.if26.activity.FriendRequestActivity;
import fr.utt.if26.fragment.InfoDialogFragment;
import fr.utt.if26.model.Contact;
import fr.utt.if26.model.FriendRequest;
import fr.utt.if26.model.User;
import fr.utt.if26.parser.FriendRequestParser;
import fr.utt.if26.persistence.MessengerDBHelper;
import fr.utt.if26.service.IManageFriendRequestService;
import fr.utt.if26.service.web.WebService;
import fr.utt.if26.service.web.WebServiceAcceptRequest;
import fr.utt.if26.service.web.WebServiceDeleteRequest;
import fr.utt.if26.service.web.WebServiceFriendRequest;
import fr.utt.if26.util.InternetConnectionVerificator;

public class FriendRequestManagementService implements IManageFriendRequestService {

	private Activity currentActivity;
	private User user;

	public FriendRequestManagementService(Activity activity, User user) {
		this.currentActivity = activity;
		this.user=user;
	}

	public void retrieveFriendRequest() {
		if (InternetConnectionVerificator.isNetworkAvailable(currentActivity)) {
			String urlRequest = WebService.SERVICE_URL + "getFriendRequests.php?token="+user.getToken();
			WebService webService = new WebServiceFriendRequest(this);
			webService.execute(urlRequest);
		} else {
			new InfoDialogFragment("Internet connection unavailable",true).show(
					currentActivity.getFragmentManager(),
					"Internet connection unavailable");
		}
	}
	
	@Override
	public void displayFriendRequestsList(JSONObject result) {
		try {
			if(!result.getBoolean("error")){
				List<FriendRequest> friendRequestsList = FriendRequestParser.jsonToMessageList(result);
				((FriendRequestActivity) currentActivity).displayFriendRequests(friendRequestsList);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	public void acceptFriendRequest(String askerPseudo) {
		if (InternetConnectionVerificator.isNetworkAvailable(currentActivity)) {
			String urlRequest = WebService.SERVICE_URL + "addContact.php?token="+user.getToken()+"&pseudo="+askerPseudo;
			WebService webService = new WebServiceAcceptRequest(this);
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
			WebService webService = new WebServiceDeleteRequest(this);
			webService.execute(urlRequest);
		} else {
			new InfoDialogFragment("Internet connection unavailable").show(
					currentActivity.getFragmentManager(),
					"Internet connection unavailable");
		}
	}

	@Override
	public void postAcceptingRequest(JSONObject result) {
		try {
			if(!result.getBoolean("error")){
				Contact newContact = new Contact(result.getInt("contactId"), result.getString("pseudo"), null);
				new MessengerDBHelper(currentActivity).persistOrUpdateContact(newContact);
//				retrieveFriendRequest();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void postDeleteRequest(JSONObject result) {
//		retrieveFriendRequest();
		
	}

	
}
