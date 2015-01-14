package fr.utt.if26.service.business;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v4.app.FragmentActivity;


import fr.utt.if26.fragment.InfoDialogFragment;
import fr.utt.if26.fragment.NotificationsFragment;
import fr.utt.if26.model.Notification;
import fr.utt.if26.model.User;
import fr.utt.if26.service.IManageNotificationService;
import fr.utt.if26.service.web.WebService;
import fr.utt.if26.service.web.WebServiceDeleteNotification;
import fr.utt.if26.service.web.WebServiceNotification;
import fr.utt.if26.util.InternetConnectionVerificator;

public class NotificationManagementService implements IManageNotificationService {
	private FragmentActivity currentActivity;
	private User user;
	private NotificationsFragment notifFrag;

	public NotificationManagementService(FragmentActivity activity, User user,NotificationsFragment notifFrag) {
		this.currentActivity = activity;
		this.user=user;
		this.notifFrag = notifFrag;
	}

	public void retrieveNotification() {
		if (InternetConnectionVerificator.isNetworkAvailable(currentActivity)) {
			String urlRequest = WebService.SERVICE_URL + "notifications.php?token="+user.getToken();
			WebService webService = new WebServiceNotification(this);
			webService.execute(urlRequest);
		} else {
			(notifFrag).displayNewTextView("Impossible to retrieve notification without internet connection try later ;)");
		}
	}
	
	@Override
	public void displayNotificationList(JSONObject result) {
		try {
			if(!result.getBoolean("error")){
				JSONArray arrayNotif = result.getJSONArray("notifications");
				List<Notification> listNotif = new ArrayList<Notification>();
				for (int i = 0; i<arrayNotif.length();i++){
					String notifMessage = arrayNotif.getJSONObject(i).getString("description");
					int id = arrayNotif.getJSONObject(i).getInt("id");
					int idUser = arrayNotif.getJSONObject(i).getInt("user");
					Notification notif = new Notification(id,notifMessage,idUser);
					
					listNotif.add(notif);
					
				}
				 this.notifFrag.displayNotification(listNotif);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	


	public void deleteNotification(Integer notifId) {
		if (InternetConnectionVerificator.isNetworkAvailable(currentActivity)) {
			String urlRequest = WebService.SERVICE_URL + "deleteNotification.php?id="+notifId;
			WebService webService = new WebServiceDeleteNotification(this);
			webService.execute(urlRequest);
		} else {
			new InfoDialogFragment("Internet connection unavailable").show(
					currentActivity.getFragmentManager(),
					"Internet connection unavailable");
		}
	}

	public void postDeleteNotification() {
		retrieveNotification();
	}

	
}

