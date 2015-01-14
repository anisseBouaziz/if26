package fr.utt.if26.service.business;

import org.json.JSONException;
import org.json.JSONObject;

import fr.utt.if26.activity.DeleteContactActivity;
import fr.utt.if26.fragment.InfoDialogFragment;
import fr.utt.if26.model.Contact;
import fr.utt.if26.persistence.MessengerDBHelper;
import fr.utt.if26.service.IManageContactService;
import fr.utt.if26.service.web.WebService;
import fr.utt.if26.service.web.WebServiceDeleteContact;
import fr.utt.if26.util.InternetConnectionVerificator;

public class ContactManagementService implements IManageContactService {

	private DeleteContactActivity currentActivity;
	private Contact contactToDelete;

	public ContactManagementService(DeleteContactActivity deleteContactActivity) {
		this.currentActivity = deleteContactActivity;
	}

	
	
	public void deleteContact(Contact contactToDelete) {
		this.contactToDelete=contactToDelete;
		if (InternetConnectionVerificator.isNetworkAvailable(currentActivity)) {
			String urlRequest = WebService.SERVICE_URL + "deleteContact.php?id="+contactToDelete.getId();
			WebService webService = new WebServiceDeleteContact(this);
			webService.execute(urlRequest);
		} else {
			new InfoDialogFragment("Internet connection unavailable").show(
					currentActivity.getFragmentManager(),
					"Internet connection unavailable");
		}
	}

	

	@Override
	public void postDeletingTreatment(JSONObject result) {
		try {
			if(!result.getBoolean("error")){
				new MessengerDBHelper(currentActivity).deleteContact(contactToDelete);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

	
}
