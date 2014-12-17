package fr.utt.if26.persistence;

import android.provider.BaseColumns;

public final class MessengerContract {
	
	// Common for all tables
	public static final String COLUMN_NAME_ID = "id";

	/* Inner class that defines the table contents */
	public static abstract class UserTable implements BaseColumns {
		public static final String TABLE_NAME = "user";
		public static final String COLUMN_NAME_TOKEN = "token";
	}

	public static abstract class ContactTable implements BaseColumns {
		public static final String TABLE_NAME = "contact";
		public static final String COLUMN_NAME_PSEUDO = "pseudo";

	}

	public static abstract class MessageTable implements BaseColumns {
		public static final String TABLE_NAME = "MESSAGE";
		public static final String COLUMN_NAME_MESSAGE = "message";
		public static final String COLUMN_NAME_DATE = "date";
		public static final String COLUMN_NAME_CONTACT_ID = "contact";
		//Stored as integer 0=false, 1=true
		public static final String COLUMN_NAME_SENT = "isSent";
	}

	public static final String CREATE_TABLE_USER = "CREATE TABLE " + UserTable.TABLE_NAME
			+ "(" + COLUMN_NAME_ID + " INTEGER PRIMARY KEY," + UserTable.COLUMN_NAME_TOKEN + " TEXT"
			+")";

	public static final String CREATE_TABLE_CONTACT = "CREATE TABLE " + ContactTable.TABLE_NAME
			+ "(" + COLUMN_NAME_ID + " INTEGER PRIMARY KEY," + ContactTable.COLUMN_NAME_PSEUDO + " TEXT"
			+ ")";

	public static final String CREATE_TABLE_MESSAGE = "CREATE TABLE " + MessageTable.TABLE_NAME
			+ "(" + COLUMN_NAME_ID + " INTEGER PRIMARY KEY," + MessageTable.COLUMN_NAME_CONTACT_ID
			+ " INTEGER,"+ MessageTable.COLUMN_NAME_MESSAGE + " TEXT,"+ MessageTable.COLUMN_NAME_DATE + " TEXT,"
			+ MessageTable.COLUMN_NAME_SENT + " INTEGER"+")";

}
