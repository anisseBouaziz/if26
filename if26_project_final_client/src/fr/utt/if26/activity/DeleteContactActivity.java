package fr.utt.if26.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import fr.utt.if26.R;
import fr.utt.if26.fragment.InfoDialogFragment;
import fr.utt.if26.model.FriendRequest;
import fr.utt.if26.model.User;
import fr.utt.if26.service.business.ContactManagementService;

/**
 * All the contacts are displayed through a checkbox list view.
 * The user selects the contacts he wants to delete and then delete them by clicking
 * on the "Delete Contact" button
 *
 */
public class DeleteContactActivity extends Activity {

	private ListView listViewContacts;
	ArrayAdapter<String> adapter;
	private User user;
	private ContactManagementService service;
	private Button deleteButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete_contact);
		getActionBar().setTitle("Delete contacts");
		deleteButton = (Button) findViewById(R.id.delete_contact_button);
		user = (User) getIntent().getSerializableExtra("user");
		listViewContacts = (ListView) findViewById(R.id.contact_checkbox_list);
		listViewContacts.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		service = new ContactManagementService(this);
		initializeCheckBoxList();
		initializeListener();
				
	}
		
	private void initializeListener() {
		View.OnClickListener buttonListenerAccept = new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			  SparseBooleanArray a = listViewContacts.getCheckedItemPositions();
			  if (a.size() != 0){
				  int lenght=user.getStringContactList().length;
				  for(int i = 0; i <lenght ; i++)
		          {
		                if (a.get(i))
		                {
		                	String pseudo = (String) listViewContacts.getItemAtPosition(i);
							service.deleteContact(user.getContactFromPseudo(pseudo));
		                }
		          }				  
			  }

			  showDialog();			
			  }

			
		};
		deleteButton.setOnClickListener(buttonListenerAccept);
		
	}

	/**
	 * After the deleting we show a confirmation dialog
	 */
	private void showDialog() {
		new InfoDialogFragment("Selected contacts have been deleted",true).show(
					this.getFragmentManager(),
					"Delete contact");
	}
	
	/**
	 * Configure the checkbox list viewer to show
	 */
	private void initializeCheckBoxList() {
		
			adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_multiple_choice,
					user.getStringContactList());
			listViewContacts.setAdapter(adapter);
		if(user.getStringContactList().length==0){
			deleteButton.setVisibility(View.GONE);
			new InfoDialogFragment("Now you don't have any contact anymore!",true).show(
					getFragmentManager(),
					"Any contact");
		}
		
	}

	
	
}
