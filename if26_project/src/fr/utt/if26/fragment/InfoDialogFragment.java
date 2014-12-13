package fr.utt.if26.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * This class has the responsability to create an info dialog with a given message. 
 * This dialog is composed by the message and a 'OK' button.
 * The dialog is removed when the 'OK' button is pressed.
 */
public class InfoDialogFragment extends DialogFragment {

	private String messageToDisplay;
	
	public InfoDialogFragment(String messageToDisplay) {
		super();
		this.messageToDisplay = messageToDisplay;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(messageToDisplay);
		AlertDialog dialog = builder.create();
		dialog.setButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		return dialog;
	}
	
}
