package fr.utt.if26.service.web;

import org.json.JSONObject;

import fr.utt.if26.service.ISendMessageService;

public class WebServiceSendMessage extends WebService {

	public WebServiceSendMessage(Object callerService) {
		super(callerService);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onPostExecute(JSONObject result) {
		((ISendMessageService) callerService).postSendingMessageTreatment(result);
		super.onPostExecute(result);
	}
}
