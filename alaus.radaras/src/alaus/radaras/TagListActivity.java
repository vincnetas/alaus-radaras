package alaus.radaras;

import java.util.List;

import alaus.radaras.adapters.TagListAdapter;
import alaus.radaras.service.BeerRadar;
import alaus.radaras.service.BeerRadarSqlite;
import alaus.radaras.utils.Utils;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class TagListActivity extends AbstractLocationActivity {

	private ListView list;
	private List<String> tags;
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
				String tag = tags.get(position);
				Intent intent = new Intent(TagListActivity.this, TagBrandsListActivity.class);
				intent.putExtra(TagBrandsListActivity.TAG, tag);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void locationUpdated(Location location) {
		tags = Utils.translateAndSortTags(this, beerRadar.getTags(location)); 
			
		
		if(tags.size() == 0) {
			Utils.showNoBeerAlert(getApplicationContext());
		}
		list.setAdapter(new TagListAdapter(this, tags));		
	}
}
