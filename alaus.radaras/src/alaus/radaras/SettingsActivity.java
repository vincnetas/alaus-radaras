package alaus.radaras;

import alaus.radaras.settings.SettingsManager;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class SettingsActivity extends Activity implements OnSeekBarChangeListener, OnCheckedChangeListener {
	
	SettingsManager settings;
	TextView distanceTextView;
	CheckBox distanceEnabledCheckBox;
	SeekBar seekBar;

	  @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	        setContentView(R.layout.settings);
	        
	        settings = new SettingsManager(this);
	        distanceTextView = (TextView)findViewById(R.id.tvMaxDistance);
	        distanceEnabledCheckBox = (CheckBox)findViewById(R.id.cbEnableDistance);
	        
	        int maxDistance = settings.getMaxDistance() ;
	        seekBar = (SeekBar)findViewById(R.id.sbMaximumDistance);
	        seekBar.setOnSeekBarChangeListener(this);
	        
	        if(maxDistance == SettingsManager.UNLIMITED_DISTANCE) {
		        seekBar.setEnabled(false);
		        distanceEnabledCheckBox.setChecked(false);
	        } else {
		        seekBar.setProgress(settings.getMaxDistance() / SettingsManager.DISTANCE_STEP);
		        distanceEnabledCheckBox.setChecked(true);
	        }
	        
	        setDistanceText(maxDistance);
	        
	        CheckBox cbAskForLocation = (CheckBox)findViewById(R.id.cbAskForLocations);
	        cbAskForLocation.setChecked(settings.askOnNoLocation());
	        cbAskForLocation.setOnCheckedChangeListener(this);
	        distanceEnabledCheckBox.setOnCheckedChangeListener(this);
	        
	  }
	  
	  private void setDistanceText(int maxDistance) {
		  String distance;
		  if(maxDistance == SettingsManager.UNLIMITED_DISTANCE) {
			  distance = getString(R.string.settings_distance_disabled);
		  } else if(maxDistance < 1000 ) {
			  distance = getString(R.string.settings_distance_meters, maxDistance);
		  } else {
			  distance =  getString(R.string.settings_distance_km, ((double)maxDistance / 1000L));
		  }
		distanceTextView.setText(distance);
		
	}
	  
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if(buttonView.getId() == R.id.cbAskForLocations) {
				settings.setAskOnNoLocation(isChecked);
			} else {
				seekBar.setEnabled(isChecked);
				seekBar.setProgress(0);
				
				int distance = (isChecked) ? SettingsManager.DISTANCE_STEP : SettingsManager.UNLIMITED_DISTANCE;			
				settings.setMaxDistance(distance);
				setDistanceText(distance);

			}
			
		}

	@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
		
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {

		}
		
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {

			int currentStep = progress +1;
			settings.setMaxDistance(currentStep * SettingsManager.DISTANCE_STEP);
			setDistanceText(currentStep * SettingsManager.DISTANCE_STEP);
			
		}

}
