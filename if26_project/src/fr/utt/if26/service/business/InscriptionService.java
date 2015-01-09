package fr.utt.if26.service.business;

import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import fr.utt.if26.activity.InscriptionActivity;
import fr.utt.if26.activity.HomePageActivity;
import fr.utt.if26.fragment.InfoDialogFragment;
import fr.utt.if26.model.User;
import fr.utt.if26.persistence.MessengerDBHelper;
import fr.utt.if26.service.IRegisterUserService;
import fr.utt.if26.service.web.WebService;
import fr.utt.if26.service.web.WebServiceInscription;
import fr.utt.if26.util.InternetConnectionVerificator;

public class InscriptionService implements IRegisterUserService {

	private Activity currentActivity;
	private User user;
	private MessengerDBHelper sqlHelper;

	public InscriptionService(Activity currentActivity) {
		this.currentActivity = currentActivity;
		sqlHelper = new MessengerDBHelper(
				currentActivity.getApplicationContext());
	}

	
	public void connectUser(String pseudo, String email, String password) {
		if (InternetConnectionVerificator.isNetworkAvailable(currentActivity)) {
			String encodedPassword = URLEncoder.encode(password);
			String urlRequest = WebService.SERVICE_URL
					+ "inscription.php?pseudo=" + pseudo + "&email=" + email
					+ "&password=" + encodedPassword;
			WebService webService = new WebServiceInscription(this);
			webService.execute(urlRequest);
		} else {
			new InfoDialogFragment("Internet connection unavailable").show(
					currentActivity.getFragmentManager(),
					"Internet connection unavailable");
		}
	}

	@Override
	public void postRegisteringTreatment(JSONObject result) {
		try {
			if (result.getBoolean("error")) { //$NON-NLS-1$
				((InscriptionActivity) currentActivity)
						.displayIncorrectInscriptionErrorMessage(result
								.getString("description"));
			} else {
				((InscriptionActivity) currentActivity)
						.hideIncorrectInscriptionErrorMessage("Inscription Succeed");
				String token = result.getString("token"); //$NON-NLS-1$
				user = new User(token);
				sqlHelper.persistUser(user);
				goToHomePageActivity();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void goToHomePageActivity() {
		Intent intent = new Intent(currentActivity, HomePageActivity.class);
		intent.putExtra("user", user);
		currentActivity.startActivity(intent);
	}

}
