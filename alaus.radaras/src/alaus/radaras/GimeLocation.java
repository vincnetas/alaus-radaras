/**
 * 
 */
package alaus.radaras;

import java.util.ArrayList;
import java.util.List;

import alaus.radaras.dao.BeerRadarDao;
import alaus.radaras.dao.model.Pub;
import alaus.radaras.utils.Utils;
import android.content.Context;
import android.location.Criteria;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

/**
 * @author Vincentas based on :
 *         http://mobiforge.com/developing/story/using-google-maps-android
 */
public class GimeLocation extends MapActivity {

	public static final String BRAND_ID = "brandId";
	
	public static final String TAG_ID = "tagId";
	
	public static final String COUNTRY_ID = "countryId";

	private BeerRadarDao beerRadarDao;

	private PubOverlay pubOverlay;
	
	private MyLocationOverlay locationOverlay;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.android.maps.MapActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle bunble) {
		super.onCreate(bunble);
		
		setContentView(R.layout.map);
		pubOverlay = new PubOverlay(getResources().getDrawable(R.drawable.bokalas), this, getMapView());

		getMapView().getOverlays().add(pubOverlay);
		locationOverlay = new MyLocationOverlay(this, getMapView());
		locationOverlay.enableMyLocation();
		
		getMapView().getOverlays().add(locationOverlay);		
		getMapView().setBuiltInZoomControls(true);
		
		fakeProvider();
		
		locationOverlay.runOnFirstFix(new Runnable() {
			
			@Override
			public void run() {
				setupMap();				
			}
		});
		
	}

	/* (non-Javadoc)
	 * @see com.google.android.maps.MapActivity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
		locationOverlay.disableMyLocation();
	}
	
	private void fakeProvider() {
		final Location location = new Location(LocationManager.GPS_PROVIDER);
		location.setLongitude(25.289261);
		location.setLatitude(54.67527);
		
		final LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
				
		locationManager.addTestProvider(LocationManager.GPS_PROVIDER, false, false, false, false, false, false, false, Criteria.POWER_LOW, Criteria.ACCURACY_FINE);
		locationManager.setTestProviderEnabled(LocationManager.GPS_PROVIDER, true);
		locationManager.setTestProviderStatus(LocationManager.GPS_PROVIDER, GpsStatus.GPS_EVENT_FIRST_FIX, null, System.currentTimeMillis());
		locationManager.setTestProviderLocation(LocationManager.GPS_PROVIDER, location);
		
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				while (keepRunning) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					locationManager.setTestProviderStatus(LocationManager.GPS_PROVIDER, GpsStatus.GPS_EVENT_FIRST_FIX, null, System.currentTimeMillis());
				}
			}
		};
		
		new Thread(runnable).start();
	}
	
	private boolean keepRunning = true;

	private MapView getMapView() {
		return (MapView) findViewById(R.id.mapView);
	}	

	/* (non-Javadoc)
	 * @see com.google.android.maps.MapActivity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		keepRunning = false;
	}

	private String getString(String key) {
		String result = null;

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			result = extras.getString(key);
		}

		return result;
		
	}

	private String getBrandId() {
		return getString(BRAND_ID);
	}
	
	private String getTagId() {
		return getString(TAG_ID);
	}
	
	private String getCountryId() {
		return getString(COUNTRY_ID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.android.maps.MapActivity#isRouteDisplayed()
	 */
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	private synchronized void populateMap(Location location) {
		alaus.radaras.dao.model.Location loc = new alaus.radaras.dao.model.Location(location.getLongitude(), location.getLatitude());
		List<Pub> pubs;

		if (getBrandId() != null) {
			pubs = getBeerRadarDao().getPubsByBrandId(getBrandId(), loc);
		} else if (getCountryId() != null) {
			pubs = getBeerRadarDao().getPubsByBrandId(getCountryId()));
		} else if (getTagId() != null) {
			pubs = getBeerRadarDao().getPubsByBrandId(getTagId(), loc);
		} else {
			pubs = getBeerRadarDao().getNearbyPubs(loc);
		}

		if (pubs == null) {
			pubs = new ArrayList<Pub>();
		}

		for (Pub pub : pubs) {
			pubOverlay.addOverlay(new PubOverlayItem(pub));
		}
	}

	/**
	 * @return the beerRadarDao
	 */
	public BeerRadarDao getBeerRadarDao() {
		return BeerRadarDao.getInstance(this);
	}

	/**
	 * @param beerRadarDao
	 *            the beerRadarDao to set
	 */
	public void setBeerRadarDao(BeerRadarDao beerRadarDao) {
		this.beerRadarDao = beerRadarDao;
	}

	/*
	 * Basic Map setup
	 */
	private void setupMap() {
		Location location = locationOverlay.getLastFix();
		
		populateMap(location);

		MapController mapController = getMapView().getController();

		// Zoom to span from the list of points
		mapController.zoomToSpan(100000, 100000);

		// Animate to the center cluster of points
		mapController.animateTo(Utils.geoPoint(location));

	}
}
