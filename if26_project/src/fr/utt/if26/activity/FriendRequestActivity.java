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
import fr.utt.if26.service.web.IFriendRequestService;
import fr.utt.if26.service.web.WebService;
import fr.utt.if26.service.web.WebServiceFriendRequest;
import fr.utt.if26.util.InternetConnectionVerificator;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class FriendRequestActivity extends ActionBarActivity implements IFriendRequestService {

	private TextView errorLabel;
	private ListView listFriendRequest;
	private List<String> listAsker ;
	private Button acceptRequest;
	private static final String SERVICE_URL = "http://192.168.0.20/messenger/";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend_request);
		String token = getIntent().getExtras().getString("token");
		errorLabel = (TextView) findViewById(R.id.tokenView);
		listFriendRequest = (ListView) findViewById(R.id.listViewFriendRequest);
		acceptRequest = (Button) findViewById(R.id.acceptRequest);
		retrieveFriendRequest(token);
		
		View.OnClickListener buttonListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		};
		acceptRequest.setOnClickListener(buttonListener);
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.friend_request, menu);
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
	
	
	public void retrieveFriendRequest(String token) {
		if (InternetConnectionVerificator.isNetworkAvailable(this)) {
			String urlRequest = SERVICE_URL + "getFriendRequests.php?token="+token;
			WebService webService = new WebServiceFriendRequest(this);
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
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
						android.R.layout.simple_list_item_multiple_choice,
						listAsker);
				listFriendRequest.setAdapter(adapter);
				listFriendRequest.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
				listFriendRequest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					  @Override
					  public void onItemClick(AdapterView<?> adapterView, 
					    View view, 
					    int position,
					    long id) {
						  List<String> listPseudoChecked = new ArrayList<String>();
						  SparseBooleanArray a = listFriendRequest.getCheckedItemPositions();
						  for(int i = 0; i < listAsker.size(); i++)
			                {
			                    if (a.valueAt(i))
			                    {
			                    	listPseudoChecked.add((String) listFriendRequest.getAdapter().getItem(i));
								//	errorLabel.setText(listPseudoChecked.get(i));
			                    }
			                }
					  }

					});

			}
			else{
				errorLabel.setText("Couldnt initialize requests list");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
