package fr.utt.if26.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MessengerDBHelper extends SQLiteOpenHelper {
	
	public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Messenger.db";


	public MessengerDBHelper(Context context, String name,
			CursorFactory factory, int version) {
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
		// TODO Auto-generated method stub

	}

}
