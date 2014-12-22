package fr.utt.if26.service.web;

import org.json.JSONObject;

import fr.utt.if26.service.IRetrieveMessageListService;

/**
 * WebService called to retrieve a list of messages (which corresponds to a conversation)
 * Classes using this web-service have to implement the {@link IRetrieveMessageListService} interface
 */
public class WebServiceMessageList extends WebService {

	public WebServiceMessageList(Object callerService) {
		super(callerService);
	}

	protected void onPostExecute(JSONObject result) {
		((IRetrieveMessageListService) callerService).retrieveMessageList(result);
		super.onPostExecute(result);
	}
}
