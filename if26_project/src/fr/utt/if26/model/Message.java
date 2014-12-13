package fr.utt.if26.model;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	private String message;
	private String date;
	private String state;
	private boolean sent;

	public Message(String message, String date, boolean sent) {
		this.message = message;
		this.date = date;
		this.sent = sent;
		if (sent) {
			state = "Sent";
		} else {
			state = "Received";
		}
	}

	public String getMessage() {
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

	

}
