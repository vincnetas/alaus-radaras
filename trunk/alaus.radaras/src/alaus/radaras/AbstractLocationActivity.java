package alaus.radaras;

import java.util.Observable;
import java.util.Observer;

import alaus.radaras.service.LocationProvider.LocationPrecision;
import alaus.radaras.utils.Utils;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Class for managing Activities that need location based info.
 * Currently solution is not the most elegant one, but...
 * @author gytis
 *
 */
public abstract class AbstractLocationActivity  extends Activity  implements Observer {

	/**
	 * Indicates if current activity is subscribed to location provider.
	 */
	private boolean isSubscribed = false;
	
	/**
	 * Method which will be called when location update is available.
	 * @param location
	 */
    protected abstract void locationUpdated(Location location);
    
    /**
     * Indicates what precision this Activity requires. Override if you want to change it.
     * Default - ANY, means any precision is good enough.
     * This variable will be used when determining if activity need to listen to location updates after
     * initial value was received.
     * @return
     */
    protected LocationPrecision getLocationPrecission() {
    	return LocationPrecision.ANY;
    }
    
    /**
     * Indicates if we need continuous location updates.
     * False means that there will be enough 
     * if we receive single update that matches our  LocationPrecision.
     * @return
     */
    protected boolean continuousUpdates() {
    	return false;
    }
    
    @Override
	public void update(Observable observable, Object data) {
    	Location location = (Location)data;
    	locationUpdated(location);
    	if(((BeerRadarApp)getApplication()).getLocationProvider().locationIsAccurateEnough(getLocationPrecission(),location)) {
    		if(!continuousUpdates()) {
    			((BeerRadarApp)getApplication()).removeLocationUpdates(this);
    		}

    	}
	}

	@Override
	protected void onPause() {
		if(isSubscribed) {
			((BeerRadarApp)getApplication()).removeLocationUpdates(this);
			isSubscribed = false;
		}
		super.onPause();

	}

	@Override
	protected void onResume() {
		BeerRadarApp app = ((BeerRadarApp)getApplication());
		Location location = app.getLastKnownLocation();
		if(location == null || continuousUpdates() || 
				!app.getLocationProvider().locationIsAccurateEnough(getLocationPrecission(), location)) {
			isSubscribed = true;
			app.requestLocationUpdates(this);
		}
		locationUpdated(app.getLastKnownLocation());
		super.onResume();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.settings:
	    	  startActivity(new Intent(this, SettingsActivity.class));
	        return true;
	    case R.id.callTaxi:
	    	startActivity(new Intent(this, TaxiListActivity.class));
	    	return true;
	    case R.id.submitPub:
	    	BeerRadarApp app = ((BeerRadarApp)getApplication());
	    	Utils.showPubSubmitDialog(this, app.getLastKnownLocation());
	    	return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
}
