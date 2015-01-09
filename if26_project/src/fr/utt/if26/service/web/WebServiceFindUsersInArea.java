package fr.utt.if26.service.web;

import org.json.JSONObject;

import fr.utt.if26.service.IAddFriendService;


public class WebServiceFindUsersInArea extends WebService {

	public WebServiceFindUsersInArea(Object callerService) {
		super(callerService);
	}

	@Override
	protected void onPostExecute(JSONObject result) {
		((IAddFriendService) callerService).usersInAreaFound(result);
		super.onPostExecute(result);
	}

}
