package fr.utt.if26.service.business;

import org.json.JSONObject;

import fr.utt.if26.model.Contact;
import fr.utt.if26.model.User;
import fr.utt.if26.service.web.IRetrieveMessageListService;

public class ContactManagementService implements IRetrieveMessageListService {

	private Object caller;

	public ContactManagementService(Object caller) {
		this.caller = caller;
	}
	
	public void retrieveContact(User user){
		
	}
		
	public void deleteContact(User user, Contact contact){
		
	}

	@Override
	public void retrieveMessageList(JSONObject result) {
		
	}
}
