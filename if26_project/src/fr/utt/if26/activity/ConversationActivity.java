package fr.utt.if26.activity;

import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import fr.utt.if26.R;
import fr.utt.if26.model.Contact;
import fr.utt.if26.model.Message;
import fr.utt.if26.model.User;
import fr.utt.if26.parser.ConversationParser;
import fr.utt.if26.service.web.IRetrieveMessageListService;

public class ConversationActivity extends Activity implements
		IRetrieveMessageListService {


	private Contact contactWithConversationToDisplay;
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.activity_conversation);

		contactWithConversationToDisplay = (Contact) getIntent()
				.getSerializableExtra("contact");
		user = (User) getIntent().getSerializableExtra("user");
		

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
		List<Message> listMessagesToDisplay = ConversationParser
				.jsonToMessageList(jsonMessageList);
		displayConversation(listMessagesToDisplay);
	}

	/**
	 * Display conversation
	 */
	public void displayConversation(List<Message> listMessagesToDisplay) {
		String conversationToDisplay = "";
		for (Message message : listMessagesToDisplay) {
			conversationToDisplay = conversationToDisplay + "<b>"
					+ message.getState() + " the " + message.getStringDate()
					+ ":</b><br>" + message.getStringMessage() + "<br><br>";
		}
		// TextView conversationView = (TextView) this
		// .findViewById(R.id.conversation);
		// conversationView.setText(Html.fromHtml(conversationToDisplay));
	}
}
