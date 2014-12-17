package fr.utt.if26.activity;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import fr.utt.if26.R;
import fr.utt.if26.model.Contact;
import fr.utt.if26.model.User;
import fr.utt.if26.parser.ContactParser;
import fr.utt.if26.persistence.MessengerDBHelper;
import fr.utt.if26.service.web.IConnectionService;
import fr.utt.if26.service.web.IRetrieveContactsService;
import fr.utt.if26.service.web.WebService;
import fr.utt.if26.service.web.WebServiceConnection;
import fr.utt.if26.service.web.WebServiceContacts;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ConnectionActivity extends Activity implements IConnectionService, IRetrieveContactsService{

	private static final String SERVICE_URL = "http://train.sandbox.eutech-ssii.com/messenger/";
	private EditText email;
	private EditText password;
	private TextView errorMessage;
	private WebService webService;
	private ConnectionActivity currentActivity;
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connection);
		email = (EditText) this.findViewById(R.id.email);
		password = (EditText) this.findViewById(R.id.password);
		Button connection = (Button) findViewById(R.id.connection);
		errorMessage = (TextView) findViewById(R.id.errorMessage);
		errorMessage.setVisibility(View.GONE);

		currentActivity = this;

		View.OnClickListener buttonListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				webService = new WebServiceConnection(currentActivity);
//				String email_string = email.getText().toString();
//				String password_string = password.getText().toString();
				String email_string="test1@test.fr";
				String password_string="test";
				String urlRequest = SERVICE_URL + "login.php?email="
						+ email_string + "&password=" + password_string;
				webService.execute(urlRequest);

			}
		};
		connection.setOnClickListener(buttonListener);

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

	/**
	 * If the combination email/password given by the user is correct then create the user with the informations returned
	 */
	public void instanciateUser(JSONObject result) {
		try {
			if (result.getBoolean("error")) {
				errorMessage.setVisibility(View.VISIBLE);
			} else {
				errorMessage.setVisibility(View.GONE);
				webService = new WebServiceContacts(currentActivity);
				String token = result.getString("token");
				user = new User(token);
				MessengerDBHelper sqlHelper = new MessengerDBHelper(getApplicationContext());
				sqlHelper.persistUser(user);
				String urlRequest = SERVICE_URL + "contacts.php?token=" + token;
				webService.execute(urlRequest);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get all the contacts of the connected user
	 */
	@Override
	public void retrieveContacts(JSONObject result) {
		try {
			if (!result.getBoolean("error")) {
				List<Contact> contactList = ContactParser
						.jsonToListContact(result);
				user.setContactList(contactList);
				Intent intent = new Intent(this, HomePageActivity.class);
				intent.putExtra("user", user);
				startActivity(intent);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	

	
}
