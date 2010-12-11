/**
 * 
 */
package alaus.radaras;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import alaus.radaras.dao.BeerRadarDao;
import alaus.radaras.dao.model.Pub;
import alaus.radaras.utils.Utils;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

/**
 * @author Vincentas
 * based on : http://mobiforge.com/developing/story/using-google-maps-android
 */
public class GimeLocation extends MapActivity implements Observer {

	public static final String BRAND_ID = "brandId";
	
	private BeerRadarDao beerRadarDao;
	
	private PubOverlay pubOverlay;
	
	/* (non-Javadoc)
	 * @see com.google.android.maps.MapActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle bunble) {
		super.onCreate(bunble);

		   Location location = new Location(LocationManager.GPS_PROVIDER);
		   location.setLongitude(25.337219);
		   location.setLatitude(54.736515);
		   
		   Pub pub = new Pub();
		   pub.setTitle("title");
		   pub.setNotes("notes");
		   pub.setId("pubId");
		   alaus.radaras.dao.model.Location loc = new alaus.radaras.dao.model.Location(location.getLongitude(), location.getLatitude());		   
		   pub.setLocation(loc);
		
		setContentView(R.layout.map);		
		pubOverlay = new PubOverlay(getResources().getDrawable(R.drawable.icon), this);
		pubOverlay.addOverlay(new PubOverlayItem(pub));
		
		
		getMapView().getOverlays().add(pubOverlay);	        
	   getMapView().setBuiltInZoomControls(true);


	   setupMap(location);
	   
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
	
	private LocationProvider getLocationProvider() {
		if (locationProvider == null) {
			locationProvider = new LocationProvider(getBaseContext());
		}
		
		return locationProvider;
	}

	@Override
	protected void onPause() {
		getLocationProvider().deleteObserver(this);
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		getLocationProvider().subscribe(this);
//		
//		pubOverlay.clean();
//		mapPopulated = false;
//		
//		Location location = getLocationProvider().getLastKnownLocation();
//		if (location == null) {
//			showDialog(ENABLE_GPS_DIALOG);
//		} else {
//			populateMap(location);
//		}
	}
	
	private boolean mapPopulated = false;
	
	private synchronized void populateMap(Location location) {
		if (!mapPopulated) {
			String brandId = getBrandId();
			alaus.radaras.dao.model.Location loc = new alaus.radaras.dao.model.Location(location.getLongitude(), location.getLatitude());
			List<Pub> pubs;
			
			if (brandId != null) {
				pubs = getBeerRadarDao().getPubsByBrandId(brandId, loc);
			} else {
				pubs = getBeerRadarDao().getNearbyPubs(loc);
			}
			
			for (Pub pub : pubs) {
				pubOverlay.addOverlay(new PubOverlayItem(pub));						
			}
			
			mapPopulated = true;
		}
	}

	@Override
	public void update(Observable observable, Object data) {
//		populateMap((Location) data);
		setupMap((Location) data);
	}

	/**
	 * @return the beerRadarDao
	 */
	public BeerRadarDao getBeerRadarDao() {
		return BeerRadarDao.getInstance(this);
	}

	/**
	 * @param beerRadarDao the beerRadarDao to set
	 */
	public void setBeerRadarDao(BeerRadarDao beerRadarDao) {
		this.beerRadarDao = beerRadarDao;
	}
	

	/*
	 * Basic Map setup
	 */
	private void setupMap(Location location){
		
			MapController mapController = getMapView().getController();
			

		
		// Zoom to span from the list of points
		mapController.zoomToSpan(100000, 100000);

		// Animate to the center cluster of points
		mapController.animateTo(Utils.geoPoint(location));
		
	}
}
