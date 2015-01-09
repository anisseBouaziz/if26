package fr.utt.if26.service.web;

import org.json.JSONObject;

import fr.utt.if26.service.IRetrieveContactsService;


public class WebServiceRefreshInformations extends WebService {

	public WebServiceRefreshInformations(Object callerService) {
		super(callerService);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onPostExecute(JSONObject result) {
		((IRetrieveContactsService) callerService).refreshContacts(result);
		super.onPostExecute(result);
	}

}
