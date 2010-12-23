package alaus.radaras;

import java.util.List;

import alaus.radaras.adapters.CountryListAdapter;
import alaus.radaras.service.BeerRadar;
import alaus.radaras.service.model.Country;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class CountryListActivity extends ListActivity {

	private List<Country> countries;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		countries = BeerRadar.getInstance(this).getCountries();

		getListView().setAdapter(new CountryListAdapter(this, countries));
		getListView().setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Country country = countries.get(position);
				Intent intent = new Intent(CountryListActivity.this, CountryBrandsListActivity.class);
				intent.putExtra(CountryBrandsListActivity.COUNTRY, country.getCode());
				startActivity(intent);
			}
		});

	}

}
