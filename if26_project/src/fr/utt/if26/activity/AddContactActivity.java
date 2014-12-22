package fr.utt.if26.activity;

import fr.utt.if26.R;
import fr.utt.if26.R.id;
import fr.utt.if26.R.layout;
import fr.utt.if26.R.menu;
import fr.utt.if26.model.User;
import fr.utt.if26.service.business.AddContactService;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddContactActivity extends ActionBarActivity {

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
		addService = new AddContactService(this);
		
		user = (User) getIntent().getSerializableExtra("user");
		currentActivity = this;
		
		View.OnClickListener buttonListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String pseudo_string = pseudoAddContact.getText().toString();

				if (currentActivity.verificationEmptyField()){
					currentActivity.displayIncorrectInscriptionErrorMessage("User not find, try another pseudo");
				}
				else{
					addService.findContact(pseudo_string,user.getToken());
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
	
	public void displayIncorrectInscriptionErrorMessage(String text){
		errorMessage.setText(text);
		errorMessage.setVisibility(View.VISIBLE);
	}
	
	public void hideIncorrectInscriptionErrorMessage(String text){
		errorMessage.setText(text);
		errorMessage.setVisibility(View.GONE);
	}
	
	
	
}
