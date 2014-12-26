package fr.utt.if26.service.web;

import org.json.JSONObject;

import fr.utt.if26.service.IManageNotificationService;

public class WebServiceNotification extends WebService {

	
	public WebServiceNotification(Object callerService) {
		super(callerService);
	}

	protected void onPostExecute(JSONObject result) {
		((IManageNotificationService) callerService).displayNotificationList(result);
		super.onPostExecute(result);
	}

}
