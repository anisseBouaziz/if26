package fr.utt.if26.model;

public class FriendRequest {

	private int id;
	
	private String askerPseudo;
	
	public FriendRequest(int id, String askerPseudo) {
		this.id = id;
		this.askerPseudo = askerPseudo;
	}

	public int getId() {
		return id;
	}

	public String getAskerPseudo() {
		return askerPseudo;
	}

}
