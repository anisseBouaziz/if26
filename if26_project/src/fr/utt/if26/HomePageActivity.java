package fr.utt.if26;

import fr.utt.if26.activity.ContactListActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;

public class HomePageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_page);
		
		TabHost tabHost = (TabHost)findViewById(R.id.tabhost);
        tabHost.setup();
     
        TabSpec spec1=tabHost.newTabSpec("inbox");
        spec1.setContent(R.id.tab1);
        spec1.setIndicator("Inbox");
     
     
        TabSpec spec2=tabHost.newTabSpec("contact");
        spec2.setIndicator("Contacts");
        Intent intent = new Intent(this,ContactListActivity.class);
        intent.putExtra("user", this.getIntent().getSerializableExtra("user"));
		spec2.setContent(intent);
     
        
        tabHost.addTab(spec1);
        tabHost.addTab(spec2);
	}
}
