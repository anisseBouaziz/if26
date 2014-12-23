package fr.utt.if26.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import fr.utt.if26.R;
import fr.utt.if26.fragment.ContactListFragment;
import fr.utt.if26.fragment.MessageListFragment;
import fr.utt.if26.fragment.NotificationsFragment;
import fr.utt.if26.model.User;
import fr.utt.if26.service.business.ConnectionService;

public class HomePageActivity extends FragmentActivity {

//	private User user;
	
//	@Override
//	protected void onRestart() {
//		ConnectionService connectionService = new ConnectionService(this, user);
//		super.onStart();
//	}

	@Override
	protected void onCreate(Bundle arg0) {
		setContentView(R.layout.activity_home_page);
		setTitle("Welcome");
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.home_page_deco_action, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.deconnection_action:
	            logOffUser();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	private void logOffUser() {
		ConnectionService connectionService = new ConnectionService(this);
		connectionService.logOffUser();
		
		
	}

}
