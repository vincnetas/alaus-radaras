package alaus.radaras;

//import alaus.radaras.service.BeerRadarUpdate;
import alaus.radaras.settings.SettingsManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class BeerRadarActivity extends AbstractLocationActivity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.main);
        
		getCounterView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(BeerRadarActivity.this, BeerCounterActivity.class));
			}
		});

		getAroundView().setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(BeerRadarActivity.this, PubLocationActivity.class));
			}
		});
		
		getSearchView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
                startActivity(new Intent(BeerRadarActivity.this, BrandTabWidget.class));
			}
		});

		getLuckyView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
                startActivity(new Intent(BeerRadarActivity.this, LuckyActivity.class));
			}
		});
		
		   
		checkIfLocationIsEnabled();
    }
    
    private void checkIfLocationIsEnabled() {
    	
    	final SettingsManager settings = new SettingsManager(getApplicationContext());
    	   if(settings.askOnNoLocation() && 
    			   ((BeerRadarApp)getApplication()).getLocationProvider().getAvailableProvidersCount() == 0) {
    		   
    		   LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
    		   final View layout = inflater.inflate(R.layout.dialog_location_enable_request,null);

    		 
    		   new AlertDialog.Builder(this)
    		   .setView(layout)
    		   .setTitle(getString(R.string.location_disabled_header))
			   .setPositiveButton(getString(R.string.location_disabled_yes), new DialogInterface.OnClickListener() {
				      public void onClick(DialogInterface dialog, int which) {
				    	  boolean stopAsking = ((CheckBox)layout.findViewById(R.id.cbSkipAskingForLocation)).isChecked();
				    	  settings.setAskOnNoLocation(!stopAsking);
				    	  Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS );
				    	    startActivity(myIntent);
				 
				    } })
			   .setNegativeButton(getString(R.string.location_disabled_no), new DialogInterface.OnClickListener() {
				      public void onClick(DialogInterface dialog, int which) {
				    	  boolean stopAsking = ((CheckBox)layout.findViewById(R.id.cbSkipAskingForLocation)).isChecked();
				    	  settings.setAskOnNoLocation(!stopAsking);
				    	  dialog.dismiss();
				    } })
				 .show();
		   }

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

	@Override
	protected void locationUpdated(Location location) {
		//do nothing. we just need this to get location fix asap.
		
	}
	
}