package fr.utt.if26.service;

import org.json.JSONObject;

public interface IManageFriendRequestService {

	public void displayFriendRequestsList(JSONObject result);

	public void postAcceptingRequest(JSONObject result);

	public void postDeleteRequest(JSONObject result);
	
}
