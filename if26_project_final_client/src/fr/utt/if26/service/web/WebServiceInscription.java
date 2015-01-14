package fr.utt.if26.service.web;

import org.json.JSONObject;

import fr.utt.if26.service.IRegisterUserService;

public class WebServiceInscription extends WebService {

	public WebServiceInscription(Object callerService) {
		super(callerService);
	}

	protected void onPostExecute(JSONObject result) {
		super.onPostExecute(result);
		((IRegisterUserService) callerService).postRegisteringTreatment(result);

	}
}
