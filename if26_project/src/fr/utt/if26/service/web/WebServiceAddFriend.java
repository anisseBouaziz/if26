package fr.utt.if26.service.web;

import org.json.JSONObject;

public class WebServiceAddFriend extends WebService {
	
	public WebServiceAddFriend(Object callerService) {
		super(callerService);
	}

	protected void onPostExecute(JSONObject result) {
		((IAddFriendService) callerService).instanciateFriendRequest(result);
		super.onPostExecute(result);
	}
}
