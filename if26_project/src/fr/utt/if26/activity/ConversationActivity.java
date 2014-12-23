package fr.utt.if26.activity;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import fr.utt.if26.R;
import fr.utt.if26.model.Contact;
import fr.utt.if26.model.Message;
import fr.utt.if26.model.User;
import fr.utt.if26.service.business.ConversationService;

public class ConversationActivity extends Activity {


	private Contact contactWithConversationToDisplay;
	private User user;
	private EditText messageToSendEditText;
	private Button sendButton;
	private ImageButton refreshButton;
	private ConversationService conversationService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.activity_conversation);

		contactWithConversationToDisplay = (Contact) getIntent()
				.getSerializableExtra("contact");
		user = (User) getIntent().getSerializableExtra("user");
		conversationService=new ConversationService(this,user,contactWithConversationToDisplay);
		conversationService.retrieveConversation();
		initializeListeners();

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
	 * Display conversation
	 */
	public void displayConversation(List<Message> listMessagesToDisplay) {
		
		for (Message message : listMessagesToDisplay) {
			displayMessageOnTheScreen(message);
		}
	}
	
	public void refreshConversation(){
		startActivity(getIntent());
		finish();
//		conversationService.retrieveConversation();
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
	
	private void initializeListeners() {
		messageToSendEditText = (EditText) findViewById(R.id.message);
		sendButton = (Button) findViewById(R.id.send_button);
		refreshButton = (ImageButton) findViewById(R.id.refreshButton);
		
		sendButton.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				String stringMessage = messageToSendEditText.getText().toString();
				if(!stringMessage.equals("")){
					if(contactWithConversationToDisplay.getLastMessage() == null
							|| !contactWithConversationToDisplay.getLastMessage().equals(stringMessage)){
						conversationService.sendMessage(stringMessage);
					}
				}
			}
			
		});
		
		refreshButton.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				refreshConversation();
			}
			
		});
		
	}
}
