package fr.utt.if26.persistence;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import fr.utt.if26.model.Contact;
import fr.utt.if26.model.Message;
import fr.utt.if26.model.User;

public class MessengerDBHelper extends SQLiteOpenHelper {

	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "Messenger.db";

	public MessengerDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(MessengerDBContract.CREATE_TABLE_USER);
		db.execSQL(MessengerDBContract.CREATE_TABLE_MESSAGE);
		db.execSQL(MessengerDBContract.CREATE_TABLE_CONTACT);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	}

	public long persistUser(User user) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(MessengerDBContract.UserTable.COLUMN_NAME_TOKEN,
				user.getToken());

		// insert row
		long user_id = db.insert(MessengerDBContract.UserTable.TABLE_NAME, null,
				values);

		return user_id;
	}

	public String getUserStored() {
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT * FROM "
				+ MessengerDBContract.UserTable.TABLE_NAME;

		Cursor c = db.rawQuery(selectQuery, null);
		boolean isUserStored = c.moveToFirst();

		if (isUserStored) {
			String token = c
					.getString(c
							.getColumnIndex(MessengerDBContract.UserTable.COLUMN_NAME_TOKEN));
			return token;
		} else {
			return null;
		}

	}

	public List<Contact> retrieveContactsInDB() {
		List<Contact> contactList = new ArrayList<Contact>();
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT * FROM "
				+ MessengerDBContract.ContactTable.TABLE_NAME;

		Cursor c = db.rawQuery(selectQuery, null);
		boolean hasFoundContact=c.moveToFirst();
		if (hasFoundContact) {
			do {
				String pseudo = c
						.getString(c
								.getColumnIndex(MessengerDBContract.ContactTable.COLUMN_NAME_PSEUDO));
				int id = c.getInt(c
						.getColumnIndex(MessengerDBContract.COLUMN_NAME_ID));

				Message message = getLastMessageForContact(id);
				Contact contact = new Contact(id, pseudo, message);
				contactList.add(contact);
			} while (c.moveToNext());
		}

		return contactList;
	}

	public void persistOrUpdateContacts(List<Contact> contactList) {

		for (Contact contact : contactList) {
			persistOrUpdateContact(contact);

		}
	}

	public void persistOrUpdateContact(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(MessengerDBContract.ContactTable.COLUMN_NAME_PSEUDO,
				contact.getPseudo());
		if (isContactExisting(contact.getId())) {
			db.update(
					MessengerDBContract.ContactTable.TABLE_NAME,
					values,
					MessengerDBContract.COLUMN_NAME_ID + "="
							+ contact.getId(), null);
		} else {
			values.put(MessengerDBContract.COLUMN_NAME_ID, contact.getId());
			db.insert(MessengerDBContract.ContactTable.TABLE_NAME, null,
					values);
			if(contact.getLastMessage() !=null){
				persistMessage(contact.getLastMessage(), contact.getId());
			}
		}
	}

	public Message getLastMessageForContact(int contactId) {
		if(getMessagesWithContact(contactId).size()!=0){
			return getMessagesWithContact(contactId).get(0);
		}
		return null;
	}

	public void persistMessage(Message message, int contactId) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(MessengerDBContract.MessageTable.COLUMN_NAME_MESSAGE,
				message.getStringMessage());
		values.put(MessengerDBContract.MessageTable.COLUMN_NAME_DATE,
				message.getStringDate());
		values.put(MessengerDBContract.COLUMN_NAME_ID,
				message.getId());
		values.put(MessengerDBContract.MessageTable.COLUMN_NAME_CONTACT_ID,
				contactId);
		if (message.isSent()) {
			values.put(MessengerDBContract.MessageTable.COLUMN_NAME_SENT, 1);
		} else {
			values.put(MessengerDBContract.MessageTable.COLUMN_NAME_SENT, 0);
		}

		db.insert(MessengerDBContract.MessageTable.TABLE_NAME, null, values);

	}


	public List<Message> getMessagesWithContact(int contactId) {
		List<Message> listMessage = new ArrayList<Message>();
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT * FROM "
				+ MessengerDBContract.MessageTable.TABLE_NAME + " WHERE "
				+ MessengerDBContract.MessageTable.COLUMN_NAME_CONTACT_ID + " = "
				+ contactId + " ORDER BY "
				+ MessengerDBContract.MessageTable.COLUMN_NAME_DATE + " ASC";

		Cursor c = db.rawQuery(selectQuery, null);
		boolean isMessageStored = c.moveToFirst();

		if (isMessageStored) {
			do{
			Message message = extractMessageFromCursor(c);
			listMessage.add(message);
			}while(c.moveToNext());
		} 
		return listMessage;
	}
	
	public List<Message> getMessagesWithContact(Contact contact) {
		return getMessagesWithContact(contact.getId());
	}

	private Message extractMessageFromCursor(Cursor c) {
		String stringMessage = c
				.getString(c
						.getColumnIndex(MessengerDBContract.MessageTable.COLUMN_NAME_MESSAGE));
		String date = c
				.getString(c
						.getColumnIndex(MessengerDBContract.MessageTable.COLUMN_NAME_DATE));

		int sent = c
				.getInt(c
						.getColumnIndex(MessengerDBContract.MessageTable.COLUMN_NAME_SENT));
		int id = c
				.getInt(c
						.getColumnIndex(MessengerDBContract.COLUMN_NAME_ID));
		
		Message message;

		if (sent == 0) {
			message = new Message(stringMessage, date, false,id);
		} else {
			message = new Message(stringMessage, date, true,id);
		}
		return message;
	}


	public void deleteContact(Contact contactToDelete) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(MessengerDBContract.ContactTable.TABLE_NAME, MessengerDBContract.COLUMN_NAME_ID+" = ?"
				, new String[] { String.valueOf(contactToDelete.getId()) });		
	}
	
	private boolean isContactExisting(int contact_id) {
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT * FROM "
				+ MessengerDBContract.ContactTable.TABLE_NAME + " WHERE "
				+ MessengerDBContract.COLUMN_NAME_ID + " = " + contact_id;

		Cursor c = db.rawQuery(selectQuery, null);
		boolean isContactFound = c.moveToFirst();
		return isContactFound;
	}

	
}
