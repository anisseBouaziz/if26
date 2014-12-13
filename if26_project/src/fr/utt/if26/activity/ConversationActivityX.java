package fr.utt.if26.activity;

import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import fr.utt.if26.R;
import fr.utt.if26.model.Contact;
import fr.utt.if26.model.Message;
import fr.utt.if26.model.User;
import fr.utt.if26.parser.ConversationParser;
import fr.utt.if26.service.web.IRetrieveMessageListService;
import fr.utt.if26.service.web.WebService;
import fr.utt.if26.service.web.WebServiceMessageList;

public class ConversationActivityX extends Activity implements
		IRetrieveMessageListService {

	private static final String SERVICE_URL = "http://train.sandbox.eutech-ssii.com/messenger/";

	private Contact contactWithConversationToDisplay;
	private User user;
	private WebService webService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_conversation);

		contactWithConversationToDisplay = (Contact) getIntent()
				.getSerializableExtra("contact");
		user = (User) getIntent().getSerializableExtra("user");
		webService = new WebServiceMessageList(this);
		String urlRequest = SERVICE_URL + "messages.php?token="
				+ user.getToken() + "&contact="
				+ contactWithConversationToDisplay.getId();

		webService.execute(urlRequest);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.conversation, menu);
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
	 * Get all the conversation with the choosen contact
	 */
	@Override
	public void retrieveMessageList(JSONObject jsonMessageList) {
		List<Message> listMessagesToDisplay = ConversationParser.jsonToMessageList(jsonMessageList);
		displayConversation(listMessagesToDisplay);
	}

	/**
	 * Display conversation 
	 */
	private void displayConversation(List<Message> listMessagesToDisplay) {
		String conversationToDisplay = "";
		for (Message message : listMessagesToDisplay) {
			conversationToDisplay=conversationToDisplay+"<b>"+message.getState()
					+" the "+message.getStringDate()+":</b><br>"+message.getMessage()+"<br><br>";		
		}
//		TextView conversationView = (TextView) this
//				.findViewById(R.id.conversation);
//		conversationView.setText(Html.fromHtml(conversationToDisplay));
	}
}
