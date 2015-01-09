package fr.utt.if26.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import fr.utt.if26.R;
import fr.utt.if26.model.User;
import fr.utt.if26.service.business.AddContactService;

public class AddContactActivity extends Activity {

	private EditText pseudoAddContact;
	private TextView infoMessage;
	private Button addUserButton;
	private AddContactService addService;
	private AddContactActivity currentActivity;
	private User user;
	private Button searchPossibleContactInRadius;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getActionBar().hide();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_contact);
		
		pseudoAddContact = (EditText) findViewById(R.id.pseudoContactAdd);
		infoMessage = (TextView) findViewById(R.id.errorMessageAdd);
		infoMessage.setVisibility(View.GONE);
		addUserButton = (Button) findViewById(R.id.addButton);
		searchPossibleContactInRadius = (Button) findViewById(R.id.findContactInRadius);

		
		user = (User) getIntent().getSerializableExtra("user");
		addService = new AddContactService(this,user);
		currentActivity = this;
		
		View.OnClickListener buttonListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String pseudo_string = pseudoAddContact.getText().toString();
				InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
				imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
				if (currentActivity.verificationEmptyField()){
					displayInfoMessage("Please enter a pseudo");
				}
				else if(user.getContactFromPseudo(pseudo_string)!=null){
					displayInfoMessage("This user is already in your contacts");
				}
				else{
					addService.findContact(pseudo_string);
				}
			}
		};
		addUserButton.setOnClickListener(buttonListener);
		
		View.OnClickListener buttonListener2 = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				addService.findPossibleContactsInArea();
			}
		};
		searchPossibleContactInRadius.setOnClickListener(buttonListener2);
		
		
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
	
	
	
	public boolean verificationEmptyField(){
		Boolean emptyField = false;
		if(this.pseudoAddContact.getText().length() == 0){
			emptyField = true;
		}
		return emptyField;
	}
	
	public void displayInfoMessage(String text){
		infoMessage.setText(text);
		infoMessage.setVisibility(View.VISIBLE);
	}
	
	public void hideInfoMessage(){
		infoMessage.setVisibility(View.GONE);
	}
	
	
	
}
