package alaus.radaras;

import java.util.Observer;

import alaus.radaras.service.LocationProvider;
import alaus.radaras.settings.SettingsManager;
import android.app.Application;
import android.location.Location;

public class BeerRadarApp extends Application {
	
	
		SettingsManager settings;
		LocationProvider provider;
	
	   @Override
	    public void onCreate() {
		   
		   if(provider == null) {
				provider = new LocationProvider(getBaseContext());
		   }
		   
		   if(settings == null) {
			   settings = new SettingsManager(getApplicationContext());
		   }

	   }
	  
	    public void removeLocationUpdates(Observer observer) {
	    	provider.unSubscribe(observer);
	    }
	    
	    public void requestLocationUpdates(Observer observer) {
	    	provider.subscribe(observer);
	    }

	    public Location getLastKnownLocation() {
	    	//return provider.getDefaultMockLocation();
	    	return provider.getLastKnownLocation();
	    }
	    
	    public LocationProvider getLocationProvider() {
	    	return provider;
	    }
	    
	    
	    //Anti- pattern.
	    public int getMaxDistance() {
	    	return settings.getMaxDistance();
	    }
	    
	    @Override
	    public void onTerminate() {
	    	super.onTerminate();
	    	provider.deleteObservers();
	    }


}
