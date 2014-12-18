package fr.utt.if26.service.web;

import org.json.JSONObject;

/**
 * WebService called to verify if the parameters entered by the user to connect himself are correct
 * Classes using this web-service have to implement the {@link IConnectionService} interface
 */
public class WebServiceConnection extends WebService {

	public WebServiceConnection(Object callerService) {
		super(callerService);
	}

	protected void onPostExecute(JSONObject result) {
		((IConnectionService) callerService).instanciateUser(result);
		super.onPostExecute(result);
	}

}
