package fr.utt.if26.service.web;

import org.json.JSONObject;

import fr.utt.if26.service.ISendMessageService;
import fr.utt.if26.service.business.NotificationManagementService;


public class WebServiceDeleteNotification extends WebService {

	public WebServiceDeleteNotification(Object callerService) {
		super(callerService);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onPostExecute(JSONObject result) {
		((NotificationManagementService) callerService).postDeleteNotification();
		super.onPostExecute(result);
	}
}
