package alaus.radaras;

import alaus.radaras.service.BeerRadar;
import alaus.radaras.service.model.Country;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class CountryBrandsListActivity extends BaseBrandListActivity {


	protected static final String COUNTRY = "country";

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			final String countryId = getIntent().getExtras().getString(COUNTRY);
			BeerRadar dao = BeerRadar.getInstance(this);
			Country country = dao.getCountry(countryId);
			setContentView(R.layout.country_brand_list);
			showBrands(dao.getBrandsByCountry(countryId), R.id.countryBrandList);
			
			TextView countryName = (TextView)findViewById(R.id.countryBrandHeader);
			countryName.setText(country.getName());
			
			ImageView image = (ImageView)findViewById(R.id.countryBrandShowOnMap);
			image.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					Intent inte = new Intent(CountryBrandsListActivity.this, GimeLocation.class);
	        		inte.putExtra(GimeLocation.COUNTRY_ID, countryId);
	        		startActivity(inte);
					
				}
			});
			
        
	}
	
}
