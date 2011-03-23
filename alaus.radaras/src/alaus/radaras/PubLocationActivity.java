/**
 * 
 */
package alaus.radaras;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import alaus.radaras.map.overlay.PubOverlay;
import alaus.radaras.map.overlay.PubOverlayItem;
import alaus.radaras.service.BeerRadar;
import alaus.radaras.service.BeerRadarSqlite;
import alaus.radaras.service.LocationProvider;
import alaus.radaras.service.model.Pub;
import alaus.radaras.utils.Utils;
import android.location.Location;
import android.os.Bundle;
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
public class PubLocationActivity extends MapActivity  implements Observer {
	
	public static final String BRAND_ID = "brandId";
	
	public static final String TAG_ID = "tagId";
	
	public static final String COUNTRY_ID = "countryId";

	private MyLocationOverlay locationOverlay;
	
	private PubOverlay pubOverlay;
	
	private BeerRadar beerRadar;

	@Override
	protected void onCreate(Bundle bunble) {
		super.onCreate(bunble);
		beerRadar = new BeerRadarSqlite(this);
		getLocationProvider().subscribe(this);
		setContentView(R.layout.map);
		locationOverlay = new MyLocationOverlay(this, getMapView());
		locationOverlay.enableMyLocation();
		
		getMapView().getOverlays().add(locationOverlay);		
		getMapView().setBuiltInZoomControls(true);

		setupMap(getLocationProvider().getLastKnownLocation());		

		setTitle();
	}

	@Override
	protected void onPause() {
		super.onPause();
		locationOverlay.disableMyLocation();
		getLocationProvider().unSubscribe(this);
	}
	


	private MapView getMapView() {
		return (MapView) findViewById(R.id.mapView);
	}	


	@Override
	protected void onDestroy() {
		super.onDestroy();
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


	
	private void setTitle() {
		String caption = "";
		
		if (getBrandId() != null) {
			caption = beerRadar.getBrand(getBrandId()).getTitle();
		} else if (getCountryId() != null) {
			caption = beerRadar.getCountry(getCountryId()).getName();
		} else if (getTagId() != null) {
			caption = beerRadar.getTag(getTagId()).getTitle();
		}

		TextView mapCaption = (TextView) findViewById(R.id.mapCaption);
		mapCaption.setText(caption);
	}

	private synchronized void populateMap(Location location) {			
		List<Pub> pubs;
		
		if (getBrandId() != null) {
			pubs = beerRadar.getPubsByBrandId(getBrandId(), location);
		} else if (getCountryId() != null) {
			pubs = beerRadar.getPubsByCountry(getCountryId(), location);
		} else if (getTagId() != null) {
			pubs = beerRadar.getPubsByTag(getTagId(), location);
		} else {
			pubs = beerRadar.getNearbyPubs(location);
		}

		if (pubs == null) {
			pubs = new ArrayList<Pub>();
		}

		if(pubOverlay == null)
			pubOverlay = new PubOverlay(getResources().getDrawable(R.drawable.pin), this, getMapView());
		pubOverlay.clean();
		
		for (Pub pub : pubs) {
			pubOverlay.addOverlay(new PubOverlayItem(pub));
		}
		

		getMapView().getOverlays().add(pubOverlay);
	}

	/*
	 * Basic Map setup
	 */
	private void setupMap(Location location) {
	
//		Log.i("GimeLocation", "setupMap ");

		MapController mapController = getMapView().getController();

		// Zoom to span from the list of points
		mapController.zoomToSpan(30000, 30000);

		// Animate to the center cluster of points
		if(location != null) {
			mapController.animateTo(Utils.geoPoint(location));
		}
		
		populateMap(location);
	}

	@Override
	public void update(Observable observable, Object data) {
		// TODO Auto-generated method stub
		setupMap((Location)data);
	}
	
	private LocationProvider getLocationProvider() {
		return ((BeerRadarApp)getApplication()).getLocationProvider();
	}

	
	@Override
	protected void onResume() {
		getLocationProvider().subscribe(this);
		setupMap(getLocationProvider().getLastKnownLocation());
		super.onResume();
		Toast.makeText(PubLocationActivity.this, getString(R.string.waiting_for_location), Toast.LENGTH_LONG).show();
		//Log.i("GimeLocation", "onResume ");
	}
	
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

}
