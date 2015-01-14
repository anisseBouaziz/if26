package fr.utt.if26.service.web;

import org.json.JSONObject;

import fr.utt.if26.service.IManageFriendRequestService;

public class WebServiceAcceptRequest extends WebService {

	public WebServiceAcceptRequest(Object callerService) {
		super(callerService);
	}

	protected void onPostExecute(JSONObject result) {
		((IManageFriendRequestService) callerService)
				.postAcceptingRequest(result);
		super.onPostExecute(result);
	}

}
