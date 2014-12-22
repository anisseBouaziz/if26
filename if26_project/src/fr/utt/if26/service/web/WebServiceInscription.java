package fr.utt.if26.service.web;

import org.json.JSONObject;

import fr.utt.if26.service.IConnectionService;

public class WebServiceInscription extends WebService {

	public WebServiceInscription(Object callerService) {
		super(callerService);
	}

	protected void onPostExecute(JSONObject result) {
		((IConnectionService) callerService).instanciateUser(result);
		super.onPostExecute(result);
	}
}
