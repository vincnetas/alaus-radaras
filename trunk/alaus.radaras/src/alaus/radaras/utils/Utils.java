/**
 * 
 */
package alaus.radaras.utils;

import android.location.Location;

import com.google.android.maps.GeoPoint;

/**
 * @author Vincentas
 *
 */
public class Utils {

	public static GeoPoint geoPoint(Location location) {
		return new GeoPoint((int) (location.getLatitude() * 1e6), (int) (location.getLongitude() * 1e6));
	}

	public static GeoPoint geoPoint(alaus.radaras.dao.model.Location location) {
		return new GeoPoint((int) (location.getLatitude() * 1e6), (int) (location.getLongtitude() * 1e6));
	}
}
