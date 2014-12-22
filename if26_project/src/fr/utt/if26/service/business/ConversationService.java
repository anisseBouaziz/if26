package fr.utt.if26.service.business;

import java.net.URLEncoder;
import java.util.List;

import org.json.JSONObject;

import fr.utt.if26.activity.ConversationActivity;
import fr.utt.if26.fragment.InfoDialogFragment;
import fr.utt.if26.model.Contact;
import fr.utt.if26.model.Message;
import fr.utt.if26.model.User;
import fr.utt.if26.parser.ConversationParser;
import fr.utt.if26.persistence.MessengerDBHelper;
import fr.utt.if26.service.IRetrieveMessageListService;
import fr.utt.if26.service.ISendMessageService;
import fr.utt.if26.service.web.WebService;
import fr.utt.if26.service.web.WebServiceMessageList;
import fr.utt.if26.service.web.WebServiceSendMessage;
import fr.utt.if26.util.InternetConnectionVerificator;

public class ConversationService implements IRetrieveMessageListService, ISendMessageService {

	private ConversationActivity callerActivity;
	private MessengerDBHelper sqlHelper;
	private Contact contact;
	private User user;
	
	public ConversationService(ConversationActivity callerActivity,User user, Contact contact) {
		this.callerActivity = callerActivity;
		sqlHelper = new MessengerDBHelper(
				callerActivity.getApplicationContext());
		this.contact=contact;
		this.user=user;
	}

	public void retrieveConversation() {
		if (InternetConnectionVerificator.isNetworkAvailable(callerActivity)) {
			String urlRequest = WebService.SERVICE_URL + "messages.php?token="
					+ user.getToken() + "&contact=" + contact.getId(); //$NON-NLS-1$

			WebServiceMessageList webService = new WebServiceMessageList(this);
			webService.execute(urlRequest);

		} else {
			List<Message> listMessagesToDisplay = sqlHelper
					.getMessagesWithContact(contact);
			callerActivity.displayConversation(listMessagesToDisplay);
		}
	}
	
	public void sendMessage(String message){
		if (InternetConnectionVerificator.isNetworkAvailable(callerActivity)) {
			String encodedMessage = URLEncoder.encode(message);
			String urlRequest = WebService.SERVICE_URL + "message.php?token="
					+ user.getToken() + "&contact=" + contact.getId()+"&message="+encodedMessage; //$NON-NLS-1$

			WebServiceSendMessage webService = new WebServiceSendMessage(this);
			webService.execute(urlRequest);

		} else {
			new InfoDialogFragment("Internet connection unavailable").show(
					callerActivity.getFragmentManager(),
					"Internet connection unavailable");
		}
	}


	@Override
	public void retrieveMessageList(JSONObject result) {
		List<Message> listMessagesToDisplay = ConversationParser
				.jsonToMessageList(result);
		for (Message message : listMessagesToDisplay) {
			sqlHelper.persistMessage(message, contact.getId());
		}
		callerActivity.displayConversation(listMessagesToDisplay);
	}
	

	@Override
	public void postSendingMessageTreatment(JSONObject result) {
		callerActivity.refreshConversation();
	}

}
