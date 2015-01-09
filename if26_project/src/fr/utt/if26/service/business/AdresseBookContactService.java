package fr.utt.if26.service.business;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import fr.utt.if26.model.User;

public class AdresseBookContactService {
	
	private Activity currentActivity;
	private User user;
	
	public AdresseBookContactService(Activity currentActivity, User user) {
		this.currentActivity = currentActivity;
		this.user = user;
	}
	
	public void searchContactThroughAdressBook(){
		TelephonyManager tMgr = (TelephonyManager)currentActivity.getSystemService(Context.TELEPHONY_SERVICE);
		String phoneNumber = tMgr.getLine1Number();
		phoneNumber.toString();
	}

}
