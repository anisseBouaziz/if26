package fr.utt.if26.service.webService;

import org.json.JSONObject;

import android.app.Activity;

/**
 * WebService called to retrieve a list of contacts
 * Classes using this web-service have to implement the {@link IRetrieveContactsService} interface
 */
public class WebServiceContacts extends WebService {

	public WebServiceContacts(Activity caller) {
		super(caller);
	}

	protected void onPostExecute(JSONObject result) {
		((IRetrieveContactsService) caller).retrieveContacts(result);
		super.onPostExecute(result);
	}

}
