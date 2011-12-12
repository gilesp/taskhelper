package uk.co.vurt.taskhelper.activities;

import uk.co.vurt.taskhelper.Constants;
import uk.co.vurt.taskhelper.authenticator.AuthenticatorActivity;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class CheckAccountExistsActivity extends Activity {

	protected AccountManager accountManager;
	
	/**
	 * Check to see if there is an existing TaskHelper account registered.
	 * If so, use that account.
	 * If not, invoke the account setup activity
	 */
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		accountManager = AccountManager.get(this);
		Account[] accounts = accountManager.getAccountsByType(Constants.ACCOUNT_TYPE); //retrieve all TaskHelper accounts
		if(accounts.length <= 0){
			//no accounts registered, invoke registration
			startActivity(new Intent(this, AuthenticatorActivity.class));
		} else {
			//Account found, so carry on as normal.
			startActivity(new Intent(this, JobList.class));
		}
		
	}
}
