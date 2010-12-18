package alaus.radaras;

import java.util.List;

import alaus.radaras.adapters.CountryListAdapter;
import alaus.radaras.service.BeerRadar;
import alaus.radaras.service.model.Country;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class CountryListActivity extends Activity {


	private List<Country> countries;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			setContentView(R.layout.country_list);
        
		countries = BeerRadar.getInstance(this).getCountries();
        ListView l1 = (ListView) findViewById(R.id.countryList);
        l1.setAdapter(new CountryListAdapter(this, countries));

        
        l1.setOnItemClickListener(new ListView.OnItemClickListener(){

        	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        		Country country = countries.get(position);
        		Intent inte = new Intent(CountryListActivity.this, CountryBrandsListActivity.class);
        		inte.putExtra(CountryBrandsListActivity.COUNTRY, country.getCode());
        		startActivity(inte);
        	}
            });

	}
	
}
