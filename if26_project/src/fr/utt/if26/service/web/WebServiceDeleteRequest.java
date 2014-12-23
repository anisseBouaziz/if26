package fr.utt.if26.service.web;

import org.json.JSONObject;

import fr.utt.if26.service.IDeleteRequest;

public class WebServiceDeleteRequest extends WebService{
	
	public WebServiceDeleteRequest(Object callerService) {
		super(callerService);
	}

	protected void onPostExecute(JSONObject result) {
		
		((IDeleteRequest) callerService).deleteFriendRequestResponse(result);
		super.onPostExecute(result);
	}
}
