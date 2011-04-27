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
			 * @see alaus.radaras.service.UpdateTask#onPostExecute(java.lang.Integer)
			 */
			@Override
			protected void onPostExecute(Integer result) {				
				super.onPostExecute(result);
				stopSelf(startId);
			}            	
        }.execute("www.alausradaras.lt");
        
        return START_STICKY;
    }    
}
