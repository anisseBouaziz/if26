package fr.utt.if26.service.web;

import org.json.JSONObject;

import fr.utt.if26.service.IRetrieveContactsService;

/**
 * WebService called to retrieve a list of contacts
 * Classes using this web-service have to implement the {@link IRetrieveContactsService} interface
 */
public class WebServiceContacts extends WebService {

	public WebServiceContacts(Object callerService) {
		super(callerService);
	}

	protected void onPostExecute(JSONObject result) {
		((IRetrieveContactsService) callerService).retrieveContacts(result);
		super.onPostExecute(result);
	}

}
