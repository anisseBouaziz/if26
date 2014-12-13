package fr.utt.if26.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.utt.if26.model.Message;

public class ConversationParser {

	/**
	 * Transform a JSON conversation (this JSON is returned by 
	 * the web-service when we call it) to a {@link List} of {@link Message}
	 * The conversation is composed of serie of messages
	 */
	public static List<Message> jsonToMessageList(
			JSONObject jsonConversation) {
		List<Message> messageList=new ArrayList<Message>();
		try {
			JSONArray jsonArray = jsonConversation.getJSONArray("messages");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonMessage = (JSONObject) jsonArray.get(i);
				instanciateMessage(jsonMessage);
				Message message = instanciateMessage(jsonMessage);
				messageList.add(message);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return messageList;
	}

	private static Message instanciateMessage(JSONObject jsonMessage)
			throws JSONException {
		String stringMessage = jsonMessage.getString("message");
		String date = jsonMessage.getString("date");
		boolean isSent = jsonMessage.getBoolean("sent");
		Message message=new Message(stringMessage, date, isSent);
		return message;
	}
}
