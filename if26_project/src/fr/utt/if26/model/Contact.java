package fr.utt.if26.model;

import java.io.Serializable;
import java.util.HashMap;

public class Contact implements Serializable, Comparable<Contact> {

	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private Message lastMessage;
	private String completeName;
	private static final long serialVersionUID = 1L;

	public Contact(int id, String firstName, String lastName, String email,
			Message message) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.lastMessage = message;
		this.completeName = firstName + " " + lastName;
	}

	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public Message getMessage() {
		return lastMessage;
	}

	public String getCompleteName() {
		return completeName;
	}

	@Override
	public int compareTo(Contact another) {
		return lastMessage.getStringDate().compareTo(another.getLastMessage().getStringDate());
	}

	public Message getLastMessage() {
		return lastMessage;
	}

}
