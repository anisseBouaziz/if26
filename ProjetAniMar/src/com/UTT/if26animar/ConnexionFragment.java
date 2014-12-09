package com.UTT.if26animar;

import com.UTT.if26animar.service.*;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ConnexionFragment extends Fragment {
	 
    private static final String SERVICE_URL = "http://train.sandbox.eutech-ssii.com/messenger/";
	private EditText email ;
    private EditText password;
    public TextView responseMessage;
    public String token;
    private TextView errorMessage;
    private WebService serviceConnection;
    private ConnexionFragment connexionFragment;

	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
		        Bundle savedInstanceState) {
		        // Inflate the layout for this fragment
		 /*
	        email = (EditText) this.getView().findViewById(R.id.email);
	        password = (EditText) this.getView().findViewById(R.id.password);
	        Button connection = (Button) this.getView().findViewById(R.id.connexionButton);
	       
	        
	        
	        connexionFragment=this;
	        

	        View.OnClickListener buttonListener = new View.OnClickListener() {

	            @Override
	            public void onClick(View v) {
	                serviceConnection = new WebServiceConnection(connexionFragment);
	                String email_string = email.getText().toString();
	                String password_string = password.getText().toString();
	                String urlRequest=SERVICE_URL+"login.php?email="+email_string+"&password="+password_string;
	                serviceConnection.execute(urlRequest);
	                
	            }
	        };
	        connection.setOnClickListener(buttonListener);*/
		        return inflater.inflate(R.layout.connexion_fragment_view, container, false);
		    }
}

	  