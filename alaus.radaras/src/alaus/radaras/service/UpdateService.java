/**
 * 
 */
package alaus.radaras.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
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
     * @see android.app.Service#onStart(android.content.Intent, int)
     */
    @Override
    public void onStart(Intent intent, int startId) {
        handleCommand();
    }

    /* (non-Javadoc)
     * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handleCommand();
        
        return START_STICKY;
    }    
    
    private void handleCommand() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getBackgroundDataSetting()) {
            new UpdateTask(this, new BeerRadarSqlite(this)).execute("www.alausradaras.lt");
        }
    }
}
