package fr.utt.if26.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import fr.utt.if26.R;
import fr.utt.if26.model.Contact;
import fr.utt.if26.model.User;

/**
 * Activity responsible to display the contact list of the connected user
 *
 */
public class ContactListActivity extends ListActivity {

	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_list);
		user = (User) getIntent().getSerializableExtra("user");

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1,
				user.getStringContactList());
		setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact, menu);
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

	/**
	 * When the user click on a specific contact then we show him all the conversation he has with this contact
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String completeName = (String) l.getItemAtPosition(position);
		Contact contactWithConversationToDisplay = user
				.getContactFromCompleteName(completeName);
		Intent intent = new Intent(this, ConversationActivity.class);
		intent.putExtra("contact", contactWithConversationToDisplay);
		intent.putExtra("user", user);
		startActivity(intent);
	}
}
