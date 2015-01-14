package fr.utt.if26.model;

import java.io.Serializable;

public class Contact implements Serializable, Comparable<Contact> {

	private int id;
	private String pseudo;
	private Message lastMessage;
	private static final long serialVersionUID = 1L;

	public Contact(int id, String pseudo, Message message) {
		this.id = id;
		this.pseudo = pseudo;
		this.lastMessage = message;
	}

	public int getId() {
		return id;
	}

	public String getPseudo() {
		return pseudo;
	}

	public Message getMessage() {
		return lastMessage;
	}

	@Override
	public int compareTo(Contact another) {
		return lastMessage.getStringDate().compareTo(
				another.getLastMessage().getStringDate());
	}

	public Message getLastMessage() {
		return lastMessage;
	}

	public void setLastMessage(Message message) {
		this.lastMessage = message;
	}

}
