package uk.co.vurt.taskhelper.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import uk.co.vurt.taskhelper.domain.definition.TaskDefinition;
import uk.co.vurt.taskhelper.domain.job.JobDefinition;
import android.accounts.Account;
import android.net.ParseException;
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

	    public static final int REQUEST_TIMEOUT_MS = 30 * 1000; // ms

	    //public static final String BASE_URL = "http://dev.vurt.co.uk/taskhelper"; /**TODO: Load Server URL from resource file?? */
	    
	    public static final String BASE_URL = "http://10.32.48.36:8080/taskhelper_server"; /**TODO: Load Server URL from resource file?? */

	    public static final String AUTH_URI = BASE_URL + "/auth";

	    public static final String FETCH_JOBS_URI = BASE_URL + "/jobs/for";
	    
	    public static final String FETCH_TASK_DEFINITIONS_URI = BASE_URL + "/taskdefinitions";


	    private NetworkUtilities() {
	    }

	    /**
	     * Configures the httpClient to connect to the URL provided.
	     */
	    public static HttpClient getHttpClient() {
            HttpClient httpClient = new DefaultHttpClient();
            final HttpParams params = httpClient.getParams();
            HttpConnectionParams.setConnectionTimeout(params, REQUEST_TIMEOUT_MS);
            HttpConnectionParams.setSoTimeout(params, REQUEST_TIMEOUT_MS);
            ConnManagerParams.setTimeout(params, REQUEST_TIMEOUT_MS);
            return httpClient;
	    }
	    
	    /**
	     * Connects to the server, authenticates the provided username and
	     * password.
	     * 
	     * @param username The user's username
	     * @param password The user's password
	     * @param handler The hander instance from the calling UI thread.
	     * @param context The context of the calling Activity.
	     * @return boolean The boolean result indicating whether the user was
	     *         successfully authenticated.
	     */
	    public static String authenticate(String username, String password) {

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
	        if (Log.isLoggable(TAG, Log.INFO)) {
                Log.i(TAG, "Authentication to: " + AUTH_URI);
            }
	        final HttpPost post = new HttpPost(AUTH_URI);
	        post.addHeader(entity.getContentType());
	        post.setEntity(entity);
	        try {
	            resp = getHttpClient().execute(post);
	            String authToken = null;
	            if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
	            	InputStream inputStream = (resp.getEntity() != null) ? resp.getEntity().getContent() : null;
	            	if(inputStream != null){
	            		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
	            		authToken = reader.readLine().trim();
	            		
	            	}
	            }
	            if((authToken != null) && (authToken.length() > 0)){
	            	if (Log.isLoggable(TAG, Log.VERBOSE)) {
	                    Log.v(TAG, "Successful authentication: " + authToken);
	                }
	                return authToken;
	            } else {
	                if (Log.isLoggable(TAG, Log.VERBOSE)) {
	                    Log.v(TAG, "Error authenticating" + resp.getStatusLine());
	                }
	                return null;
	            }
	        } catch (final IOException e) {
	            if (Log.isLoggable(TAG, Log.ERROR)) {
	                Log.e(TAG, "IOException when getting authtoken", e);
	            }
	            return null;
	        } finally {
	            if (Log.isLoggable(TAG, Log.VERBOSE)) {
	                Log.v(TAG, "getAuthtoken completing");
	            }
	        }
	    }

	    public static List<JobDefinition> fetchJobs(Account account, String authToken, Date lastUpdated) throws JSONException, ParseException, IOException, AuthenticationException {
	    	final ArrayList<JobDefinition> jobList = new ArrayList<JobDefinition>();
	    	
	    	String data = fetchData(FETCH_JOBS_URI + "/" + account.name, null, null, null);
	    	Log.d(TAG, "JOBS DATA: " + data);
	    	final JSONArray jobs = new JSONArray(data);
	    	
	    	for (int i = 0; i < jobs.length(); i++) {
                jobList.add(JobDefinition.valueOf(jobs.getJSONObject(i)));
            }
	    	return jobList;
	    }
	    
	    private static String fetchData(String url, Account account, String authToken, ArrayList<NameValuePair> params) throws ClientProtocolException, IOException, AuthenticationException{
	    	String data = null;
	    	if(params == null){
	    		params = new ArrayList<NameValuePair>();
	    	}
	    	if(account != null){
	    		params.add(new BasicNameValuePair(PARAM_USERNAME, account.name));
		        params.add(new BasicNameValuePair(PARAM_AUTHTOKEN, authToken));
	    	}
	    	
	    	Log.i(TAG, params.toString());
	        HttpEntity entity = new UrlEncodedFormEntity(params);
	        final HttpPost post = new HttpPost(url);
	        post.addHeader(entity.getContentType());
	        post.setEntity(entity);
	        
	        final HttpResponse httpResponse = getHttpClient().execute(post);
	    	if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
	    		data = EntityUtils.toString(httpResponse.getEntity());
	    	} else if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_UNAUTHORIZED) {
	    		Log.e(TAG, "Authentication exception in fetching remote task definitions");
                throw new AuthenticationException();
	    	} else {
	    		Log.e(TAG, "Server error in fetching remote task definitions: " + httpResponse.getStatusLine());
                throw new IOException();
	    	}
	    	return data;
	    }
	    
	    public static List<TaskDefinition> fetchTaskDefinitions(Account account, String authToken, Date lastUpdated) throws JSONException, ParseException, IOException, AuthenticationException {
	    	final ArrayList<TaskDefinition> definitionList = new ArrayList<TaskDefinition>();
	    	
	    	/**
	    	 * I'm commenting this out for the time being as the current emphasis is on working with lists of pre-set jobs, rather than choosing 
	    	 * from a list of possible tasks. It'll be reinstated as and when required.
	    	 */
//	    	final ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
//	        params.add(new BasicNameValuePair(PARAM_USERNAME, account.name));
//	        params.add(new BasicNameValuePair(PARAM_AUTHTOKEN, authToken));
//	        if (lastUpdated != null) {
//	            final SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
//	            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
//	            params.add(new BasicNameValuePair(PARAM_UPDATED, formatter.format(lastUpdated)));
//	        }
//	        Log.i(TAG, params.toString());
//	        HttpEntity entity = null;
//	        entity = new UrlEncodedFormEntity(params);
//	        final HttpPost post = new HttpPost(FETCH_TASK_DEFINITIONS_URI);
//	        post.addHeader(entity.getContentType());
//	        post.setEntity(entity);
//	        
//	        final HttpResponse resp = getHttpClient().execute(post);
//	        final String response = EntityUtils.toString(resp.getEntity());
//	        if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//	            // Succesfully connected to the samplesyncadapter server and
//	            // authenticated.
//	            final JSONArray definitions = new JSONArray(response);
//	            Log.d(TAG, response);
//	            for (int i = 0; i < definitions.length(); i++) {
//	                definitionList.add(TaskDefinition.valueOf(definitions.getJSONObject(i)));
//	            }
//	        } else {
//	            if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_UNAUTHORIZED) {
//	                Log.e(TAG, "Authentication exception in fetching remote task definitions");
//	                throw new AuthenticationException();
//	            } else {
//	                Log.e(TAG, "Server error in fetching remote task definitions: " + resp.getStatusLine());
//	                throw new IOException();
//	            }
//	        }
	        
	    	return definitionList;
	    }
	    
	}

