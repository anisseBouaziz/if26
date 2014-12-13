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

import fr.utt.if26.fragment.InfoDialogFragment;
import fr.utt.if26.util.InternetConnectionVerificator;
import android.app.Activity;
import android.os.AsyncTask;


public abstract class WebService extends AsyncTask<String, Integer, JSONObject> {

	protected Activity caller;
	
	public WebService(Activity caller) {
		this.caller = caller;
	}

	@Override
	protected JSONObject doInBackground(String... params) {
		if (InternetConnectionVerificator.isNetworkAvailable(caller)) {
			JSONObject JSONResponse = executeRequest(params[0]);
			return JSONResponse;
		} else {
			this.cancel(true);
			new InfoDialogFragment("Internet connection unavailable").show(caller.getFragmentManager(),
					"Internet connection unavailable");
			return null;
		}

	}

	private JSONObject executeRequest(String uri) {
		HttpGet httpGet = new HttpGet(uri);
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse = null;
		String responseString = null;
		try {
			httpResponse = httpClient.execute(httpGet);
			StatusLine statusLine = httpResponse.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				responseString = convertHttpResponseToString(httpResponse);
			} else {
				// Closes the connection.
				httpResponse.getEntity().getContent().close();
				throw new IOException(statusLine.getReasonPhrase());
			}

		} catch (ClientProtocolException clientProtocolException) {
			clientProtocolException.printStackTrace();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}

		JSONObject JSONResponse = null;
		try {
			JSONResponse = new JSONObject(responseString);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return JSONResponse;
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
