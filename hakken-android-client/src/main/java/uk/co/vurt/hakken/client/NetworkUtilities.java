package uk.co.vurt.hakken.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import uk.co.vurt.hakken.client.json.JacksonStreamParser;
import uk.co.vurt.hakken.client.json.JobDefinitionHandler;
import uk.co.vurt.hakken.client.json.JsonStreamParser;
import uk.co.vurt.hakken.client.json.TaskDefinitionHandler;
import uk.co.vurt.hakken.domain.JSONUtil;
import uk.co.vurt.hakken.domain.job.Submission;
import uk.co.vurt.hakken.domain.job.SubmissionStatus;
import uk.co.vurt.hakken.domain.task.TaskDefinition;
import uk.co.vurt.hakken.security.HashUtils;
import uk.co.vurt.hakken.security.model.LoginResponse;
import uk.co.vurt.hakken.util.StringUtils;
import android.accounts.Account;
import android.content.Context;
import android.net.ParseException;
import android.preference.PreferenceManager;
import android.util.Log;

final public class NetworkUtilities {

	/** The tag used to log to adb console. **/
	private static final String TAG = "NetworkUtilities";

	/** The Intent extra to store password. **/
	public static final String PARAM_PASSWORD = "password";

	public static final String PARAM_AUTHTOKEN = "authToken";

	/** The Intent extra to store username. **/
	public static final String PARAM_USERNAME = "username";

	public static final String PARAM_UPDATED = "timestamp";

	public static final String USER_AGENT = "TaskHelper/1.0";

	public static final int REQUEST_TIMEOUT_MS = 300 * 1000; // ms

	public static final String AUTH_URI = "/auth/login";

	public static final String FETCH_JOBS_URI = "/jobs/for/[username]/since/[timestamp]?hmac=[hmac]";
	
	//TODO: RP/Kash Figure out where this URI needs to go to0
	
	public static final String FETCH_TASK_DEFINITIONS_URI = "/tasks/list/";

	/*
	 * The trailing slash after the username is required (due to idosyncrasies
	 * in the Spring MVC implementation of the server)
	 */
	public static final String SUBMIT_JOB_DATA_URI = "/submissions/from/[username]/?hmac=[hmac]";

	private NetworkUtilities() {
	}

	private static String getBaseUrl(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getString("sync_server", null);
	}

	/**
	 * Configures the httpClient to connect to the URL provided.
	 */
	public static HttpClient getHttpClient(Integer timeout) {
		HttpClient httpClient = new DefaultHttpClient();
		final HttpParams params = httpClient.getParams();
		if (timeout == null) {
			timeout = REQUEST_TIMEOUT_MS;
		}
		HttpConnectionParams.setConnectionTimeout(params, timeout);
		HttpConnectionParams.setSoTimeout(params, timeout);
		ConnManagerParams.setTimeout(params, timeout);
		return httpClient;
	}

	public static HttpClient getHttpClient() {
		return getHttpClient(REQUEST_TIMEOUT_MS);
	}

	/**
	 * Connects to the server, authenticates the provided username and password.
	 * 
	 * @param username
	 *            The user's username
	 * @param password
	 *            The user's password
	 * @param handler
	 *            The hander instance from the calling UI thread.
	 * @param context
	 *            The context of the calling Activity.
	 * @return boolean The boolean result indicating whether the user was
	 *         successfully authenticated.
	 */
	public static LoginResponse authenticate(Context context, String username,
			String password) {

		final HttpResponse resp;
		final ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(PARAM_USERNAME, username));
		params.add(new BasicNameValuePair(PARAM_PASSWORD, password));
		HttpEntity entity = null;
		try {
			entity = new UrlEncodedFormEntity(params);
		} catch (final UnsupportedEncodingException e) {
			// this should never happen.
			throw new AssertionError(e);
		}
		String baseUrl = getBaseUrl(context);
		if (Log.isLoggable(TAG, Log.INFO)) {
			Log.i(TAG, "Authentication to: " + baseUrl + AUTH_URI);
		}
		final HttpPost post = new HttpPost(baseUrl + AUTH_URI);
		post.addHeader(entity.getContentType());
		post.setEntity(entity);
		String authToken = null;

		LoginResponse response = new LoginResponse();

		try {
			resp = getHttpClient().execute(post);

			if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				JSONObject loginJson;
				try {
					loginJson = new JSONObject(EntityUtils.toString(resp
							.getEntity()));

					response.setSuccess(loginJson.getBoolean("success"));
					if (loginJson.has("reason")) {
						response.setReason(loginJson.getString("reason"));
					}
					if (loginJson.has("token")) {
						response.setToken(loginJson.getString("token"));
					}
				} catch (org.apache.http.ParseException e) {
					response.setSuccess(false);
					response.setReason(e.getMessage());
					Log.e(TAG, "Unable to parse login response", e);
				} catch (JSONException e) {
					response.setSuccess(false);
					response.setReason(e.getMessage());
					Log.e(TAG, "Unable to parse login response", e);
				}
			}

