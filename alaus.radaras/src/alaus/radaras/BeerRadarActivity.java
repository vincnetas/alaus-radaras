package alaus.radaras;

import alaus.radaras.service.BeerRadarUpdate;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class BeerRadarActivity extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.main);
        
		getCounterView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(BeerRadarActivity.this, "Beer counter", Toast.LENGTH_SHORT);
				startActivity(new Intent(BeerRadarActivity.this, BeerCounterActivity.class));
			}
		});

		getAroundView().setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				Toast.makeText(BeerRadarActivity.this, "Beer around you", Toast.LENGTH_SHORT);
				startActivity(new Intent(BeerRadarActivity.this, GimeLocation.class));
			}
		});
		
		getSearchView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(BeerRadarActivity.this, "Beer search", Toast.LENGTH_SHORT);
                startActivity(new Intent(BeerRadarActivity.this, BrandTabWidget.class));
			}
		});

		getLuckyView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(BeerRadarActivity.this, "Feeling lucky", Toast.LENGTH_SHORT);
                startActivity(new Intent(BeerRadarActivity.this, LuckyActivity.class));
			}
		});

    }
    
    private View getAroundView() {
    	return findViewById(R.id.around);
    }
    
    private View getSearchView() {
    	return findViewById(R.id.search);
    }
    
    private View getLuckyView() {
    	return findViewById(R.id.lucky);
    }
    
    private View getCounterView() {
    	return findViewById(R.id.counter);
    }    
    
    @Override
    public void onResume(){
    	final SettingsManager settings = new SettingsManager(this);
        TextView counter = (TextView) findViewById(R.id.mainCounterCurrent);
        counter.setText(settings.getTotalCount().toString());
        
        super.onResume();
    }
}