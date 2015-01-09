package fr.utt.if26.service.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class WebService extends AsyncTask<String, Integer, JSONObject> {

	protected Object callerService;
	public static final String SERVICE_URL = "http://192.168.43.253/messenger/";

	public WebService(Object callerService) {
		this.callerService = callerService;
	}

	@Override
	protected JSONObject doInBackground(String... params) {
		JSONObject JSONResponse = executeRequest(params[0]);
		return JSONResponse;

	}

	private JSONObject executeRequest(String uri) {
		HttpGet httpGet;		
		try {
			httpGet = new HttpGet(uri);
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = null;
			String responseString = null;
			httpResponse = httpClient.execute(httpGet);
			StatusLine statusLine = httpResponse.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				responseString = convertHttpResponseToString(httpResponse);
			} else {
				// Closes the connection.
				httpResponse.getEntity().getContent().close();
				throw new IOException(statusLine.getReasonPhrase());
			}
			JSONObject jSONResponse = null;

			jSONResponse = new JSONObject(responseString);
			return jSONResponse;


		} catch (ClientProtocolException clientProtocolException) {
			clientProtocolException.printStackTrace();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;


	}

	private String convertHttpResponseToString(HttpResponse httpResponse)
			throws IOException {
		String responseString;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		httpResponse.getEntity().writeTo(out);
		out.close();
		responseString = out.toString();
		return responseString;
	}

}
