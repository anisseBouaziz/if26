package fr.utt.if26.activity;

import fr.utt.if26.R;
import fr.utt.if26.model.User;
import fr.utt.if26.service.business.InscriptionService;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class InscriptionActivity extends Activity {

	private EditText emailInscription;
	private EditText passwordInscription;
	private EditText pseudoInscription;
	private TextView errorInscription;
	private InscriptionService inscriptionService;
	private InscriptionActivity currentActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inscription);
		getActionBar().setTitle("Inscription page");
		pseudoInscription = (EditText) this.findViewById(R.id.pseudoInscription);
		emailInscription = (EditText) this.findViewById(R.id.emailInscription);
		passwordInscription = (EditText) this.findViewById(R.id.passwordInscription);
		errorInscription = (TextView) this.findViewById(R.id.errorInscription);
		errorInscription.setVisibility(View.GONE);
		Button buttonInscription = (Button) findViewById(R.id.buttonInscription);
		inscriptionService = new InscriptionService(this);

		currentActivity = this;
		
		View.OnClickListener buttonListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String pseudo_string = pseudoInscription.getText().toString();
				String email_string = emailInscription.getText().toString();
				String password_string = passwordInscription.getText().toString();
				
				if (currentActivity.verificationEmptyField()){
					currentActivity.displayIncorrectInscriptionErrorMessage("One of the field is empty");
				}
				else if(password_string.length()<6){
					displayIncorrectInscriptionErrorMessage("Please enter a password with at least 6 characters");

				}
				else{
					inscriptionService.connectUser(pseudo_string, email_string, password_string);
				}
			}
		};
		buttonInscription.setOnClickListener(buttonListener);
		
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inscription, menu);
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
	
	
	public void displayIncorrectInscriptionErrorMessage(String text){
		errorInscription.setText(text);
		errorInscription.setVisibility(View.VISIBLE);
	}
	
	public void hideIncorrectInscriptionErrorMessage(String text){
		errorInscription.setText(text);
		errorInscription.setVisibility(View.GONE);
	}
	
	public Boolean verificationEmptyField(){
		Boolean emptyField = false;
		if(this.emailInscription.getText().length() == 0 ||
				this.pseudoInscription.getText().length() == 0 ||
				this.passwordInscription.getText().length() ==0 ){
			emptyField = true;
		}
		return emptyField;
	}
	
	
	
	
	
}
