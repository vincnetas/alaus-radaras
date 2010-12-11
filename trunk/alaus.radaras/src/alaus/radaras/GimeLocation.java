/**
 * 
 */
package alaus.radaras;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import alaus.radaras.dao.BeerRadarDao;
import alaus.radaras.dao.model.Pub;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

/**
 * @author Vincentas
 * based on : http://mobiforge.com/developing/story/using-google-maps-android
 */
public class GimeLocation extends MapActivity implements Observer {

	public static final String BRAND_ID = "brandId";
	
	private BeerRadarDao beerRadarDao;
	
	/* (non-Javadoc)
	 * @see com.google.android.maps.MapActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle bunble) {
		super.onCreate(bunble);

		setContentView(R.layout.map);
		
		List<Overlay> mapOverlays = getMapView().getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.icon);
		PubOverlay pubsOverlay = new PubOverlay(drawable, this);
		
		List<Pub> pubs = getBeerRadarDao().getPubsByBrandId(getBrandId());
		for (Pub pub : pubs) {
			pubsOverlay.addOverlay(new PubOverlayItem(pub));						
		}	
		
		mapOverlays.add(pubsOverlay);
	}
	
	private MapView getMapView() {
		return (MapView) findViewById(R.id.mapView);
	}
	
	private String getBrandId() {
		return getIntent().getExtras().getString(BRAND_ID);
	}
	
	/* (non-Javadoc)
	 * @see com.google.android.maps.MapActivity#isRouteDisplayed()
	 */
	@Override
	protected boolean isRouteDisplayed() {
		return false;
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

	/**
	 * @return the beerRadarDao
	 */
	public BeerRadarDao getBeerRadarDao() {
		return BeerRadarDao.getInstance();
	}

	/**
	 * @param beerRadarDao the beerRadarDao to set
	 */
	public void setBeerRadarDao(BeerRadarDao beerRadarDao) {
		this.beerRadarDao = beerRadarDao;
	}
	
	
}
