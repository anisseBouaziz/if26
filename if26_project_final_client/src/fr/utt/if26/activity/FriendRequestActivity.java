package fr.utt.if26.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import fr.utt.if26.R;
import fr.utt.if26.fragment.InfoDialogFragment;
import fr.utt.if26.model.FriendRequest;
import fr.utt.if26.model.User;
import fr.utt.if26.service.business.FriendRequestManagementService;

/**
 * All the friend requests are presented through a checkox list view.
 * The user selects the friend requests he wants to accept or refuse and click on the corresponding button
 * (they are two buttons, one to accept and another to refuse)
 *
 */
public class FriendRequestActivity extends Activity {

	private ListView listViewFriendRequest;
	private List<String> listAsker;
	ArrayAdapter<String> adapter;
	private User user;
	private HashMap<String, Integer> mapRequestsList;
	private FriendRequestManagementService service;
	private Button acceptRequest;
	private Button deleteRequest;
	private RelativeLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend_request);
		getActionBar().setTitle("Friend requests");
		mapRequestsList = new HashMap<String, Integer>();
		listAsker = new ArrayList<String>();
		user = (User) getIntent().getSerializableExtra("user");
		listViewFriendRequest = (ListView) findViewById(R.id.listViewFriendRequest);
		service = new FriendRequestManagementService(this, user);
		service.retrieveFriendRequest();
		acceptRequest = (Button) findViewById(R.id.acceptRequest);
		deleteRequest = (Button) findViewById(R.id.refuseRequest);
		layout=(RelativeLayout) acceptRequest.getParent();
		initializeListeners();

	}

	private void initializeListeners() {

		View.OnClickListener buttonListenerAccept = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SparseBooleanArray a = listViewFriendRequest
						.getCheckedItemPositions();
				if (a.size() != 0) {
					for (int i = 0; i < listAsker.size(); i++) {
						if (a.get(i)) {
							service.acceptFriendRequest(listAsker.get(i));
							service.deleteFriendRequest(mapRequestsList
									.get(listAsker.get(i)));//After being accepted we have to delete it
						}
					}
				}
				service.retrieveFriendRequest();

			}

		};
		acceptRequest.setOnClickListener(buttonListenerAccept);

		View.OnClickListener buttonListenerDelete = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SparseBooleanArray a = listViewFriendRequest
						.getCheckedItemPositions();
				if (a.size() != 0) {
					for (int i = 0; i < listAsker.size(); i++) {
						if (a.get(i)) {
							service.deleteFriendRequest(mapRequestsList
									.get(listAsker.get(i)));
						}
					}
				}
				service.retrieveFriendRequest();
			}
		};
		deleteRequest.setOnClickListener(buttonListenerDelete);
	}

	public void displayFriendRequests(List<FriendRequest> friendRequestsList) {
		if(friendRequestsList.size()==0){
			displayNoRequestMessage();
		}
		else{
			initializeList(friendRequestsList);
			adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_multiple_choice, listAsker);
			listViewFriendRequest.setAdapter(adapter);
			listViewFriendRequest.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		}
		
	}

	/**
	 * Message indicating they isn't any friend request
	 */
	private void displayNoRequestMessage() {
		listViewFriendRequest.setVisibility(View.GONE);
		acceptRequest.setVisibility(View.GONE);
		deleteRequest.setVisibility(View.GONE);
		TextView messageView = new TextView(this);
		messageView.setText("You have 0 friend request :(");
		messageView.setId(1);
		messageView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));

		layout.addView(messageView);
	}

	/**
	 * Configure the checkbox list viewer to show all the {@link FriendRequest}
	 */
	private void initializeList(List<FriendRequest> friendRequestsList) {
		listAsker.clear();
		mapRequestsList.clear();
		for (FriendRequest friendRequest : friendRequestsList) {
			mapRequestsList.put(friendRequest.getAskerPseudo(),
					friendRequest.getId());
			listAsker.add(friendRequest.getAskerPseudo());
		}
	}

	
}
