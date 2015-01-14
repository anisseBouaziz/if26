package fr.utt.if26.service.web;

import org.json.JSONObject;

import fr.utt.if26.service.IManageFriendRequestService;

public class WebServiceFriendRequest extends WebService {
	
	public WebServiceFriendRequest(Object callerService) {
		super(callerService);
	}

	protected void onPostExecute(JSONObject result) {
		((IManageFriendRequestService) callerService).displayFriendRequestsList(result);
		super.onPostExecute(result);
	}

}
