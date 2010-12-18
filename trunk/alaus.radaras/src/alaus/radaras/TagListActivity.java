package alaus.radaras;

import java.util.List;

import alaus.radaras.adapters.TagListAdapter;
import alaus.radaras.service.BeerRadar;
import alaus.radaras.service.model.Tag;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class TagListActivity extends Activity {


	private List<Tag> tags;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			setContentView(R.layout.tag_list);
        
        tags = BeerRadar.getInstance(this).getTags();
        ListView l1 = (ListView) findViewById(R.id.tagList);
        l1.setAdapter(new TagListAdapter(this, tags));

        
        l1.setOnItemClickListener(new ListView.OnItemClickListener(){

        	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        		Tag tag = tags.get(position);
        		Intent inte = new Intent(TagListActivity.this, TagBrandsListActivity.class);
        		inte.putExtra(TagBrandsListActivity.TAG, tag.getCode());
        		startActivity(inte);
        	}
            });

        
	}
	
}
