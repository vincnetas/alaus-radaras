/**
 * 
 */
package alaus.radaras.utils;

import alaus.radaras.R;
import alaus.radaras.dialogs.NewPubReportDialog;
import android.content.Context;
import android.location.Location;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;

/**
 * @author Vincentas
 *
 */
public class Utils {

	public static GeoPoint geoPoint(Location location) {
		return new GeoPoint((int) (location.getLatitude() * 1e6), (int) (location.getLongitude() * 1e6));
	}

	public static void showNoBeerAlert(Context context) {
		Toast.makeText(context, context.getString(R.string.no_beers), Toast.LENGTH_SHORT).show();
		
	}

	public static void showPubSubmitDialog(Context context,
			Location location) {
		NewPubReportDialog dialog = new NewPubReportDialog(context);
		dialog.display(location);
		
	}
}
