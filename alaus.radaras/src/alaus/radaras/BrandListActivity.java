/**
 * 
 */
package alaus.radaras;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View;
import alaus.radaras.adapters.BrandListAdapter;
import alaus.radaras.dao.BeerRadarDao;
import alaus.radaras.dao.model.Brand;


/**
 * @author LP
 *
 */
public class BrandListActivity extends Activity {

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	private List<Brand> brands;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		try {
			setContentView(R.layout.list);
        
        	brands = BeerRadarDao.getInstance(this).getBrands();
        }catch(Exception ex){
			Toast.makeText(BrandListActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        ListView l1 = (ListView) findViewById(R.id.list);
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