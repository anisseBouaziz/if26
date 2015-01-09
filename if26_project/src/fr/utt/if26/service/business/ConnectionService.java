package fr.utt.if26.service.business;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import fr.utt.if26.activity.ConnectionActivity;
import fr.utt.if26.activity.HomePageActivity;
import fr.utt.if26.fragment.IDisplayUserInformartionsFragment;
import fr.utt.if26.fragment.InfoDialogFragment;
import fr.utt.if26.model.Contact;
import fr.utt.if26.model.User;
import fr.utt.if26.parser.ContactParser;
import fr.utt.if26.persistence.MessengerDBContract;
import fr.utt.if26.persistence.MessengerDBHelper;
import fr.utt.if26.service.IConnectionService;
import fr.utt.if26.service.IRetrieveContactsService;
import fr.utt.if26.service.web.WebService;
import fr.utt.if26.service.web.WebServiceConnection;
import fr.utt.if26.service.web.WebServiceContacts;
import fr.utt.if26.service.web.WebServiceRefreshInformations;
import fr.utt.if26.util.InternetConnectionVerificator;

public class ConnectionService implements IConnectionService,
		IRetrieveContactsService {

	private Activity currentActivity;
	private User user;
	private MessengerDBHelper sqlHelper;
	private IDisplayUserInformartionsFragment iDisplayUserInformations;

	public ConnectionService(Activity currentActivity) {
		this.currentActivity = currentActivity;
		sqlHelper = new MessengerDBHelper(
				currentActivity.getApplicationContext());
	}

	public ConnectionService(Activity currentActivity, User user) {
		super();
		this.currentActivity = currentActivity;
		sqlHelper = new MessengerDBHelper(
				currentActivity.getApplicationContext());
		this.user = user;
	}

	/**
	 * Verify if the pseudo and the password are correct
	 */
	public void connectUser(String email, String password) {
		if (InternetConnectionVerificator.isNetworkAvailable(currentActivity)) {
			String urlRequest = WebService.SERVICE_URL + "login.php?email="
					+ email + "&password=" + password;
			WebService webService = new WebServiceConnection(this);
			webService.execute(urlRequest);
		} else {
			new InfoDialogFragment("Internet connection unavailable").show(
					currentActivity.getFragmentManager(),
					"Internet connection unavailable");
		}
	}

	/**
	 * If the combination email/password given by the user is correct then
	 * create the user with the token returned by the server
	 */
	public void instanciateUser(JSONObject result) {
		try {
			if (result.getBoolean("error")) { //$NON-NLS-1$
				((ConnectionActivity) currentActivity)
						.displayIncorrectLoginErrorMessage();
			} else {
				((ConnectionActivity) currentActivity)
						.hideIncorrectLoginErrorMessage();
				String token = result.getString("token"); //$NON-NLS-1$
				user = new User(token);
				sqlHelper.persistUser(user);
				initializeUserInformations();

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * After getting the token, retrieve all informations about the user
	 * (contacts ...)
	 */
	public void initializeUserInformations() {
		if (InternetConnectionVerificator.isNetworkAvailable(currentActivity)) {
			retrieveContactsFromServer();
		} else { // If offline mode then get info from the local database
			user.setContactList(sqlHelper.retrieveContactsInDB());
			goToHomePageActivity();
		}
	}

	private void retrieveContactsFromServer() {
		sqlHelper.getWritableDatabase().delete(
				MessengerDBContract.ContactTable.TABLE_NAME, null, null);
		sqlHelper.getWritableDatabase().delete(
				MessengerDBContract.MessageTable.TABLE_NAME, null, null);
		WebService webService = new WebServiceContacts(this);
		String urlRequest = WebService.SERVICE_URL
				+ "contacts.php?token=" + user.getToken(); //$NON-NLS-1$
		webService.execute(urlRequest);
	}

	/**
	 * Method called by the WebService after the server returned the wanted JSON
	 * contening contacts
	 */
	@Override
	public void retrieveContacts(JSONObject result) {
		try {
			if (!result.getBoolean("error")) {
				List<Contact> contactList = ContactParser
						.jsonToListContact(result);
				user.setContactList(contactList);
				sqlHelper.persistOrUpdateContacts(contactList);
				updateLocation();
				goToHomePageActivity();
			} else {
				sqlHelper.getWritableDatabase().delete(
						MessengerDBContract.UserTable.TABLE_NAME, null, null);
				goToConnectionActivity();
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void goToConnectionActivity() {
		Intent intent = new Intent(currentActivity, ConnectionActivity.class);
		currentActivity.startActivity(intent);
	}

	private void goToHomePageActivity() {
		Intent intent = new Intent(currentActivity, HomePageActivity.class);
		intent.putExtra("user", user);
		currentActivity.startActivity(intent);
	}

	public void logOffUser() {
		emptyLocalDataBase();
		Intent intent = new Intent(currentActivity, ConnectionActivity.class);
		currentActivity.startActivity(intent);
		currentActivity.finish();
	}

	private void emptyLocalDataBase() {
		sqlHelper.getWritableDatabase().delete(
				MessengerDBContract.ContactTable.TABLE_NAME, null, null);
		sqlHelper.getWritableDatabase().delete(
				MessengerDBContract.MessageTable.TABLE_NAME, null, null);
		sqlHelper.getWritableDatabase().delete(
				MessengerDBContract.UserTable.TABLE_NAME, null, null);
	}

	public void refreshUserInformations(
			IDisplayUserInformartionsFragment iDisplayUserInformations) {
		this.iDisplayUserInformations = iDisplayUserInformations;
		if (InternetConnectionVerificator.isNetworkAvailable(currentActivity)) {
			refreshContactsFromServerInfo();
		} else { // If offline mode then get info from the local database
			user.setContactList(sqlHelper.retrieveContactsInDB());
			iDisplayUserInformations.refreshUserInformations(user);
		}

	}

	private void refreshContactsFromServerInfo() {
		sqlHelper.getWritableDatabase().delete(
				MessengerDBContract.ContactTable.TABLE_NAME, null, null);
		sqlHelper.getWritableDatabase().delete(
				MessengerDBContract.MessageTable.TABLE_NAME, null, null);
		WebService webService = new WebServiceRefreshInformations(this);
		String urlRequest = WebService.SERVICE_URL
				+ "contacts.php?token=" + user.getToken(); //$NON-NLS-1$
		webService.execute(urlRequest);
	}

	@Override
	public void refreshContacts(JSONObject result) {
		try {
			if (!result.getBoolean("error")) {
				List<Contact> contactList = ContactParser
						.jsonToListContact(result);
				user.setContactList(contactList);
				sqlHelper.persistOrUpdateContacts(contactList);
				this.iDisplayUserInformations.refreshUserInformations(user);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void updateLocation() {
		LocationManager locationManager = (LocationManager) currentActivity
				.getSystemService(Context.LOCATION_SERVICE);

		locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 5000, 10,
				new LocationListener() {

					@Override
					public void onStatusChanged(String provider, int status,
							Bundle extras) {
					}

					@Override
					public void onProviderEnabled(String provider) {
					}

					@Override
					public void onProviderDisabled(String provider) {
					}

					@Override
					public void onLocationChanged(Location location) {
						updateLocationRequest(location);
					}
				});
		Location location = locationManager
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		updateLocationRequest(location);
	}

	public void updateLocationRequest(Location location) {
		String urlRequest = WebService.SERVICE_URL
				+ "updateLocation.php?token=" + user.getToken() + "&latitude="
				+ location.getLatitude()+"&longitude="+location.getLongitude();
		WebService webServiceLocation = new WebService(this);
		webServiceLocation.execute(urlRequest);
	}
}
