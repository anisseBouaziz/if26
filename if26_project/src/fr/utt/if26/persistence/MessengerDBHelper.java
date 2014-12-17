package fr.utt.if26.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
	    values.put(MessengerContract.UserTable.COLUMN_NAME_TOKEN, user.getToken());
	   	 
	    // insert row
	    long user_id = db.insert(MessengerContract.UserTable.TABLE_NAME, null, values);
	 	 
	    return user_id;
	}
	
	public User getUserStored (){
	    SQLiteDatabase db = this.getReadableDatabase();
	    String selectQuery = "SELECT * FROM " + MessengerContract.UserTable.TABLE_NAME;
	  
	    Cursor c = db.rawQuery(selectQuery, null);
	    boolean isUserStored=c.moveToFirst();
		   
	    if(isUserStored){
	    	User user = new User(c.getString(c.getColumnIndex(MessengerContract.UserTable.COLUMN_NAME_TOKEN)));
	 	    return user;
	    }
	    else{
	    	return null;
	    }
	        
	   
	   
	    
	}

}
