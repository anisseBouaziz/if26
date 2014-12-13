package fr.utt.if26.service.web;

import org.json.JSONObject;

/**
 *Interface to be implemented by classes wanted to get a contact list via the class {@link WebServiceContacts} 
 *
 */
public interface IRetrieveContactsService {

	public void retrieveContacts(JSONObject result);
}
