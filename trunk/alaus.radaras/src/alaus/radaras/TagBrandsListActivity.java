package alaus.radaras;


import alaus.radaras.service.BeerRadar;
import alaus.radaras.service.model.Tag;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class TagBrandsListActivity extends BaseBrandListActivity {

	protected static final String TAG = "tag";

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			final String tagId = getIntent().getExtras().getString(TAG);
			BeerRadar dao = BeerRadar.getInstance(this);
			Tag tag = dao.getTag(tagId);
			setContentView(R.layout.tag_brand_list);
			showBrands(dao.getBrandsByTag(tagId), R.id.tagBrandList);
			
			TextView tagName = (TextView)findViewById(R.id.tagBrandHeader);
			tagName.setText(tag.getTitle());
			
			ImageView image = (ImageView)findViewById(R.id.tagBrandShowOnMap);
			image.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					Intent inte = new Intent(TagBrandsListActivity.this, GimeLocation.class);
	        		inte.putExtra(GimeLocation.TAG_ID, tagId);
	        		startActivity(inte);
					
				}
			});
			
        
	}

}
