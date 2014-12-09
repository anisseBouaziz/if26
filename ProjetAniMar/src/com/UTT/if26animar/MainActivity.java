package com.UTT.if26animar;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends FragmentActivity {

	private static final String FRAGMENT_CONNNEXION = null;
	private static final String INSCRIPTION_FRAGMENT = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ConnexionFragment fragment = new ConnexionFragment();
		
		android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
		transaction.replace(R.id.fragmentConnexion, fragment);
		transaction.commit();
		
		/*android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
	    fragmentTransaction.add(R.id.fragmentConnexion, new ConnexionFragment(), FRAGMENT_CONNNEXION);
	    fragmentTransaction.add(R.id.inscriptionFragment, new InscriptionFragment(), INSCRIPTION_FRAGMENT);
	    fragmentTransaction.commit();*/
	}
/*
	public void onResume() {
	    super.onResume();
	    if (true) {
	        switchToA();
	    } else {
	        switchToB();
	    }
	}

	private void switchToA() {
		ConnexionFragment fragA = (ConnexionFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_CONNNEXION);
	    android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
	    fragmentTransaction.detach(getSupportFragmentManager().findFragmentByTag(INSCRIPTION_FRAGMENT));
	    fragmentTransaction.attach(fragA);
	    fragmentTransaction.addToBackStack(null);

	    fragmentTransaction.commitAllowingStateLoss();
	    getSupportFragmentManager().executePendingTransactions();
	}
	
	private void switchToB() {
		InscriptionFragment fragB = (InscriptionFragment) getSupportFragmentManager().findFragmentByTag(INSCRIPTION_FRAGMENT);
	    android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
	    fragmentTransaction.detach(getSupportFragmentManager().findFragmentByTag(FRAGMENT_CONNNEXION));
	    fragmentTransaction.attach(fragB);
	    fragmentTransaction.addToBackStack(null);

	    fragmentTransaction.commitAllowingStateLoss();
	    getSupportFragmentManager().executePendingTransactions();
	}
	*/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
