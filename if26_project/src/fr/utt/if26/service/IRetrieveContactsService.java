package fr.utt.if26.service;

import org.json.JSONObject;

import fr.utt.if26.service.web.WebServiceContacts;

/**
 *Interface to be implemented by classes wanted to get a contact list via the class {@link WebServiceContacts} 
 *
 */
public interface IRetrieveContactsService {

	public void retrieveContacts(JSONObject result);

	public void refreshContacts(JSONObject result);
}
