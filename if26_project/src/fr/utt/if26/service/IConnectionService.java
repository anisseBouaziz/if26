package fr.utt.if26.service;

import org.json.JSONObject;

import fr.utt.if26.service.web.WebServiceConnection;

/**
 *Interface to be implemented by classes wanted to verify if the connection
 * parameters are correct via the class {@link WebServiceConnection} 
 *
 */
public interface IConnectionService {

	public void instanciateUser(JSONObject result);

}
