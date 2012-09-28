package uk.co.vurt.hakken.syncadapter;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SyncService extends Service {

	private static final Object syncAdapterLock = new Object();
	private static SyncAdapter syncAdapter = null;
	
	public void onCreate(){
		super.onCreate();
		synchronized(syncAdapterLock){
			if(syncAdapter == null){
				syncAdapter = new SyncAdapter(getApplicationContext(), true);
			}
		}
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		return syncAdapter.getSyncAdapterBinder();
	}

}
