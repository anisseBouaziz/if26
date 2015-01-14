package fr.utt.if26.activity;

import fr.utt.if26.R;
import fr.utt.if26.fragment.InfoDialogFragment;
import fr.utt.if26.model.User;
import fr.utt.if26.service.business.AddContactService;
import android.app.Activity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * Search all the possible new contacts which are in a radius of 1000m from the user physical location
 * Then the user can choose the contacts he wants to send a friend request to.
 *  All the possible new contacts are displayed through a checkbox list view.

 */
public class PossibleContactAroundActivity extends Activity implements ISendFriendRequestActivity{

	private String[] usersFound;
	private ListView listView;
	private ArrayAdapter<String> adapter;
	private AddContactService service;
	private User user;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_possible_contact_around);
		getActionBar().setTitle("Users found around you");
		listView = (ListView) findViewById(R.id.listViewFoundUsersInArea);

		usersFound = (String[]) getIntent().getSerializableExtra("usersFound");
		user = (User) getIntent().getSerializableExtra("user");

		service = new AddContactService(this, user);
		displayUsersFound();
		initializeListeners();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.possible_contact_around, menu);
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

	@Override
	protected void onStop() {
		super.onStop();
		finish();
	}

	private void displayUsersFound() {
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_multiple_choice, usersFound);
		listView.setAdapter(adapter);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	}

	private void initializeListeners() {
		Button sendRequest = (Button) findViewById(R.id.sendRequest);

		View.OnClickListener buttonListenerAccept = new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				SparseBooleanArray a = listView
						.getCheckedItemPositions();
				if (a.size() != 0) {
					for (int i = 0; i < usersFound.length; i++) {
						if (a.get(i)) {
							service.findContact(usersFound[i]);
						}
					}
					new InfoDialogFragment("Friend requests have been sent",true).show(
							getFragmentManager(),
							"Friend request has been sent");
				}

			}

		};
		sendRequest.setOnClickListener(buttonListenerAccept);
	}

	@Override
	public void postSendTreatment() {
				
	}
}
