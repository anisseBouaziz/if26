package fr.utt.if26.service.business;

import java.util.List;

import org.json.JSONObject;

import fr.utt.if26.activity.ConversationActivity;
import fr.utt.if26.model.Contact;
import fr.utt.if26.model.Message;
import fr.utt.if26.model.User;
import fr.utt.if26.parser.ConversationParser;
import fr.utt.if26.persistence.MessengerDBHelper;
import fr.utt.if26.service.web.IRetrieveMessageListService;
import fr.utt.if26.service.web.WebServiceMessageList;
import fr.utt.if26.util.InternetConnectionVerificator;

public class ConversationService implements IRetrieveMessageListService {

	private ConversationActivity callerActivity;
	private MessengerDBHelper sqlHelper;
	private static final String SERVICE_URL = "http://192.168.56.1/messenger/"; //$NON-NLS-1$


	public ConversationService(ConversationActivity callerActivity) {
		this.callerActivity = callerActivity;
		sqlHelper = new MessengerDBHelper(
				callerActivity.getApplicationContext());
	}

	public void getConversationWithContact(Contact contact,User user) {
		if (InternetConnectionVerificator.isNetworkAvailable(callerActivity)) {
			String urlRequest = SERVICE_URL+"messages.php?token="+user.getToken()
					+"&contact="+contact.getId(); //$NON-NLS-1$

			WebServiceMessageList webService = new WebServiceMessageList(this);
			webService.execute(urlRequest);
			
		} else {
			List<Message> listMessagesToDisplay = sqlHelper.getMessagesWithContact(contact);
			callerActivity.displayConversation(listMessagesToDisplay);
		}
	}

	@Override
	public void retrieveMessageList(JSONObject result) {
		List<Message> listMessagesToDisplay = ConversationParser.jsonToMessageList(result);
		callerActivity.displayConversation(listMessagesToDisplay);
	}

}
