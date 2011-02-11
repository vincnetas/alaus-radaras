package alaus.radaras.service;

import java.util.Iterator;
import java.util.List;

import alaus.radaras.service.model.MultipleLocation;
import alaus.radaras.service.model.Pub;
import alaus.radaras.service.model.SingleLocation;
import alaus.radaras.settings.SettingsManager;
import android.location.Location;
import android.util.Log;

public class LocationFilter {
	
	
	
	private static final String LOG_TAG = "BeerRadar.LocationFilter";

	public static void filterBySingleLocation(List<? extends SingleLocation> values, Location location, double maxDistance) {
		Log.d(LOG_TAG, "filterBySingleLocation Lat:" + String.valueOf(location.getLatitude()) + " Long:" + String.valueOf(location.getLongitude()) + " Dist:" + String.valueOf(maxDistance) + " Item size:" + values.size());
		if(location != null && maxDistance != SettingsManager.UNLIMITED_DISTANCE) {
			Iterator<? extends SingleLocation> i = values.iterator();
			while (i.hasNext()) {
				Object vl = i.next();
				double distance = ((SingleLocation)vl).getLocation().distanceTo(location);
					if(distance > maxDistance) {
						if(vl.getClass().equals(Pub.class)) {
							Log.d(LOG_TAG,"removing pub " + ((Pub)vl).getId() + " distance " + distance);
						}
						i.remove();
					} else {
						if(vl.getClass().equals(Pub.class)) {
							Log.d(LOG_TAG,"keeping pub " + ((Pub)vl).getId() + " distance " + distance);
						}
					}
			}
		}
	}

	public static void filterByLocations(List<? extends MultipleLocation> values,
			Location location,  double maxDistance) {

		Log.d(LOG_TAG, "filterByLocations Lat:" + String.valueOf(location.getLatitude()) + " Long:" + String.valueOf(location.getLongitude()) + " Dist:" + String.valueOf(maxDistance));

		
		if(location != null && maxDistance != SettingsManager.UNLIMITED_DISTANCE) {
			Iterator<? extends MultipleLocation> i = values.iterator();
			while (i.hasNext()) {
				boolean needToRemove = true;
				MultipleLocation item = i.next();
				for(Location loc : item.getLocations()) {
					if( location.distanceTo(loc) < maxDistance) {
						needToRemove = false;
						break;
					}
				}
				if(needToRemove) {
					i.remove();
				}
			}
		}

	}

}
