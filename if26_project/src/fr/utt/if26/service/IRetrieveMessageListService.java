package fr.utt.if26.service;

import org.json.JSONObject;

import fr.utt.if26.service.web.WebServiceMessageList;

/**
 *Interface to be implemented by classes wanted to get a message list via the class {@link WebServiceMessageList} 
 *
 */
public interface IRetrieveMessageListService {

	public void retrieveMessageList(JSONObject result);

}
