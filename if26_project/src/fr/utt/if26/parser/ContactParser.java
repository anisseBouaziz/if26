package fr.utt.if26.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.utt.if26.model.Contact;
import fr.utt.if26.model.Message;

public class ContactParser {

	/**
	 * Transform a JSON list of contact (this JSON is returned by 
	 * the web-service when we call it) to a {@link List} of {@link Contact}
	 */
	public static List<Contact> jsonToListContact(JSONObject jsonContactList) {
		ArrayList<Contact> listContact = new ArrayList<Contact>();
		try {
			JSONArray jsonArray = jsonContactList.getJSONArray("contacts");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonContact = (JSONObject) jsonArray.get(i);

				String stringMessage = jsonContact.getJSONObject("message")
						.getString("message");
				String date = jsonContact.getJSONObject("message").getString(
						"date");
				boolean sent = jsonContact.getJSONObject("message").getBoolean(
						"sent");
				Message message = new Message(stringMessage, date, sent);

				int contactId = (Integer) jsonContact.get("id");
				String firstName = jsonContact.getJSONObject("contact")
						.getString("first_name");
			
				Contact contact = new Contact(contactId, firstName, message);

				listContact.add(contact);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return listContact;
	}

}
