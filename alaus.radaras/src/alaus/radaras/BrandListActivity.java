/**
 * 
 */
package alaus.radaras;

import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;
import alaus.radaras.adapters.BrandListAdapter;
import alaus.radaras.dao.model.Brand;


/**
 * @author Vincentas
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
        
        ListView l1 = (ListView) findViewById(R.id.listBrands);
        l1.setAdapter(new BrandListAdapter(this, brands));

	}

	
	
}
