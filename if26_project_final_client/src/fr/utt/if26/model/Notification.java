package fr.utt.if26.model;

public class Notification {

	private int notifId;
	
	private String messageNotification;
	
	private int userId;
	
	public Notification(int id, String messageNotification, int user) {
		this.notifId = id;
		this.messageNotification = messageNotification;
		this.userId = user;
	}

	public int getId() {
		return notifId;
	}

	public String getMessageNotif() {
		return this.messageNotification;
	}
	
	public int getUserId() {
		return this.userId;
	}
}
