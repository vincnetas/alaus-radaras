package alaus.radaras;

import java.util.List;

import alaus.radaras.adapters.TagListAdapter;
import alaus.radaras.service.BeerRadar;
import alaus.radaras.service.model.Tag;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class TagListActivity extends ListActivity {

	private List<Tag> tags;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		tags = BeerRadar.getInstance(this).getTags();
		getListView().setAdapter(new TagListAdapter(this, tags));

		getListView().setOnItemClickListener(new ListView.OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Tag tag = tags.get(position);
				Intent inte = new Intent(TagListActivity.this, TagBrandsListActivity.class);
				inte.putExtra(TagBrandsListActivity.TAG, tag.getCode());
				startActivity(inte);
			}
		});
	}
}
