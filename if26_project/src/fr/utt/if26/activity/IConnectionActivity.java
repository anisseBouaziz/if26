package fr.utt.if26.activity;

import fr.utt.if26.model.User;

public interface IConnectionActivity {
	
	public void displayHomePage (User user);
	
	public void displayErrorMessage (String ErrorMessage);

}
