package fr.utt.if26.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	private String token;
	private List<Contact> contactList;

	public User(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setContactList(List<Contact> contactList) {
		this.contactList = contactList;
	}

	public List<Contact> getAllContactList() {
		return contactList;
	}
	
	public List<Contact> getContactWithAtLeastOneMessageContactList() {
		List<Contact> contactListWithAtLeastOneMessage = new ArrayList<Contact>();
		for (Contact contact : contactList) {
			if(contact.getLastMessage() != null){ // It means no conversation between the user and the contact exists
				contactListWithAtLeastOneMessage.add(contact);
			}
		}
		Collections.sort(contactListWithAtLeastOneMessage,Collections.reverseOrder());
		return contactListWithAtLeastOneMessage;
	}

	public String[] getStringContactList() {
		String list[] = new String[contactList.size()];
		for (int i = 0; i < contactList.size(); i++) {
			list[i] = contactList.get(i).getPseudo();
		}
		Arrays.sort(list);
		return list;
	}

	/**
	 * Search, in the list of contacts, the contact object corresponding to the given pseudo
	 */
	public Contact getContactFromPseudo(String pseudo) {
		Contact contactToReturn = null;
		for (Contact contact : contactList) {
			if (contact.getPseudo().equals(pseudo)) {
				contactToReturn = contact;
			}
		}
		return contactToReturn;
	}
	
	/**
	 * Return a map of the messages history.
	 * Each key is the string pseudo of the contact, and the value is the date of 
	 * the last message
	 */
	public List<String[]> getHistoryDescription() {
		List<String[]> history = new LinkedList<String[]>();
		for (Contact contact : getContactWithAtLeastOneMessageContactList()) {
			history.add(new String[] { contact.getPseudo(), 
					contact.getMessage().getStringDate()
					+":"+contact.getMessage().getStringMessage()});
		}
		return history;
	}

}
