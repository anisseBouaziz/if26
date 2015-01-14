package fr.utt.if26.service.web;

import org.json.JSONObject;

import fr.utt.if26.service.IManageContactService;

public class WebServiceDeleteContact extends WebService {

	public WebServiceDeleteContact(Object callerService) {
		super(callerService);
	}

	protected void onPostExecute(JSONObject result) {
		((IManageContactService) callerService)
				.postDeletingTreatment(result);
		super.onPostExecute(result);
	}

}
