package fr.utt.if26.fragment;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import fr.utt.if26.activity.ConversationActivity;
import fr.utt.if26.model.Contact;
import fr.utt.if26.model.User;
import fr.utt.if26.persistence.MessengerDBHelper;

public class MessageListFragment extends ListFragment{

	private User user;

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		user = (User) getActivity().getIntent().getSerializableExtra("user");

		final List<String[]> history = user.getHistoryDescription();
        setListAdapter(new ArrayAdapter<String[]>(
                getActivity(),
                android.R.layout.simple_list_item_2,
                android.R.id.text1,
                history) {
     
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    String[] entry = history.get(position);
                    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                    TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                    text1.setText(entry[0]);
                    text2.setText(entry[1]);
                    return view;
                }
            });	
	}
	
	
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		String[] items = (String[]) l.getItemAtPosition(position);
		Contact contactWithConversationToDisplay = user
				.getContactFromPseudo(items[0]);
		Intent intent = new Intent(getActivity(), ConversationActivity.class);
		intent.putExtra("contact", contactWithConversationToDisplay);
		intent.putExtra("user", user);
		startActivity(intent);
		super.onListItemClick(l, v, position, id);
	}



	@Override
	public void onResume() {
		user.setContactList(new MessengerDBHelper(getActivity()).retrieveContactsInDB());
		super.onResume();
	}


}
