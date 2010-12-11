package alaus.radaras;

import java.util.Observer;

import android.app.Activity;

public abstract class AbstractLocationActivity  extends Activity  implements Observer {

	private LocationProvider locationProvider;
		
	protected LocationProvider getLocationProvider() {
		if (locationProvider == null) {
			locationProvider = new LocationProvider(getBaseContext());
		}
		return locationProvider;
	}

	protected void killLocationProvider() {
		if (locationProvider != null) {
			locationProvider.deleteObserver(this);
		}
		
		getLocationProvider().killAll();
	};

	@Override
	protected void onPause() {
		killLocationProvider();
		super.onPause();
	}

	@Override
	protected void onResume() {
		getLocationProvider().subscribe(this);
		super.onResume();
	}
}
