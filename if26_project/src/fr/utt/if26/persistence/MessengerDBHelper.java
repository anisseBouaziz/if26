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
		db.execSQL(MessengerContract.CREATE_TABLE_USER);
		db.execSQL(MessengerContract.CREATE_TABLE_MESSAGE);
		db.execSQL(MessengerContract.CREATE_TABLE_CONTACT);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	}

	public long persistUser(User user) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(MessengerContract.UserTable.COLUMN_NAME_TOKEN,
				user.getToken());

		// insert row
		long user_id = db.insert(MessengerContract.UserTable.TABLE_NAME, null,
				values);

		return user_id;
	}

	public String getUserStored() {
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT * FROM "
				+ MessengerContract.UserTable.TABLE_NAME;

		Cursor c = db.rawQuery(selectQuery, null);
		boolean isUserStored = c.moveToFirst();

		if (isUserStored) {
			String token = c
					.getString(c
							.getColumnIndex(MessengerContract.UserTable.COLUMN_NAME_TOKEN));
			return token;
		} else {
			return null;
		}

	}
	
	public List<Contact> retrieveContactsInDB() {
		List<Contact> contactList = new ArrayList<Contact>();
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT * FROM "
				+ MessengerContract.ContactTable.TABLE_NAME;

		Cursor c = db.rawQuery(selectQuery, null);
		c.moveToFirst();
		do{
			String pseudo= c
					.getString(c
							.getColumnIndex(MessengerContract.ContactTable.COLUMN_NAME_PSEUDO));
			int id = c
					.getInt(c
							.getColumnIndex(MessengerContract.COLUMN_NAME_ID));
			
			Message message=getLastMessageForContact(id);
			Contact contact = new Contact(id, pseudo, message);
			contactList.add(contact);
		}while(c.moveToNext());
			
		return contactList;
	}

	public void persistOrUpdateContacts(List<Contact> contactList) {
		SQLiteDatabase db = this.getWritableDatabase();

		for (Contact contact : contactList) {
			ContentValues values = new ContentValues();
			values.put(MessengerContract.ContactTable.COLUMN_NAME_PSEUDO,
					contact.getPseudo());
			if(isContactExisting(contact.getId())){
				db.update(MessengerContract.ContactTable.TABLE_NAME,
						values,MessengerContract.COLUMN_NAME_ID+"="+contact.getId(),null);
			}else{
				values.put(MessengerContract.COLUMN_NAME_ID,
						contact.getId());
				db.insert(MessengerContract.ContactTable.TABLE_NAME, null,
						values);
				persistMessage(contact.getLastMessage(), contact.getId());
			}
			
			
		}
	}
	
	public Message getLastMessageForContact (int contactId){
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT * FROM "
				+ MessengerContract.MessageTable.TABLE_NAME+" WHERE "
				+MessengerContract.MessageTable.COLUMN_NAME_CONTACT_ID+" = "+contactId
				+" ORDER BY "+MessengerContract.MessageTable.COLUMN_NAME_DATE+ " ASC";

		Cursor c = db.rawQuery(selectQuery, null);
		boolean isMessageStored = c.moveToFirst();

		if (isMessageStored) {
			String stringMessage = c
					.getString(c
							.getColumnIndex(MessengerContract.MessageTable.COLUMN_NAME_MESSAGE));
			String date = c
					.getString(c
							.getColumnIndex(MessengerContract.MessageTable.COLUMN_NAME_DATE));
			
			int sent = c
					.getInt(c
							.getColumnIndex(MessengerContract.MessageTable.COLUMN_NAME_DATE));
			Message message;

			if(sent==0){
				 message = new Message(stringMessage, date, false);
			}else{
				 message = new Message(stringMessage, date, true);
			}
			return message;

			
		} else {
			return null;
		}
	}
	
	public void persistMessage(Message message, int contactId){
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(MessengerContract.MessageTable.COLUMN_NAME_MESSAGE,
				message.getMessage());
		values.put(MessengerContract.MessageTable.COLUMN_NAME_DATE,
				message.getStringDate());
		values.put(MessengerContract.MessageTable.COLUMN_NAME_CONTACT_ID,
				contactId);
		if(message.isSent()){
			values.put(MessengerContract.MessageTable.COLUMN_NAME_SENT,
					1);
		}else{
			values.put(MessengerContract.MessageTable.COLUMN_NAME_SENT,
					0);
		}
		
		db.insert(MessengerContract.MessageTable.TABLE_NAME, null,
				values);
		
	}
	private boolean isContactExisting(int contact_id){
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT * FROM "
				+ MessengerContract.ContactTable.TABLE_NAME+" WHERE "+MessengerContract.COLUMN_NAME_ID+
				" = "+contact_id;

		Cursor c = db.rawQuery(selectQuery, null);
		boolean isContactFound = c.moveToFirst();
		return isContactFound;
	}

}
