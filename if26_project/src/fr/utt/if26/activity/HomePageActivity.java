package fr.utt.if26.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import fr.utt.if26.R;
import fr.utt.if26.fragment.ContactListFragment;
import fr.utt.if26.fragment.MessageListFragment;
import fr.utt.if26.fragment.NotificationsFragment;

public class HomePageActivity extends FragmentActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        FragmentTabHost tabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        tabHost.addTab(tabHost.newTabSpec("inobx").setIndicator("Inbox"),
        		MessageListFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("contacts").setIndicator("Contacts"),
        		ContactListFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("notifications").setIndicator("Notifications"),
        		NotificationsFragment.class, null);
      
	}

}
