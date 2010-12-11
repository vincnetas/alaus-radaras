package alaus.radaras;

import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class LocationProvider extends Observable implements LocationListener{

	private Context context;
	private LocationManager manager;
	
	private static final int GPS_MIN_DISTANCE = 10;
	private static final int GPS_MIN_UPDATE_TIME = 1000 * 15;//15 seconds;
	private static final int NETWORK_MIN_DISTANCE = 30;
	private static final int NETWORK_MIN_UPDATE_TIME = 2 * 60 * 1000; //2 minutes
	private static final int ALLOWED_TIME_THRESHOLD = 5 * 60 * 1000; //5 minutes
	private static final Boolean DEBUG = false;
	private Location lastKnownLocation = null;
	private Boolean hasSubscriptions = false;
	
	public LocationProvider(Context context) {
		this.context = context;
		initUpdateRequester();
        Log.i("BeerLocationProvider", "constructor ");
	}
	
	private void initUpdateRequester() {
		 Log.i("BeerLocationProvider", "initUpdateRequester ");

		 LocationManager locMng = getLocationManager();
		 if(!hasSubscriptions) {
			 List<String> locationProviders  = locMng.getProviders(true);
			 for(String provider : locationProviders)
			  {
				 if(provider == "gps") {
					 locMng.requestLocationUpdates(provider, GPS_MIN_DISTANCE, GPS_MIN_UPDATE_TIME, this);
				 }
				 else {
					 locMng.requestLocationUpdates(provider, NETWORK_MIN_DISTANCE, NETWORK_MIN_UPDATE_TIME, this);
				 }
			 }
		 	//hasSubscriptions  = true;
		 }
		 
		
	}

	public void killAll() {
		getLocationManager().removeUpdates(this);
		Log.i("BeerLocationProvider", "received killall");
	}
	
	public LocationManager getLocationManager() {
		if(manager == null) {
			manager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
			hasSubscriptions = false;
    	}
    	return manager;
	}
	
	
	
	@Override
	public void onLocationChanged(Location newLocation) {
       Log.d("BeerLocationProvider", "onLocationChanged: " + newLocation);
        updateLocation(newLocation);
	}

	synchronized public void onBestLocationChanged(Location location) {
        Log.d("BeerLocationProvider", "onBestLocationChanged: " + location);
        lastKnownLocation = location;
        setChanged();
        notifyObservers(location);
    }
	private void updateLocation(Location newLocation) {

		 if (DEBUG) {
	            Log.d("BeerLocationProvider", "updateLocation: Old: " + lastKnownLocation);
	            Log.d("BeerLocationProvider", "updateLocation: New: " + newLocation);
	        }

	        // Cases where we only have one or the other.
	        if (newLocation != null && lastKnownLocation == null) {
	            if (DEBUG) Log.d("BeerLocationProvider", "updateLocation: Null last location");
	            onBestLocationChanged(newLocation);
	            return;

	        } else if (newLocation == null) {
	            if (DEBUG) Log.d("BeerLocationProvider", "updated location is null, doing nothing");
	            return;
	        }

	        long now = new Date().getTime();
	        long locationUpdateDelta = now - newLocation.getTime();
	        long lastLocationUpdateDelta = now - lastKnownLocation.getTime();
	        boolean locationIsInTimeThreshold = locationUpdateDelta <= ALLOWED_TIME_THRESHOLD;
	        boolean lastLocationIsInTimeThreshold = lastLocationUpdateDelta <= ALLOWED_TIME_THRESHOLD;
	        boolean locationIsMostRecent = locationUpdateDelta <= lastLocationUpdateDelta;

	        boolean accuracyComparable = newLocation.hasAccuracy() || lastKnownLocation.hasAccuracy();
	        boolean locationIsMostAccurate = false;
	        if (accuracyComparable) {
	            // If we have only one side of the accuracy, that one is more
	            // accurate.
	            if (newLocation.hasAccuracy() && !lastKnownLocation.hasAccuracy()) {
	                locationIsMostAccurate = true;
	            } else if (!newLocation.hasAccuracy() && lastKnownLocation.hasAccuracy()) {
	                locationIsMostAccurate = false;
	            } else {
	                // If we have both accuracies, do a real comparison.
	                locationIsMostAccurate = newLocation.getAccuracy() <= lastKnownLocation.getAccuracy();
	            }
	        }

	        if (DEBUG) {
	            Log.d("BeerLocationProvider", "locationIsMostRecent:\t\t\t" + locationIsMostRecent);
	            Log.d("BeerLocationProvider", "locationUpdateDelta:\t\t\t" + locationUpdateDelta);
	            Log.d("BeerLocationProvider", "lastLocationUpdateDelta:\t\t" + lastLocationUpdateDelta);
	            Log.d("BeerLocationProvider", "locationIsInTimeThreshold:\t\t" + locationIsInTimeThreshold);
	            Log.d("BeerLocationProvider", "lastLocationIsInTimeThreshold:\t" + lastLocationIsInTimeThreshold);

	            Log.d("BeerLocationProvider", "accuracyComparable:\t\t\t" + accuracyComparable);
	            Log.d("BeerLocationProvider", "locationIsMostAccurate:\t\t" + locationIsMostAccurate);
	        }

	        // Update location if its more accurate and w/in time threshold or if
	        // the old location is
	        // too old and this update is newer.
	        if (accuracyComparable && locationIsMostAccurate && locationIsInTimeThreshold) {
	            onBestLocationChanged(newLocation);
	        } else if (locationIsInTimeThreshold && !lastLocationIsInTimeThreshold) {
	            onBestLocationChanged(newLocation);
	        }
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	public void subscribe(Observer obs) {
		Log.i("BeerLocationProvider", "received subscribe");
		addObserver(obs);
		initUpdateRequester();
	}

	synchronized public Location getLastKnownLocation() {
		return lastKnownLocation;
	}

}

