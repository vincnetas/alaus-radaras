/**
 * 
 */
package alaus.radaras.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * @author Vincentas
 *
 */
public class UpdateService extends Service {

    public static final String UPDATE_SOURCE = "updateSource";

	public static final String UPDATE_STATUS = "alaus.radaras.service.UPDATE_STATUS";
    
	private final IBinder binder = new LocalBinder();
    
    public class LocalBinder extends Binder {
        UpdateService getService() {
            return UpdateService.this;
        }
    }
    
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

	/* (non-Javadoc)
     * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
     */
    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        new UpdateTask(this, new BeerRadarSqlite(this)) {
        	
			/* (non-Javadoc)
			 * @see android.os.AsyncTask#onPreExecute()
			 */
			@Override
			protected void onPreExecute() {
				super.onPreExecute();

				Intent intent = new Intent(UPDATE_STATUS);
				intent.putExtra(UPDATE_STATUS, true);
				
				sendBroadcast(intent);
			}

			/* (non-Javadoc)
			 * @see alaus.radaras.service.UpdateTask#onPostExecute(java.lang.Integer)
			 */
			@Override
			protected void onPostExecute(Integer result) {				
				super.onPostExecute(result);
				
				Intent intent = new Intent(UPDATE_STATUS);
				intent.putExtra(UPDATE_STATUS, false);
				
				sendBroadcast(intent);

				stopSelf(startId);
			}            	
        }.execute(intent.getExtras().getString(UPDATE_SOURCE));
        
        return START_STICKY;
    }    
}
