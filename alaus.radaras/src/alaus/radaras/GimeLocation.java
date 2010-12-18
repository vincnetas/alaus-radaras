/**
 * 
 */
package alaus.radaras;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


import alaus.radaras.service.BeerRadar;
import alaus.radaras.service.model.Pub;
import alaus.radaras.utils.Utils;
import android.content.Context;
import android.location.Criteria;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

/**
 * @author Vincentas based on :
 *         http://mobiforge.com/developing/story/using-google-maps-android
 */
public class GimeLocation extends MapActivity  implements Observer {

	private LocationProvider locationProvider;
	
	public static final String BRAND_ID = "brandId";
	
	public static final String TAG_ID = "tagId";
	
	public static final String COUNTRY_ID = "countryId";

	private BeerRadar beerRadarDao;

	private MyLocationOverlay locationOverlay;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.android.maps.MapActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle bunble) {
		super.onCreate(bunble);
		getLocationProvider().subscribe(this);
		setContentView(R.layout.map);
		locationOverlay = new MyLocationOverlay(this, getMapView());
		locationOverlay.enableMyLocation();
		
		getMapView().getOverlays().add(locationOverlay);		
		getMapView().setBuiltInZoomControls(true);
		
		//Log.i("GimeLocation", "onCreate ");
		
		//fakeProvider();
		
//		locationOverlay.runOnFirstFix(new Runnable() {
//			
//			@Override
//			public void run() {

					setupMap(getLocationProvider().getLastKnownLocation());		
//			}
//		});
		
		setTitle();
	}

	/* (non-Javadoc)
	 * @see com.google.android.maps.MapActivity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
		locationOverlay.disableMyLocation();
		killLocationProvider();
	}
	
////	private void fakeProvider() {
//	
//		
//		final LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//				
//		locationManager.addTestProvider(LocationManager.GPS_PROVIDER, false, false, false, false, false, false, false, Criteria.POWER_LOW, Criteria.ACCURACY_FINE);
//		locationManager.setTestProviderEnabled(LocationManager.GPS_PROVIDER, true);
//		locationManager.setTestProviderStatus(LocationManager.GPS_PROVIDER, GpsStatus.GPS_EVENT_FIRST_FIX, null, System.currentTimeMillis());
//		locationManager.setTestProviderLocation(LocationManager.GPS_PROVIDER, location);
//		
//		Runnable runnable = new Runnable() {
//
//			@Override
//			public void run() {
//				while (keepRunning) {
//					try {
//						Thread.sleep(1000);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//					locationManager.setTestProviderStatus(LocationManager.GPS_PROVIDER, GpsStatus.GPS_EVENT_FIRST_FIX, null, System.currentTimeMillis());
//				}
//			}
//		};
//		
//		new Thread(runnable).start();
//	}
	
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
	
	private void setTitle() {
		String caption = "";
		BeerRadar dao = getBeerRadarDao();
		
		if (getBrandId() != null) {
			caption = dao.getBrand(getBrandId()).getTitle();
		} else if (getCountryId() != null) {
			caption = dao.getCountry(getCountryId()).getName();
		} else if (getTagId() != null) {
			caption = dao.getTag(getTagId()).getTitle();
		}

		TextView mapCaption = (TextView) findViewById(R.id.mapCaption);
		mapCaption.setText(caption);
	}

	private synchronized void populateMap(Location location) {
		alaus.radaras.service.model.Location loc = new alaus.radaras.service.model.Location();
		if(location != null) {
		 loc = new alaus.radaras.service.model.Location(location.getLongitude(), location.getLatitude());
		}
			
		List<Pub> pubs;
		BeerRadar dao = getBeerRadarDao();
		
		if (getBrandId() != null) {
			pubs = dao.getPubsByBrandId(getBrandId(), loc);
		} else if (getCountryId() != null) {
			pubs = dao.getPubsByCountry(getCountryId(), loc);
		} else if (getTagId() != null) {
			pubs = dao.getPubsByTag(getTagId(), loc);
		} else {
			pubs = dao.getNearbyPubs(loc);
		}

		if (pubs == null) {
			pubs = new ArrayList<Pub>();
		}

		PubOverlay pubOverlay = new PubOverlay(getResources().getDrawable(R.drawable.pin), this, getMapView());
		
		for (Pub pub : pubs) {
			pubOverlay.addOverlay(new PubOverlayItem(pub));
		}
		
		getMapView().getOverlays().add(pubOverlay);
	}

	/**
	 * @return the beerRadarDao
	 */
	public BeerRadar getBeerRadarDao() {
		return BeerRadar.getInstance(this);
	}

	/**
	 * @param beerRadarDao
	 *            the beerRadarDao to set
	 */
	public void setBeerRadarDao(BeerRadar beerRadarDao) {
		this.beerRadarDao = beerRadarDao;
	}

	/*
	 * Basic Map setup
	 */
	private void setupMap(Location location) {
	
//		Log.i("GimeLocation", "setupMap ");
		populateMap(location);

		MapController mapController = getMapView().getController();

		// Zoom to span from the list of points
		mapController.zoomToSpan(30000, 30000);

		// Animate to the center cluster of points
		if(location != null) {
			mapController.animateTo(Utils.geoPoint(location));
		}

	}

	@Override
	public void update(Observable observable, Object data) {
		// TODO Auto-generated method stub
		setupMap((Location)data);
	}
	
	private LocationProvider getLocationProvider() {
		if (locationProvider == null) {
			locationProvider = initLocationProvider();
			//Log.i("GimeLocation", "initLocationProvider ");
		}
		return locationProvider;
	}

	private LocationProvider initLocationProvider() {
		LocationProvider provider = new LocationProvider(getBaseContext());
		return provider;
	}

	private void killLocationProvider() {
		if (locationProvider != null) {
			locationProvider.deleteObserver(this);
		//	Log.i("GimeLocation", "killLocationProvider ");
		}
		getLocationProvider().killAll();
	};
	
	@Override
	protected void onResume() {
		getLocationProvider().subscribe(this);
		setupMap(getLocationProvider().getLastKnownLocation());
		super.onResume();
		Toast.makeText(GimeLocation.this, "Palaukite, bandau nustatyti Jūsų vietą...", Toast.LENGTH_LONG).show();
		//Log.i("GimeLocation", "onResume ");
	}

}
