package fr.utt.if26.model;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	private String message;
	private String date;
	private String state;
	private boolean sent;
	private int id;

	public Message(String message, String date, boolean sent,int id) {
		this.message = message;
		this.date = date;
		this.sent = sent;
		if (sent) {
			state = "Sent";
		} else {
			state = "Received";
		}
		this.id=id;
	}

	public String getStringMessage() {
		return message;
	}

	public String getStringDate() {
		return date.toString();
	}

	public String getState() {
		return state;
	}

	public boolean isSent() {
		return sent;
	}

	public int getId() {
		return id;
	}

	public void setIsSend(boolean b) {
		
	}

	

	

}
