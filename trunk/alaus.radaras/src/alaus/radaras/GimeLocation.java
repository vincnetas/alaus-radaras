/**
 * 
 */
package alaus.radaras;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import alaus.radaras.dao.BeerRadarDao;
import alaus.radaras.dao.model.Pub;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;

import com.google.android.maps.MapActivity;
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

		setContentView(R.layout.map);		
//		pubOverlay = new PubOverlay(getResources().getDrawable(R.drawable.icon), this);
//		getMapView().getOverlays().add(pubOverlay);
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
		
//		getLocationProvider().subscribe(this);
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
		populateMap((Location) data);		
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
	
	private static final int ENABLE_GPS_DIALOG = 1;
	
	private static final int SEARCHING_LOCATION = 2;
	
	private static final int SHOW_GPS_SETTINGS = 120;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateDialog(int)
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog result = null;
		
		switch (id) {
			case ENABLE_GPS_DIALOG: {
				result = new AlertDialog.Builder(this).
					setTitle("Enable GPS").
					setCancelable(false).
					setMessage("This application requires GPS.").
					setPositiveButton("Enable", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
								startActivityForResult(intent, SHOW_GPS_SETTINGS);
							}
						}).create();
				break;
			}
			case SEARCHING_LOCATION: {
				result = getSearchLocationDialog();
				break;
			}		
			default: {
				break;
			}
		}

        return result;
	}
	
	private ProgressDialog searchLocationDialog = null;
	
	private Dialog getSearchLocationDialog() {
		if (searchLocationDialog == null) {
			searchLocationDialog = new ProgressDialog(this);
			searchLocationDialog.setTitle("Searching...");
			searchLocationDialog.setMessage("Aquiring GPS location...");
			searchLocationDialog.setCancelable(false);
			searchLocationDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		}
		
		return searchLocationDialog;
	}
}
