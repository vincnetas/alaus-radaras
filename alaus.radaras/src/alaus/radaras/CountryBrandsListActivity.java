package alaus.radaras;

import alaus.radaras.service.BeerRadar;
import alaus.radaras.service.BeerRadarSqlite;
import alaus.radaras.service.model.Country;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class CountryBrandsListActivity extends BaseBrandListActivity {

	protected static final String COUNTRY = "country";

	private BeerRadar beerRadar;
	
	private Country country;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		beerRadar = new BeerRadarSqlite(this);
		
		setContentView(R.layout.country_brand_list);
	
		final String countryId = getIntent().getExtras().getString(COUNTRY);
		country = beerRadar.getCountry(countryId);
		
		TextView countryName = (TextView)findViewById(R.id.countryBrandHeader);
		countryName.setText(country.getName(this));
		
		ImageView image = (ImageView)findViewById(R.id.countryBrandShowOnMap);
		image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent inte = new Intent(CountryBrandsListActivity.this, PubLocationActivity.class);
        		inte.putExtra(PubLocationActivity.COUNTRY_ID, countryId);
        		startActivity(inte);
				
			}
		});        
	}


	@Override
	protected void locationUpdated(Location location) {
		showBrands(beerRadar.getBrandsByCountry(country.getCode(), location), R.id.countryBrandList);
	}
	
}
