package fr.utt.if26.model;

import java.io.Serializable;
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

	public List<Contact> getContactList() {
		return contactList;
	}

	public String[] getStringContactList() {
		String list[] = new String[contactList.size()];
		for (int i = 0; i < contactList.size(); i++) {
			list[i] = contactList.get(i).getCompleteName();
		}
		return list;
	}

	/**
	 * Search, in the list of contacts, the contact object corresponding to the complete name given
	 */
	public Contact getContactFromCompleteName(String completeName) {
		Contact contactToReturn = null;
		for (Contact contact : contactList) {
			if (contact.getCompleteName().equals(completeName)) {
				contactToReturn = contact;
			}
		}
		return contactToReturn;
	}

}
