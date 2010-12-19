package alaus.radaras;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class BeerRadarActivity extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.main);
        
		ImageView buttonTitle = (ImageView) findViewById(R.id.imgTitleButton);
		buttonTitle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(BeerRadarActivity.this, BeerCounterActivity.class));
			}
		});

        Button button = (Button) findViewById(R.id.ButtonNear);
		button.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(BeerRadarActivity.this, GimeLocation.class));
			}
		});
		
		Button buttonBrand = (Button) findViewById(R.id.ButtonBrand);
		buttonBrand.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
                startActivity(new Intent(BeerRadarActivity.this, BrandTabWidget.class));
			}
		});

		Button buttonLucky = (Button) findViewById(R.id.ButtonLucky);
		buttonLucky.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
                startActivity(new Intent(BeerRadarActivity.this, LuckyActivity.class));
			}
		});

    }
    
    @Override
    public void onResume(){
    	final SettingsManager settings = new SettingsManager(this);
        TextView counter = (TextView) findViewById(R.id.mainCounterCurrent);
        counter.setText(settings.getTotalCount().toString());
        
        super.onResume();
    }
}