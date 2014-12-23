package fr.utt.if26.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.utt.if26.model.FriendRequest;

public class FriendRequestParser {

	/**
	 * Transform a JSON friend requests list to a friend requests List
	 */
	public static List<FriendRequest> jsonToMessageList(
			JSONObject jsonResult) {
		List<FriendRequest> friendRequestList=new ArrayList<FriendRequest>();
		try {
			JSONArray requestsArray = jsonResult.getJSONArray("requests");
			for (int i=0;i<requestsArray.length();i++){
				int id= requestsArray.getJSONObject(i).getInt("id");
				String askerPseudo=requestsArray.getJSONObject(i).getJSONObject("asker").getString("pseudo");
				friendRequestList.add(new FriendRequest(id, askerPseudo));	
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return friendRequestList;
	}
}
