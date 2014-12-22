package fr.utt.if26.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import fr.utt.if26.R;
import fr.utt.if26.model.User;
import fr.utt.if26.service.business.AddContactService;

public class AddContactActivity extends Activity {

	private EditText pseudoAddContact;
	private TextView errorMessage;
	private Button addUserButton;
	private AddContactService addService;
	private AddContactActivity currentActivity;
	private User user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_contact);
		
		pseudoAddContact = (EditText) findViewById(R.id.pseudoContactAdd);
		errorMessage = (TextView) findViewById(R.id.errorMessageAdd);
		errorMessage.setVisibility(View.GONE);
		addUserButton = (Button) findViewById(R.id.addButton);
		
		user = (User) getIntent().getSerializableExtra("user");
		addService = new AddContactService(this,user);
		currentActivity = this;
		
		View.OnClickListener buttonListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String pseudo_string = pseudoAddContact.getText().toString();

				if (currentActivity.verificationEmptyField()){
					currentActivity.displayErrorMessage("User not find, try another pseudo");
				}
				else{
					addService.findContact(pseudo_string);
				}
			}
		};
		addUserButton.setOnClickListener(buttonListener);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_contact, menu);
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
	
	
	
	public Boolean verificationEmptyField(){
		Boolean emptyField = false;
		if(this.pseudoAddContact.getText().length() == 0){
			emptyField = true;
		}
		return emptyField;
	}
	
	public void displayErrorMessage(String text){
		errorMessage.setText(text);
		errorMessage.setVisibility(View.VISIBLE);
	}
	
	public void hideErrorMessage(){
		errorMessage.setVisibility(View.GONE);
	}
	
	
	
}
