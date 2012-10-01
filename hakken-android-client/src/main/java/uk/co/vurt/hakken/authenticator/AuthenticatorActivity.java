package uk.co.vurt.hakken.authenticator;

import uk.co.vurt.hakken.Constants;
import uk.co.vurt.hakken.activities.DispatcherActivity;
import uk.co.vurt.hakken.client.NetworkUtilities;
import uk.co.vurt.hakken.providers.TaskProvider;
import uk.co.vurt.hakken.security.model.LoginResponse;
import uk.co.vurt.hakken.R;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

public class AuthenticatorActivity extends AccountAuthenticatorActivity{

	/** The Intent flag to confirm credentials. **/
    public static final String PARAM_CONFIRM_CREDENTIALS = "confirmCredentials";

    /** The Intent extra to store password. **/
    public static final String PARAM_PASSWORD = "password";

    /** The Intent extra to store username. **/
    public static final String PARAM_USERNAME = "username";

    /** The Intent extra to store authtoken type. **/
    public static final String PARAM_AUTHTOKEN_TYPE = "authtokenType";
    
    /** The tag used to log to adb console. **/
    private static final String TAG = "AuthenticatorActivity";

    private AccountManager accountManager;

    /** Keep track of the login task so it can be cancelled if requested */
    private UserLoginTask authTask = null;
    
    /** Keep track of the progress dialog so we can dismiss it */
    private ProgressDialog progressDialog = null;
    
    /**
     * If set we are just checking that the user knows their credentials; this
     * doesn't cause the user's password to be changed on the device.
     */
    private Boolean confirmCredentials = false;
    
    private TextView message;

    private String password;

    private EditText passwordEdit;

    /** Was the original caller asking for an entirely new account? */
    protected boolean requestNewAccount = false;

    private String username;

    private EditText usernameEdit;
    
    private boolean returnToStart = false;
    
