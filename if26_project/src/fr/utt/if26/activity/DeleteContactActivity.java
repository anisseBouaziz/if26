package fr.utt.if26.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import fr.utt.if26.R;
import fr.utt.if26.model.User;
import fr.utt.if26.service.business.ContactManagementService;

public class DeleteContactActivity extends Activity {

	private ListView listViewContacts;
	ArrayAdapter<String> adapter;
	private User user;
	private ContactManagementService service;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete_contact);
		getActionBar().setTitle("Delete contacts");
		user = (User) getIntent().getSerializableExtra("user");
		listViewContacts = (ListView) findViewById(R.id.contact_checkbox_list);
		listViewContacts.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		service = new ContactManagementService(this);
		initializeCheckBoxList();
		initializeListener();
				
	}
		
	private void initializeListener() {
		Button deleteButton = (Button) findViewById(R.id.delete_contact_button);
		
		View.OnClickListener buttonListenerAccept = new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			  SparseBooleanArray a = listViewContacts.getCheckedItemPositions();
			  if (a.size() != 0){
				  for(int i = 0; i < user.getStringContactList().length; i++)
		          {
		                if (a.get(i))
		                {
		                	String pseudo = (String) listViewContacts.getItemAtPosition(i);
							service.deleteContact(user.getContactFromPseudo(pseudo));
							user.removeContact(pseudo);
							listViewContacts.setAdapter(adapter);
		                }
		          }				  
			  }

			  initializeCheckBoxList();
			}
		};
		deleteButton.setOnClickListener(buttonListenerAccept);
		
	}

	private void initializeCheckBoxList() {
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_multiple_choice,
				user.getStringContactList());
		listViewContacts.setAdapter(adapter);
	}

	
	
}
