package fr.utt.if26.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import fr.utt.if26.R;
import fr.utt.if26.fragment.InfoDialogFragment;
import fr.utt.if26.model.FriendRequest;
import fr.utt.if26.model.User;
import fr.utt.if26.service.business.FriendRequestManagementService;
import fr.utt.if26.service.web.WebService;
import fr.utt.if26.util.InternetConnectionVerificator;

public class FriendRequestActivity extends Activity {

	private TextView errorLabel;
	private ListView listViewFriendRequest;
	private List<String> listAsker ;
	ArrayAdapter<String> adapter;
	private User user;
	private HashMap<String, Integer> mapRequestsList;
	private FriendRequestManagementService service;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend_request);
		mapRequestsList=new HashMap<String, Integer>();
		listAsker=new ArrayList<String>();
		user = (User) getIntent().getSerializableExtra("user");
		errorLabel = (TextView) findViewById(R.id.tokenView);
		errorLabel.setVisibility(View.GONE);
		listViewFriendRequest = (ListView) findViewById(R.id.listViewFriendRequest);
		service = new FriendRequestManagementService(this,user);
		service.retrieveFriendRequest();
		initializeListeners();
				
	}
		
	public void setErrorText(String text){
		errorLabel.setText(text);
		
	}

	private void initializeListeners() {
		Button acceptRequest = (Button) findViewById(R.id.acceptRequest);
		Button deleteRequest = (Button) findViewById(R.id.refuseRequest);

		
		View.OnClickListener buttonListenerAccept = new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			  SparseBooleanArray a = listViewFriendRequest.getCheckedItemPositions();
			  if (a.size() != 0){
				  for(int i = 0; i < listAsker.size(); i++)
		          {
		                if (a.valueAt(i) == false)
		                {
		                	service.acceptFriendRequest(listAsker.get(i));
		                	service.deleteFriendRequest(mapRequestsList.get(listAsker.get(i))); 
		                	listAsker.remove(i);
		                	listViewFriendRequest.setAdapter(adapter);
		                }
		          }				  
			  }

		
			}
		};
		acceptRequest.setOnClickListener(buttonListenerAccept);
		
		
		View.OnClickListener buttonListenerDelete = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				  SparseBooleanArray a = listViewFriendRequest.getCheckedItemPositions();
				  if (a.size() != 0){
					  for(int i = 0; i < listAsker.size(); i++)
			          {
			                if (a.valueAt(i) == false)
			                {
			                //	deleteFriendRequest(); jai pas encore supprimer la friend request du serveur
			                	listAsker.remove(i);
			                	listViewFriendRequest.setAdapter(adapter);
			                }
			          }				  
				  }
				
			}
		};
		deleteRequest.setOnClickListener(buttonListenerDelete);
	}

	public void displayFriendRequests(List<FriendRequest> friendRequestsList) {
		initializeList(friendRequestsList);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_multiple_choice,
				listAsker);
		listViewFriendRequest.setAdapter(adapter);
		listViewFriendRequest.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	}

	private void initializeList(List<FriendRequest> friendRequestsList) {
		for (FriendRequest friendRequest : friendRequestsList) {
			mapRequestsList.put(friendRequest.getAskerPseudo(), friendRequest.getId());
			listAsker.add(friendRequest.getAskerPseudo());
		}
	}
	
}
