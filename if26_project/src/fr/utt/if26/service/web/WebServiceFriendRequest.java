package fr.utt.if26.service.web;

import org.json.JSONObject;

import fr.utt.if26.service.IFriendRequestService;

public class WebServiceFriendRequest extends WebService {
	
	public WebServiceFriendRequest(Object callerService) {
		super(callerService);
	}

	protected void onPostExecute(JSONObject result) {
		((IFriendRequestService) callerService).getListFriendRequest(result);
		super.onPostExecute(result);
	}

}
