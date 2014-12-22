package fr.utt.if26.service.web;

import org.json.JSONObject;

import fr.utt.if26.service.IAddFriendService;

public class WebServiceAddFriend extends WebService {
	
	public WebServiceAddFriend(Object callerService) {
		super(callerService);
	}

	protected void onPostExecute(JSONObject result) {
		((IAddFriendService) callerService).instanciateFriendRequest(result);
		super.onPostExecute(result);
	}
}
