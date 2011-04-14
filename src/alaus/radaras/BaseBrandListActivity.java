package alaus.radaras;

import java.util.List;

import alaus.radaras.adapters.BrandListAdapter;
import alaus.radaras.service.model.Brand;
import alaus.radaras.utils.Utils;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public abstract class BaseBrandListActivity extends AbstractLocationActivity {

	protected List<Brand> brands;
	protected ListView list;
	
	public void onCreate(Bundle savedInstanceState) {
		   super.onCreate(savedInstanceState);
	}
	
	protected void showBrands(final List<Brand> brands, int listId) {
		this.brands = brands;
		
		if(brands.size() == 0) {
			Utils.showNoBeerAlert(getApplicationContext());
		}
		
		list = (ListView)findViewById(listId);
		
        list.setAdapter(new BrandListAdapter(this, brands));

        list.setOnItemClickListener(new ListView.OnItemClickListener(){
        	
        	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        		Intent inte = new Intent(BaseBrandListActivity.this, PubLocationActivity.class);
        		inte.putExtra(PubLocationActivity.BRAND_ID, brands.get(position).getId());
        		startActivity(inte);
        		}
            });
		
	}
}
