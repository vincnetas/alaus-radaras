package alaus.radaras;


import alaus.radaras.service.BeerRadar;
import alaus.radaras.service.BeerRadarSqlite;
import alaus.radaras.service.model.Tag;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class TagBrandsListActivity extends BaseBrandListActivity {

	protected static final String TAG = "tag";

	private Tag tag;
	
	private BeerRadar beerRadar;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		beerRadar = new BeerRadarSqlite(this);
		
		setContentView(R.layout.tag_brand_list);
	
		final String tagId = getIntent().getExtras().getString(TAG);
		tag = beerRadar.getTag(tagId);

		TextView tagName = (TextView)findViewById(R.id.tagBrandHeader);
		tagName.setText(tag.getTitle());
		
		ImageView image = (ImageView)findViewById(R.id.tagBrandShowOnMap);
		image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent inte = new Intent(TagBrandsListActivity.this, PubLocationActivity.class);
        		inte.putExtra(PubLocationActivity.TAG_ID, tagId);
        		startActivity(inte);
				
			}
		});        
	}

	@Override
	protected void locationUpdated(Location location) {
		showBrands(beerRadar.getBrandsByTag(tag.getCode(), location), R.id.tagBrandList);		
	}

}
