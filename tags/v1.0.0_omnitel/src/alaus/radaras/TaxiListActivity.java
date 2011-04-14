package alaus.radaras;

import java.util.List;

import alaus.radaras.adapters.TaxiListAdapter;
import alaus.radaras.service.BeerRadar;
import alaus.radaras.service.BeerRadarSqlite;
import alaus.radaras.service.model.Taxi;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class TaxiListActivity extends AbstractLocationActivity {

	private ListView list;
	private List<Taxi> taxies;
	private BeerRadar beerRadar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		beerRadar = new BeerRadarSqlite(this);
		
		setContentView(R.layout.list);

		list = (ListView)findViewById(R.id.list);
		list.setOnItemClickListener(new ListView.OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Taxi taxi = taxies.get(position);
				Intent intent = new Intent(Intent.ACTION_DIAL);
				intent.setData(Uri.parse("tel:" + taxi.getPhone()));
				startActivity(intent);
			}
		});
	}

	@Override
	protected void locationUpdated(Location location) {
		taxies = beerRadar.getTaxies(location);
		Log.e("d",String.valueOf(taxies.size()));
		list.setAdapter(new TaxiListAdapter(this, taxies));
		
		if(taxies.size() == 0) {
			Toast.makeText(this, getString(R.string.no_taxies), Toast.LENGTH_LONG).show();
			
		}
		

	}

}
