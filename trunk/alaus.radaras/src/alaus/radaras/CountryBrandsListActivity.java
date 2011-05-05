package alaus.radaras;

import alaus.radaras.service.BeerRadar;
import alaus.radaras.service.BeerRadarSqlite;
import alaus.radaras.utils.Utils;
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
	
	private String country;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		beerRadar = new BeerRadarSqlite(this);
		
		setContentView(R.layout.country_brand_list);
	
		country = getIntent().getExtras().getString(COUNTRY);
		
		TextView countryName = (TextView)findViewById(R.id.countryBrandHeader);
		countryName.setText(Utils.translate(this, country, "country"));
		
		ImageView image = (ImageView)findViewById(R.id.countryBrandShowOnMap);
		image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent inte = new Intent(CountryBrandsListActivity.this, PubLocationActivity.class);
        		inte.putExtra(PubLocationActivity.COUNTRY_ID, country);
        		startActivity(inte);
				
			}
		});        
	}


	@Override
	protected void locationUpdated(Location location) {
		showBrands(beerRadar.getBrandsByCountry(country, location), R.id.countryBrandList);
	}
	
}
