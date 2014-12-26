package fr.utt.if26.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import fr.utt.if26.R;
import fr.utt.if26.activity.ConversationActivity;
import fr.utt.if26.model.Contact;
import fr.utt.if26.model.FriendRequest;
import fr.utt.if26.model.Notification;
import fr.utt.if26.model.User;
import fr.utt.if26.persistence.MessengerDBHelper;
import fr.utt.if26.service.IManageNotificationService;
import fr.utt.if26.service.business.FriendRequestManagementService;
import fr.utt.if26.service.business.NotificationManagementService;
import fr.utt.if26.service.web.WebService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


public class NotificationsFragment extends Fragment{

	private User user;
	private Button deleteNotificationButton;
	private ListView listView;
	private List<String> listNotification;
	ArrayAdapter<String> adapter;
	private HashMap<String, Integer> mapNotificationList;
	private NotificationManagementService service;
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_notification, container, false);
		deleteNotificationButton = (Button) view.findViewById(R.id.buttonDeleteNotification);
		listView = (ListView) view.findViewById(R.id.listViewNotification);
		user = (User) getActivity().getIntent().getSerializableExtra("user");
		service = new NotificationManagementService(this.getActivity(), user, this);
		service.retrieveNotification();
		listNotification = new ArrayList<String>();
		mapNotificationList = new HashMap<String,Integer>();
		return view;
	}
	
	
	
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		user = (User) getActivity().getIntent().getSerializableExtra("user");
		initializeListeners();
		

		
	}
	
	
	
	private void initializeListeners() {

		
		View.OnClickListener buttonListenerDelete = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SparseBooleanArray a = listView.getCheckedItemPositions();
				if (a.size() != 0) {
					for (int i = 0; i < listNotification.size(); i++) {
						if (a.get(i)) {
							service.deleteNotification(mapNotificationList.get(listNotification.get(i)));
							mapNotificationList.remove(listNotification.get(i));
						}
					}
				}
				refresheCheckboxList();
			}

		};
		deleteNotificationButton.setOnClickListener(buttonListenerDelete);

	}

	
	public void displayNotification(List<Notification> listNotif) { 
		
		for (int i=0;i<listNotif.size();i++){
			String message = listNotif.get(i).getMessageNotif();
			int notifId = listNotif.get(i).getId();
			mapNotificationList.put(message, notifId);
			listNotification.add(message);
		}
		adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_multiple_choice, listNotification);
		listView.setAdapter(adapter);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	}

	

	private void refresheCheckboxList() {
		listNotification.clear();
		for (String messNotif : mapNotificationList.keySet()) {
			listNotification.add(messNotif);
		}
		listView.setAdapter(adapter);
	}



	@Override
	public void onResume() {
		user.setContactList(new MessengerDBHelper(getActivity()).retrieveContactsInDB());
		super.onResume();
	}


	
	
}
