/**
 * 
 */
package alaus.radaras;

import android.os.Bundle;

import com.google.android.maps.MapActivity;

/**
 * @author Vincentas
 *
 */
public class GimeLocation extends MapActivity {

	
	
	/* (non-Javadoc)
	 * @see com.google.android.maps.MapActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		
		setContentView(R.layout.map);
	}

	/* (non-Javadoc)
	 * @see com.google.android.maps.MapActivity#isRouteDisplayed()
	 */
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

}
