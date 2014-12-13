package fr.utt.if26;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class HomePageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_page);
		
		TabHost tabHost = (TabHost)findViewById(R.id.tabhost);
        tabHost.setup();
     
        TabSpec spec1=tabHost.newTabSpec("TAB 1");
        spec1.setContent(R.id.tab1);
        spec1.setIndicator("TAB 1");
     
     
        TabSpec spec2=tabHost.newTabSpec("TAB 2");
        spec2.setIndicator("TAB 2");
        spec2.setContent(R.id.tab2);
     
     
        
        tabHost.addTab(spec1);
        tabHost.addTab(spec2);
	}
}
