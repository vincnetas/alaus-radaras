package alaus.radaras;

import java.util.List;

import alaus.radaras.adapters.BrandListAdapter;
import alaus.radaras.dao.model.Brand;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class BaseBrandListActivity extends Activity {

	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	protected List<Brand> brands;
	protected ListView list;
	
	public void onCreate(Bundle savedInstanceState) {
		   super.onCreate(savedInstanceState);
	}
	
	protected void showBrands(final List<Brand> brands, int listId) {
		this.brands = brands;

		list = (ListView)findViewById(listId);
		
        list.setAdapter(new BrandListAdapter(this, brands));

        list.setOnItemClickListener(new ListView.OnItemClickListener(){
        	
        	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        		Intent inte = new Intent(BaseBrandListActivity.this, GimeLocation.class);
        		inte.putExtra(GimeLocation.BRAND_ID, brands.get(position).getId());
        		startActivity(inte);
        		}
            });
		
	}
}
