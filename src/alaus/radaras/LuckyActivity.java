package alaus.radaras;

import alaus.radaras.service.BeerRadar;
import alaus.radaras.service.BeerRadarSqlite;
import alaus.radaras.service.model.Brand;
import alaus.radaras.service.model.FeelingLucky;
import alaus.radaras.service.model.Pub;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class LuckyActivity extends AbstractLocationActivity {
	
	private BeerRadar beerRadar;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beerRadar = new BeerRadarSqlite(this);
        setContentView(R.layout.lucky);       
	}

	@Override
	protected void locationUpdated(Location location) {
		 
        ImageView imgBrand = (ImageView) findViewById(R.id.LuckyBrandIcon);
        TextView brand = (TextView) findViewById(R.id.LuckyBrandTitle);
        TextView pubView = (TextView) findViewById(R.id.LuckyPubTitle);
        
        FeelingLucky lucky = beerRadar.feelingLucky(location);
        final Brand brData = lucky.getBrand();
        final Pub pb = lucky.getPub();
        pubView.setText(pb.getTitle());
        if(brData != null) {
        	imgBrand.setImageDrawable(beerRadar.getImage(brData.getIcon()));
        	brand.setText(brData.getTitle());
        }
        
        
        TextView pubT = (TextView) findViewById(R.id.LuckyPubT);
        pubT.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LuckyActivity.this, PubActivity.class);
				intent.putExtra(PubActivity.PUB_ID_PARAM, pb.getId());
                startActivity(intent);
			}
		});
        TextView brandT = (TextView) findViewById(R.id.LuckyBrandT);

        brandT.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(brData != null) {
	        		Intent inte = new Intent(LuckyActivity.this, PubLocationActivity.class);
	        		inte.putExtra(PubLocationActivity.BRAND_ID, brData.getId());
	        		startActivity(inte);
				}
			}
		});

	}
}
