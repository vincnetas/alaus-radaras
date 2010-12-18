/**
 * 
 */
package alaus.radaras;

import alaus.radaras.service.BeerRadar;
import android.os.Bundle;


/**
 * @author LP
 *
 */
public class BrandListActivity extends BaseBrandListActivity {


	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		showBrands(BeerRadar.getInstance(this).getBrands(), R.id.list);
  
	}

	

	
	
}
