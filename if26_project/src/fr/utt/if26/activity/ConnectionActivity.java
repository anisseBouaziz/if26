package fr.utt.if26.activity;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import fr.utt.if26.R;
import fr.utt.if26.model.Contact;
import fr.utt.if26.model.User;
import fr.utt.if26.parser.ContactParser;
import fr.utt.if26.service.IRetrieveContactsService;
import fr.utt.if26.service.business.ConnectionService;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ConnectionActivity extends Activity{

	private EditText email;
	private EditText password;
	private TextView errorMessage;
	private ConnectionService connectionService;
	private ConnectionActivity currentActivity;
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setTitle("Identification page");
		setContentView(R.layout.activity_connection);
		email = (EditText) this.findViewById(R.id.email);
		password = (EditText) this.findViewById(R.id.password);
		Button connectionButton = (Button) findViewById(R.id.connection);
		Button inscriptionButton = (Button) findViewById(R.id.inscription);
		errorMessage = (TextView) findViewById(R.id.errorMessage);
		errorMessage.setVisibility(View.GONE);
		
		connectionService = new ConnectionService(this);
		currentActivity =this;

		View.OnClickListener buttonListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String email_string = email.getText().toString();
				String password_string = password.getText().toString();
				connectionService.connectUser(email_string, password_string);
			}
		};
		connectionButton.setOnClickListener(buttonListener);
		
		View.OnClickListener inscriptionListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(currentActivity, InscriptionActivity.class);
				startActivity(intent);
			}
		};
		inscriptionButton.setOnClickListener(inscriptionListener);


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.activity_main, container,
					false);
			return rootView;
		}
	}

	public void displayIncorrectLoginErrorMessage(){
		errorMessage.setVisibility(View.VISIBLE);
	}
	
	public void hideIncorrectLoginErrorMessage(){
		errorMessage.setVisibility(View.GONE);
	}
	
	

	

	
}
