/**
 * 
 */
package alaus.radaras;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import android.view.View;
import alaus.radaras.adapters.BrandListAdapter;
import alaus.radaras.dao.BeerRadarDao;
import alaus.radaras.dao.model.Brand;


/**
 * @author LP
 *
 */
public class BrandListActivity extends ListActivity {

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	private List<Brand> brands;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        
        brands = BeerRadarDao.getInstance(this).getBrands();
        
        ListView l1 = (ListView) findViewById(R.id.listBrands);
        l1.setAdapter(new BrandListAdapter(this, brands));

        
        l1.setOnItemClickListener(new ListView.OnItemClickListener(){

        	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        		Brand br = brands.get(position);
        		Intent inte = new Intent(BrandListActivity.this, GimeLocation.class);
        		inte.putExtra(GimeLocation.BRAND_ID, br.getId());
        		startActivity(inte);
        	}
            });

        
	}

	
	
}
