package fr.utt.if26.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import fr.utt.if26.R;
import fr.utt.if26.model.User;

/**
 * Activity responsible to display the contact list of the connected user
 *
 */
public class ContactListFragment extends Fragment {

	private User user;

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		user = (User) getActivity().getIntent().getSerializableExtra("user");
		   
		ListView listView = (ListView) getActivity().findViewById(R.id.listView1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1,
				user.getStringContactList());
		listView.setAdapter(adapter);
		
		
		
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_contact_list, container, false);
		
		return view;
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

//	/**
//	 * When the user click on a specific contact then we show him all the conversation he has with this contact
//	 */
//	@Override
//	public void onListItemClick(ListView l, View v, int position, long id) {
//		super.onListItemClick(l, v, position, id);
//		String completeName = (String) l.getItemAtPosition(position);
//		Contact contactWithConversationToDisplay = user
//				.getContactFromCompleteName(completeName);
//		Intent intent = new Intent(getActivity(), ConversationActivityX.class);
//		intent.putExtra("contact", contactWithConversationToDisplay);
//		intent.putExtra("user", user);
//		startActivity(intent);
//	}
}
