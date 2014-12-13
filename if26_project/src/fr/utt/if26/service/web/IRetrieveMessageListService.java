package fr.utt.if26.service.web;

import org.json.JSONObject;

/**
 *Interface to be implemented by classes wanted to get a message list via the class {@link WebServiceMessageList} 
 *
 */
public interface IRetrieveMessageListService {

	public void retrieveMessageList(JSONObject result);

}
