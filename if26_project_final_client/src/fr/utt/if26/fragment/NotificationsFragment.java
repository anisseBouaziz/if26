package fr.utt.if26.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.utt.if26.R;
import fr.utt.if26.model.Notification;
import fr.utt.if26.model.User;
import fr.utt.if26.service.business.NotificationManagementService;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

/**
 * Display the notifications, the user can delete them
 *
 */
public class NotificationsFragment extends Fragment {

	private User user;
	private Button deleteNotificationButton;
	private ListView listView;
	private List<String> listNotification;
	ArrayAdapter<String> adapter;
	private HashMap<String, Integer> mapNotificationList;
	private NotificationManagementService service;
	private View view;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_notification, container,
				false);
		deleteNotificationButton = (Button) view
				.findViewById(R.id.buttonDeleteNotification);
		deleteNotificationButton.setVisibility(View.GONE);

		listView = (ListView) view.findViewById(R.id.listViewNotification);
		user = (User) getActivity().getIntent().getSerializableExtra("user");
		service = new NotificationManagementService(this.getActivity(), user,
				this);
		service.retrieveNotification();
		listNotification = new ArrayList<String>();
		mapNotificationList = new HashMap<String, Integer>();
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
							service.deleteNotification(mapNotificationList
									.get(listNotification.get(i)));
							mapNotificationList.remove(listNotification.get(i));
						}
					}
				}
			}

		};
		deleteNotificationButton.setOnClickListener(buttonListenerDelete);

	}

	public void displayNotification(List<Notification> listNotif) {
		if (listNotif.size() != 0) {
			listNotification.clear();
			mapNotificationList.clear();
			deleteNotificationButton.setVisibility(View.VISIBLE);
			for (int i = 0; i < listNotif.size(); i++) {
				String message = listNotif.get(i).getMessageNotif();
				int notifId = listNotif.get(i).getId();
				mapNotificationList.put(message, notifId);
				listNotification.add(message);
			}
			adapter = new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_multiple_choice,
					listNotification);
			listView.setAdapter(adapter);
			listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		}
		else{
			displayNewTextView("No notification to display");
		}

	}

	//Add a text view to the screen
	public void displayNewTextView(String message) {
		deleteNotificationButton.setVisibility(View.GONE);
		listView.setVisibility(View.GONE);
		TextView messageView = new TextView(getActivity());
		messageView.setText(message);
		messageView.setId(2);
		messageView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));

		((RelativeLayout) view).addView(messageView);
	}

	
	

}