			if (Log.isLoggable(TAG, Log.INFO)) {
				Log.i(TAG, "Login Response: " + response);
			}
		} catch (final IOException e) {
			if (Log.isLoggable(TAG, Log.INFO)) {
				Log.i(TAG, "IOException when getting authtoken", e);
			}
			response.setReason(e.getMessage());
		} finally {
			if (Log.isLoggable(TAG, Log.VERBOSE)) {
				Log.v(TAG, "getAuthtoken completing");
			}
		}
		return response;
	}

	/**
	 * Submit job data back to the server
	 * 
	 * essentially json encoded version of the dataitems submitted as form data.
	 * 
	 * @param account
	 * @param authToken
	 * @return
	 */
	public static SubmissionStatus submitData(Context context, Account account,
			String authToken, Submission submission) {

		SubmissionStatus status = null;

		StringEntity stringEntity;
		try {
			stringEntity = new StringEntity(JSONUtil.getInstance().toJson(
					submission));

			Map<String, String> parameterMap = new HashMap<String, String>();
			parameterMap.put("username", account.name);

			String hmac = HashUtils.hash(parameterMap);
			parameterMap.put("hmac", URLUtils.encode(hmac));

			final HttpPost post = new HttpPost(StringUtils.replaceTokens(
					getBaseUrl(context) + SUBMIT_JOB_DATA_URI, parameterMap));
			Log.d(TAG, "username: " + account.name);
			Log.d(TAG, "hmac: " + hmac);

			post.setEntity(stringEntity);
			post.setHeader("Accept", "application/json");
			post.setHeader("Content-type", "application/json");

			final HttpResponse httpResponse = getHttpClient().execute(post);
			// if (httpResponse.getStatusLine().getStatusCode() ==
			// HttpStatus.SC_CREATED) {
			String response = EntityUtils.toString(httpResponse.getEntity());

			status = JSONUtil.getInstance().parseSubmissionStatus(response);
			Log.d(TAG, "Response: " + response);
			// } else {
			// Log.w(TAG, "Data submission failed: "
			// + httpResponse.getStatusLine().getStatusCode());
			// }
		} catch (UnsupportedEncodingException e) {
			Log.e(TAG, "Unable to convert submission to JSON", e);
		} catch (ClientProtocolException e) {
			Log.e(TAG, "Error submitting json", e);
		} catch (IOException e) {
			Log.e(TAG, "Error submitting json", e);
		}
		return status;

	}

	public static void fetchJobs(Context context, Account account,
			String authToken, Date lastUpdated, JobDefinitionHandler callback)
			throws JSONException, ParseException, IOException,
			AuthenticationException {

		SimpleDateFormat dateFormatter = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss");

		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("username", account.name);
		parameterMap.put("timestamp", dateFormatter.format(lastUpdated));

		String hmac = HashUtils.hash(parameterMap);
		parameterMap.put("hmac", URLUtils.encode(hmac));

		final HttpGet get = new HttpGet(StringUtils.replaceTokens(
				getBaseUrl(context) + FETCH_JOBS_URI, parameterMap));

		final HttpResponse httpResponse = getHttpClient().execute(get);
		JsonStreamParser streamParser = new JacksonStreamParser();

		streamParser.parseJobDefinitionStream(httpResponse.getEntity()
				.getContent(), callback);

	}

	/*
	 * TODO: RP/Kash - we need to bring the commented out method below back into
	 * play.
	 */
	public static void fetchTaskDefinitions(Context context, Account account,
			String authToken, Date lastUpdated, TaskDefinitionHandler callback) throws JSONException,
			ParseException, IOException, AuthenticationException {
			
		SimpleDateFormat dateFormatter = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss");

		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("username", account.name);
		parameterMap.put("timestamp", dateFormatter.format(lastUpdated));

		String hmac = HashUtils.hash(parameterMap);
		parameterMap.put("hmac", URLUtils.encode(hmac));

		final HttpGet get = new HttpGet(StringUtils.replaceTokens(
				getBaseUrl(context) + FETCH_TASK_DEFINITIONS_URI, parameterMap));

		final HttpResponse httpResponse = getHttpClient().execute(get);
		JsonStreamParser streamParser = new JacksonStreamParser();

		streamParser.parseTaskDefinitionStream(httpResponse.getEntity()
				.getContent(), callback);

	}

}
