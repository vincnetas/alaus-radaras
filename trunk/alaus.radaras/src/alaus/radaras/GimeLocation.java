/**
 * 
 */
package alaus.radaras;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

/**
 * @author Vincentas
 * based on : http://mobiforge.com/developing/story/using-google-maps-android
 */
public class GimeLocation extends MapActivity implements Observer {

	/* (non-Javadoc)
	 * @see com.google.android.maps.MapActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle buicicle) {
		super.onCreate(buicicle);
		
		setContentView(R.layout.map);
		
		MapView mapView = (MapView) findViewById(R.id.mapView);
		
		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.icon);
		PubOverlay overlay = new PubOverlay(drawable, this);
		
		GeoPoint point = new GeoPoint(19240000,-99120000);
		OverlayItem overlayitem = new OverlayItem(point, "Hola, Mundo!", "I'm in Mexico City!");
		
		overlay.addOverlay(overlayitem);
		mapOverlays.add(overlay);
	}
	
	/* (non-Javadoc)
	 * @see com.google.android.maps.MapActivity#isRouteDisplayed()
	 */
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(event);
	}
	
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

	@Override
	public void update(Observable observable, Object data) {
		// TODO Auto-generated method stub
		
	}
}
