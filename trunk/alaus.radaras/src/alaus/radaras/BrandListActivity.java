/**
 * 
 */
package alaus.radaras;

import alaus.radaras.service.BeerRadar;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;


/**
 * @author LP
 *
 */
public class BrandListActivity extends BaseBrandListActivity {


	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		
  
	}

	@Override
	protected void locationUpdated(Location location) {
		if(location != null) {
			Toast.makeText(this, location.toString(), Toast.LENGTH_SHORT);
		}
		showBrands(BeerRadar.getInstance(this).getBrands(location), R.id.list);
		
	}

	

	
	
}
