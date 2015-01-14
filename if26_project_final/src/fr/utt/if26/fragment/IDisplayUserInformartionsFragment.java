package fr.utt.if26.fragment;

import fr.utt.if26.model.User;

/**
 * Interface to be implemented by all the fragment showing user informations
 */
public interface IDisplayUserInformartionsFragment {
	public void refreshUserInformations(User user);
}
