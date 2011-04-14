/**
 * 
 */
package alaus.radaras;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * @author Vincentas
 *
 */
public class TestReceiver extends BroadcastReceiver {

	/* (non-Javadoc)
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		Toast.makeText(arg0, "Wifi", Toast.LENGTH_SHORT);

	}

}
