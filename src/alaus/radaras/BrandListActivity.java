/**
 * 
 */
package alaus.radaras;

import alaus.radaras.service.BeerRadar;
import alaus.radaras.service.BeerRadarSqlite;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;


/**
 * @author LP
 *
 */
public class BrandListActivity extends BaseBrandListActivity {

	private BeerRadar beerRadar;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		beerRadar = new BeerRadarSqlite(this);
		
		setContentView(R.layout.list);  
	}

	@Override
	protected void locationUpdated(Location location) {
		if (location != null) {
			Toast.makeText(this, location.toString(), Toast.LENGTH_SHORT);
		}
		
		showBrands(beerRadar.getBrands(location), R.id.list);
	}

	

	
	
}
