package fr.utt.if26.activity;

import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import fr.utt.if26.R;
import fr.utt.if26.model.Contact;
import fr.utt.if26.model.Message;
import fr.utt.if26.model.User;
import fr.utt.if26.parser.ConversationParser;
import fr.utt.if26.service.business.ConversationService;
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
		ConversationService conversationService=new ConversationService(this,user,contactWithConversationToDisplay);
		conversationService.retrieveConversation();

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
		for (Message message : listMessagesToDisplay) {
			displayMessageOnTheScreen(message);
		}
	}

	private void displayMessageOnTheScreen(Message message) {
		String messageToDisplay;
		messageToDisplay = "<b>"
				+ message.getState() + " the " + message.getStringDate()
				+ ":</b><br>" + message.getStringMessage() + "<br>";
		
		View linearLayout =  findViewById(R.id.messageLayout);

		TextView messageView = new TextView(this);
		messageView.setText(Html.fromHtml(messageToDisplay));
		messageView.setId(message.getId());
		messageView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));

		((LinearLayout) linearLayout).addView(messageView);
	}
}
