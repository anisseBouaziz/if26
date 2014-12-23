package fr.utt.if26.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import fr.utt.if26.R;
import fr.utt.if26.fragment.ContactListFragment;
import fr.utt.if26.fragment.MessageListFragment;
import fr.utt.if26.fragment.NotificationsFragment;

public class HomePageActivity extends FragmentActivity {

//	private User user;
//	
//	@Override
//	protected void onRestart() {
//		ConnectionService connectionService = new ConnectionService(this, user);
//		super.onStart();
//	}

	@Override
	protected void onCreate(Bundle arg0) {
		setContentView(R.layout.activity_home_page);
		setTitle("Welcome to MessenUTT");
		 FragmentTabHost tabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
	        tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

	        tabHost.addTab(tabHost.newTabSpec("inbox").setIndicator("Inbox"),
	        		MessageListFragment.class, null);
	        tabHost.addTab(tabHost.newTabSpec("contacts").setIndicator("Contacts"),
	        		ContactListFragment.class, null);
	        tabHost.addTab(tabHost.newTabSpec("notifications").setIndicator("Notifications"),
	        		NotificationsFragment.class, null);
	       
		super.onCreate(arg0);
	}

//	public User getCurrentUser(){
//		return user;
//	}

}
