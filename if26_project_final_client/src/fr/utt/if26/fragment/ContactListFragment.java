package fr.utt.if26.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import fr.utt.if26.R;
import fr.utt.if26.activity.AddContactActivity;
import fr.utt.if26.activity.ConversationActivity;
import fr.utt.if26.activity.DeleteContactActivity;
import fr.utt.if26.activity.FriendRequestActivity;
import fr.utt.if26.model.Contact;
import fr.utt.if26.model.User;
import fr.utt.if26.service.business.ConnectionService;
import fr.utt.if26.util.InternetConnectionVerificator;

/**
 * Fragment responsible to display the contact list of the connected user
 * 
 */
public class ContactListFragment extends Fragment implements
		IDisplayUserInformartionsFragment {

	private User user;
	private Button addContactButton;
	private Button friendRequestButton;
	private Context context;
	private ListView listView;
	private Button deleteContactButton;
	private View view;
	private TextView messageView;

	public void onAttach(Activity activity) {
		super.onAttach(activity);
		context = getActivity();
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_contact_list, container,
				false);
		addContactButton = (Button) view.findViewById(R.id.buttonAddContact);
		friendRequestButton = (Button) view
				.findViewById(R.id.buttonFriendRequest);
		deleteContactButton = (Button) view
				.findViewById(R.id.buttonDeleteContact);
		listView = (ListView) view.findViewById(R.id.listView1);

		messageView = new TextView(getActivity());
		messageView.setText("You don't have any contact :(");
		messageView.setId(1);
		messageView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));

		((RelativeLayout) view).addView(messageView);
		messageView.setVisibility(View.GONE);

		return view;
	}

	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		user = (User) getActivity().getIntent().getSerializableExtra("user");

		configureContactList();

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				String pseudo = (String) adapterView
						.getItemAtPosition(position);
				displayConversationWithSelectedContact(pseudo);
			}

			public void displayConversationWithSelectedContact(String pseudo) {
				Contact contactWithConversationToDisplay = user
						.getContactFromPseudo(pseudo);
				Intent intent = new Intent(context, ConversationActivity.class);
				intent.putExtra("contact", contactWithConversationToDisplay);
				intent.putExtra("user", user);
				startActivity(intent);
			}
		});

		addContactButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (InternetConnectionVerificator
						.isNetworkAvailable(getActivity())) {
					Intent intent = new Intent(getActivity(),
							AddContactActivity.class);
					intent.putExtra("user", user);
					startActivity(intent);
				} else {
					new InfoDialogFragment("Internet connection unavailable")
							.show(getActivity().getFragmentManager(),
									"Internet connection unavailable");
				}
			}
		});

		friendRequestButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				if (InternetConnectionVerificator
						.isNetworkAvailable(getActivity())) {

					Intent intent = new Intent(getActivity(),
							FriendRequestActivity.class);
					intent.putExtra("user", user);
					startActivity(intent);

				} else {
					new InfoDialogFragment("Internet connection unavailable")
							.show(getActivity().getFragmentManager(),
									"Internet connection unavailable");
				}
			}
		});

		deleteContactButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if (InternetConnectionVerificator
						.isNetworkAvailable(getActivity())) {
					if (user.getStringContactList().length == 0) {
						new InfoDialogFragment("You don't have any contact!")
								.show(getActivity().getFragmentManager(),
										"Any contact");
					} else {
						Intent intent = new Intent(getActivity(),
								DeleteContactActivity.class);
						intent.putExtra("user", user);
						startActivity(intent);
					}

				} else {
					new InfoDialogFragment("Internet connection unavailable")
							.show(getActivity().getFragmentManager(),
									"Internet connection unavailable");
				}
			}
		});

	}

	public void configureContactList() {
		
			messageView.setVisibility(View.GONE);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					getActivity(), android.R.layout.simple_list_item_1,
					user.getStringContactList());
			listView.setAdapter(adapter);

			if (user.getStringContactList().length == 0) {
				messageView.setVisibility(View.VISIBLE);

			}

	}

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
	public void onResume() {
		ConnectionService service = new ConnectionService(getActivity(), user);
		service.refreshUserInformations(this);
		super.onResume();
	}

	@Override
	public void refreshUserInformations(User user) {
		this.user = user;
		configureContactList();
	}

}