    public void onCreate(Bundle icicle) {

        Log.i(TAG, "onCreate(" + icicle + ")");
        super.onCreate(icicle);
        accountManager = AccountManager.get(this);
        Log.i(TAG, "loading data from Intent");
        final Intent intent = getIntent();
        returnToStart = intent.getBooleanExtra(DispatcherActivity.RETURN_TO_START_KEY, false);
        username = intent.getStringExtra(PARAM_USERNAME);
//        mAuthtokenType = intent.getStringExtra(PARAM_AUTHTOKEN_TYPE);
        requestNewAccount = username == null;
        confirmCredentials = intent.getBooleanExtra(PARAM_CONFIRM_CREDENTIALS, false);
        Log.i(TAG, "    request new: " + requestNewAccount);
        requestWindowFeature(Window.FEATURE_LEFT_ICON);
        setContentView(R.layout.login_activity);
        getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
            android.R.drawable.ic_dialog_alert);
        message = (TextView) findViewById(R.id.message);
        usernameEdit = (EditText) findViewById(R.id.username_edit);
        passwordEdit = (EditText) findViewById(R.id.password_edit);
        if(username != null && username.length() > 0){
        	usernameEdit.setText(username);
        }
        message.setText(getMessage());
    }
    
    protected Dialog onCreateDialog(int id) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(getText(R.string.ui_activity_authenticating));
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                Log.i(TAG, "dialog cancel has been invoked");
                if (authTask != null) {
                	authTask.cancel(true);
                }
            }
        });
        // We save off the progress dialog in a field so that we can dismiss
        // it later. We can't just call dismissDialog(0) because the system
        // can lose track of our dialog if there's an orientation change.
        progressDialog = dialog;
        return dialog;
    }
    
    /**
     * Handles onClick event on the Submit button. Sends username/password to
     * the server for authentication.
     * 
     * @param view The Submit button for which this method is invoked
     */
    public void handleLogin(View view) {
        if (requestNewAccount) {
            username = usernameEdit.getText().toString();
        }
        password = passwordEdit.getText().toString();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            message.setText(getMessage());
        } else {
            // Start authenticating...
            showProgress();
            authTask = new UserLoginTask();
            authTask.execute();
        }
    }
    
    /**
     * Called when response is received from the server for confirm credentials
     * request. See onAuthenticationResult(). Sets the
     * AccountAuthenticatorResult which is sent back to the caller.
     * 
     * @param the confirmCredentials result.
     */
    private void finishConfirmCredentials(boolean result) {
        Log.i(TAG, "finishConfirmCredentials()");
        final Account account = new Account(username, Constants.ACCOUNT_TYPE);
        accountManager.setPassword(account, password);
        final Intent intent = new Intent();
        intent.putExtra(AccountManager.KEY_BOOLEAN_RESULT, result);
        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
        if(returnToStart){
        	startActivity(new Intent(this, DispatcherActivity.class));
        }
        finish();
    }
    
    /**
     * Called when response is received from the server for authentication
     * request. See onAuthenticationResult(). Sets the
     * AccountAuthenticatorResult which is sent back to the caller. Also sets
     * the authToken in AccountManager for this account.
     * 
     * @param the confirmCredentials result.
     */
    private void finishLogin() {

        Log.i(TAG, "finishLogin()");
        final Account account = new Account(username, Constants.ACCOUNT_TYPE);
        if (requestNewAccount) {
            accountManager.addAccountExplicitly(account, password, null);
        } else {
            accountManager.setPassword(account, password);
        }
        // Set sync for this account.
        ContentResolver.setSyncAutomatically(account, TaskProvider.AUTHORITY, true);
        
        final Intent intent = new Intent();
        intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, username);
        intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, Constants.ACCOUNT_TYPE);
        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
        if(returnToStart){
        	startActivity(new Intent(this, DispatcherActivity.class));
        }
        finish();
    }
    
    /**
     * Hides the progress UI for a lengthy operation.
     */
    private void hideProgress() {
    	if(progressDialog != null){
    		progressDialog.dismiss();
    		progressDialog = null;
    	}
    }

    /**
     * Called when the authentication process completes (see attemptLogin()).
     */
    public void onAuthenticationResult(String authToken) {

    	boolean success = ((authToken != null) && (authToken.length() > 0));
        Log.i(TAG, "onAuthenticationResult(" + success + ")");
        
        //Task is complete to get rid of it
        authTask = null;
        
        // Hide the progress dialog
        hideProgress();
        if (success) {
            if (!confirmCredentials) {
                finishLogin();
            } else {
                finishConfirmCredentials(true);
            }
        } else {
            Log.e(TAG, "onAuthenticationResult: failed to authenticate");
            if (requestNewAccount) {
                // "Please enter a valid username/password.
                message.setText(getText(R.string.login_activity_loginfail_text_both));
            } else {
                // "Please enter a valid password." (Used when the
                // account is already in the database but the password
                // doesn't work.)
                message.setText(getText(R.string.login_activity_loginfail_text_pwonly));
            }
        }
    }
    
    public void onAuthenticationCancel() {
    	authTask = null;
    	hideProgress();
    }
    
    /**
     * Returns the message to be displayed at the top of the login dialog box.
     */
    private CharSequence getMessage() {
        getString(R.string.label);
        if (TextUtils.isEmpty(username)) {
            // If no username, then we ask the user to log in using an
            // appropriate service.
            final CharSequence msg = getText(R.string.login_activity_newaccount_text);
            return msg;
        }
        if (TextUtils.isEmpty(password)) {
            // We have an account but no password
            return getText(R.string.login_activity_loginfail_text_pwmissing);
        }
        return null;
    }

    /**
     * Shows the progress UI for a lengthy operation.
     */
    private void showProgress() {
        showDialog(0);
    }
    
    /**
     * Represents an asynchronous task used to authenticate a user against the
     * SampleSync Service
     */
    public class UserLoginTask extends AsyncTask<Void, Void, LoginResponse> {

        @Override
        protected LoginResponse doInBackground(Void... params) {
            // We do the actual work of authenticating the user
            // in the NetworkUtilities class.
            try {
                return NetworkUtilities.authenticate(AuthenticatorActivity.this, username, password);
            } catch (Exception ex) {
                Log.e(TAG, "UserLoginTask.doInBackground: failed to authenticate");
                Log.i(TAG, ex.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(final LoginResponse response) {
            // On a successful authentication, call back into the Activity to
            // communicate the authToken (or null for an error).
        	String authToken = null;
        	if(response.isSuccess()){
        		authToken = response.getToken();
        	}
            onAuthenticationResult(authToken);
        }

        @Override
        protected void onCancelled() {
            // If the action was canceled (by the user clicking the cancel
            // button in the progress dialog), then call back into the
            // activity to let it know.
            onAuthenticationCancel();
        }
    }
}
