/**
 * 
 */
package alaus.radaras;

import alaus.radaras.service.UpdateService;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

/**
 * @author Vincentas
 *
 */
public class SyncSchedulingReceiver extends BroadcastReceiver {

    private static final long FIVE_MINUTES = 1000 * 60 * 5;
    
	/* (non-Javadoc)
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
	    if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED) || 
	            intent.getAction().equals(ConnectivityManager.ACTION_BACKGROUND_DATA_SETTING_CHANGED)) {

            PendingIntent pendingIntent = PendingIntent.getService(
                    context, 
                    0, 
                    new Intent(context, UpdateService.class), 
                    PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
	        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	        if (connectivityManager.getBackgroundDataSetting()) {
	            /*
	             * If background data is enabled, schedule daily update.  
	             */	                    	            
	            alarmManager.setInexactRepeating(
	                    AlarmManager.RTC, 
	                    System.currentTimeMillis() + FIVE_MINUTES, 
	                    AlarmManager.INTERVAL_DAY, 
	                    pendingIntent);
	        } else {
	            /*
	             * Cancel scheduled daily update.
	             */
	            alarmManager.cancel(pendingIntent);
	        }	        
	    }    
	}
}
