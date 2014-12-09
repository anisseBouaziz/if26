package fr.utt.if26.service.webService;

import org.json.JSONObject;

import android.app.Activity;

/**
 * WebService called to retrieve a list of messages (which corresponds to a conversation)
 * Classes using this web-service have to implement the {@link IRetrieveMessageListService} interface
 */
public class WebServiceMessageList extends WebService {

	public WebServiceMessageList(Activity caller) {
		super(caller);
	}

	protected void onPostExecute(JSONObject result) {
		((IRetrieveMessageListService) caller).retrieveMessageList(result);
		super.onPostExecute(result);
	}
}
