package alaus.radaras;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BeerRadarActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.main);
		ImageView buttonTitle = (ImageView) findViewById(R.id.imgTitleButton);
		//buttonTitle.setAlpha(100);
		buttonTitle.setOnClickListener(new View.OnClickListener() {
				
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(BeerRadarActivity.this, BeerCounterActivity.class);
				try
        		{
        			startActivity(intent);
        		}catch(Exception ex){
        			Toast.makeText(BeerRadarActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        		}
			}
		});

        Button button = (Button) findViewById(R.id.ButtonNear);
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(BeerRadarActivity.this, GimeLocation.class);
                startActivity(intent);
			}
		});
		Button buttonBrand = (Button) findViewById(R.id.ButtonBrand);
		buttonBrand.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
                Intent inte = new Intent(BeerRadarActivity.this, BrandTabWidget.class);
        		try
        		{
        			startActivity(inte);
        		}catch(Exception ex){
        			Toast.makeText(BeerRadarActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        		}
			}
		});

		Button buttonLucky = (Button) findViewById(R.id.ButtonLucky);
		buttonLucky.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
                Intent inte = new Intent(BeerRadarActivity.this, LuckyActivity.class);
        		try
        		{
        			startActivity(inte);
        		}catch(Exception ex){
        			Toast.makeText(BeerRadarActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        		}
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