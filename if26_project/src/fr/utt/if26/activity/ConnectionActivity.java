package fr.utt.if26.activity;

import fr.utt.if26.R;
import fr.utt.if26.model.User;
import fr.utt.if26.service.web.WebService;
import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class ConnectionActivity extends Activity{

	private static final String SERVICE_URL = "http://192.168.56.1/messenger/";
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
	}

	

	
}
