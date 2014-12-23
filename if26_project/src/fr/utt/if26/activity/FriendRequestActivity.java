package fr.utt.if26.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.utt.if26.R;
import fr.utt.if26.R.id;
import fr.utt.if26.R.layout;
import fr.utt.if26.R.menu;
import fr.utt.if26.fragment.InfoDialogFragment;
import fr.utt.if26.model.User;
import fr.utt.if26.service.IAcceptRequest;
import fr.utt.if26.service.IAddFriendService;
import fr.utt.if26.service.IDeleteRequest;
import fr.utt.if26.service.IFriendRequestService;
import fr.utt.if26.service.web.WebService;
import fr.utt.if26.service.web.WebServiceAcceptRequest;
import fr.utt.if26.service.web.WebServiceAddFriend;
import fr.utt.if26.service.web.WebServiceDeleteRequest;
import fr.utt.if26.service.web.WebServiceFriendRequest;
import fr.utt.if26.util.InternetConnectionVerificator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class FriendRequestActivity extends Activity implements IFriendRequestService,IAcceptRequest, IDeleteRequest {

	private TextView errorLabel;
	private ListView listFriendRequest;
	private List<String> listAsker ;
	ArrayAdapter<String> adapter;
	private String token;
	private Button acceptRequest;
	private Button deleteRequest;
	private User user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend_request);
		user = (User) getIntent().getSerializableExtra("user");
		token = user.getToken();
		errorLabel = (TextView) findViewById(R.id.tokenView);
		listFriendRequest = (ListView) findViewById(R.id.listViewFriendRequest);
		acceptRequest = (Button) findViewById(R.id.acceptRequest);
		deleteRequest = (Button) findViewById(R.id.refuseRequest);
		retrieveFriendRequest(token);

		
		View.OnClickListener buttonListenerAccept = new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			  SparseBooleanArray a = listFriendRequest.getCheckedItemPositions();
			  if (a.size() != 0){
				  for(int i = 0; i < listAsker.size(); i++)
		          {
		                if (a.valueAt(i) == false)
		                {
		                	acceptFriendRequest(token,listAsker.get(i));
		                //	deleteFriendRequest(); jai pas encore supprimer la friend request du serveur
		                	listAsker.remove(i);
		                	listFriendRequest.setAdapter(adapter);
		                }
		          }				  
			  }

		
			}
		};
		acceptRequest.setOnClickListener(buttonListenerAccept);
		
		
		View.OnClickListener buttonListenerDelete = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				  SparseBooleanArray a = listFriendRequest.getCheckedItemPositions();
				  if (a.size() != 0){
					  for(int i = 0; i < listAsker.size(); i++)
			          {
			                if (a.valueAt(i) == false)
			                {
			                //	deleteFriendRequest(); jai pas encore supprimer la friend request du serveur
			                	listAsker.remove(i);
			                	listFriendRequest.setAdapter(adapter);
			                }
			          }				  
				  }
				
			}
		};
		deleteRequest.setOnClickListener(buttonListenerDelete);
		
		
		
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
	
	
	public void retrieveFriendRequest(String token) {
		if (InternetConnectionVerificator.isNetworkAvailable(this)) {
			String urlRequest = WebService.SERVICE_URL + "getFriendRequests.php?token="+token;
			WebService webService = new WebServiceFriendRequest(this);
			webService.execute(urlRequest);
		} else {
			new InfoDialogFragment("Internet connection unavailable").show(
					this.getFragmentManager(),
					"Internet connection unavailable");
		}
	}
	
	public void acceptFriendRequest(String token,String pseudo) {
		if (InternetConnectionVerificator.isNetworkAvailable(this)) {
			String urlRequest = WebService.SERVICE_URL + "addContact.php?token="+token+"&pseudo="+pseudo;
			WebService webService = new WebServiceAcceptRequest(this);
			webService.execute(urlRequest);
		} else {
			new InfoDialogFragment("Internet connection unavailable").show(
					this.getFragmentManager(),
					"Internet connection unavailable");
		}
	}
	
	public void deleteFriendRequest(String token,String pseudo) {
		if (InternetConnectionVerificator.isNetworkAvailable(this)) {
			String urlRequest = WebService.SERVICE_URL + "deleteFriendRequest.php";
			WebService webService = new WebServiceDeleteRequest(this);
			webService.execute(urlRequest);
		} else {
			new InfoDialogFragment("Internet connection unavailable").show(
					this.getFragmentManager(),
					"Internet connection unavailable");
		}
	}
	
	
	
	
	

	@Override
	public void getListFriendRequest(JSONObject result) {
		
		try {
			listAsker = new ArrayList<String>();
			if(!result.getBoolean("error")){
				JSONArray requestsArray = result.getJSONArray("requests");

				for (int i=0;i<requestsArray.length();i++){
					listAsker.add(requestsArray.getJSONObject(i).getJSONObject("asker").getString("pseudo"));
					
				}
				adapter = new ArrayAdapter<String>(this,
						android.R.layout.simple_list_item_multiple_choice,
						listAsker);
				listFriendRequest.setAdapter(adapter);
				listFriendRequest.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

			}
			else{
				setErrorText("Couldnt initialize requests list");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void setErrorText(String text){
		errorLabel.setText(text);
		
	}


	@Override
	public void deleteFriendRequestResponse(JSONObject result) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void acceptFriendRequestResponse(JSONObject result) {
		// TODO Auto-generated method stub
		
	}



	
	
}
