package alaus.radaras;

import java.util.List;

import alaus.radaras.adapters.CountryListAdapter;
import alaus.radaras.service.BeerRadar;
import alaus.radaras.service.BeerRadarSqlite;
import alaus.radaras.utils.Utils;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class CountryListActivity extends AbstractLocationActivity {

	private BeerRadar beerRadar;
	private List<String> countries;
	private ListView list;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		beerRadar = new BeerRadarSqlite(this);
		
		setContentView(R.layout.list);
			list = (ListView)findViewById(R.id.list);
		list.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				String country = countries.get(position);
				Intent intent = new Intent(CountryListActivity.this, CountryBrandsListActivity.class);
				intent.putExtra(CountryBrandsListActivity.COUNTRY, country);
				startActivity(intent);
			}
		});

	}

	@Override
	protected void locationUpdated(Location location) {
		countries = Utils.sortTranslation(this, beerRadar.getCountries(location), "country");
		
		if(countries.size() == 0) {
			Utils.showNoBeerAlert(getApplicationContext());
		}
		
		list.setAdapter(new CountryListAdapter(this, countries));
		
	
		
	}

}
