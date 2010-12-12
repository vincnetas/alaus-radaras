/**
 * 
 */
package alaus.radaras;

import alaus.radaras.dao.BeerRadarDao;
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
		showBrands(BeerRadarDao.getInstance(this).getBrands(), R.id.list);
  
	}

	

	
	
}
