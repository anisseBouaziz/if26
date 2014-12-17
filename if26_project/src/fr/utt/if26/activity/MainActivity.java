package fr.utt.if26.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import fr.utt.if26.R;
import fr.utt.if26.model.User;
import fr.utt.if26.persistence.MessengerDBHelper;
import fr.utt.if26.service.business.ConnectionService;

/**
 * This activity verifies if an user session is stored in the database
 * If an user session has been found then we display the HomePage activity.
 * If no user has session been found it means the user has to identify himself, so we display the Connection Activity
 *
 */
public class MainActivity extends Activity{

	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		MessengerDBHelper sqlHelper=new MessengerDBHelper(getApplicationContext());
		user=sqlHelper.getUserStored();
		if(user==null){
			Intent intent = new Intent(this,ConnectionActivity.class);
			startActivity(intent);
		}
		else{
			ConnectionService connectionService = new ConnectionService(this);
			connectionService.initializeUserInformations(user);
		}
		

	}
	

	/**
	 * If the user is on the Connection Activity and he presses the device return button, then the program will restart this activity, which will lead to a bug (blank screen
	 * with an infinite progress bar).
	 * To prevent this bug when we start an other activity we finish this one, so she won't be restarted.
	 */
	@Override
	protected void onStop() {
		super.onStop();
		finish();	
	}

}
