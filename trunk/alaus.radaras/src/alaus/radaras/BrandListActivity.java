/**
 * 
 */
package alaus.radaras;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;
import alaus.radaras.adapters.BrandListAdapter;


/**
 * @author Vincentas
 *
 */
public class BrandListActivity extends ListActivity {

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        
        ListView l1 = (ListView) findViewById(R.id.listBrands);
        l1.setAdapter(new BrandListAdapter(this, currencies));

	}

	
	
}
