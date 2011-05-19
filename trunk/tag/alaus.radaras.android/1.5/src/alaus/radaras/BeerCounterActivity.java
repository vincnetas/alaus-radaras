package alaus.radaras;

import alaus.radaras.alerts.BeerCountAlert;
import alaus.radaras.alerts.CallTaxiAlert;
import alaus.radaras.alerts.NewLevelAlert;
import alaus.radaras.service.BeerRadar;
import alaus.radaras.service.BeerRadarSqlite;
import alaus.radaras.service.model.Qoute;
import alaus.radaras.settings.SettingsManager;
import alaus.radaras.utils.Utils;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class BeerCounterActivity extends Activity {

	private  SettingsManager settings;
	
	private BeerRadar beerRadar;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    settings = new SettingsManager(this);
	    beerRadar = new BeerRadarSqlite(this);
	    
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    setContentView(R.layout.counter);
	    


	    update(settings.getCurrentCount(),settings.getTotalCount());
	    
	    ImageView beerImage = (ImageView)findViewById(R.id.countBeer);
	    beerImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				  
			    settings.increaseCurrent();
			    Integer current = settings.getCurrentCount();
			    update(current,settings.getTotalCount());
			    displayAlerts(current);
			    
			}
	    });
	    
	    beerImage.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				  
				resetCurrentCount();
			    return true;
			}

			
	    });
	    
	}
	
	private void resetCurrentCount() {
		settings.resetCurrent();
		update(0,settings.getTotalCount());
	}

	private void update(Integer currentCount, Integer totalCount) {
		Qoute qoute;
		if(currentCount <= 10) {
		 qoute = beerRadar.getQoute(currentCount);
		}
		else {
			qoute = new Qoute();
			qoute.setText(getString(R.string.counter_max_level_qoute));
		}
		 ((TextView)findViewById(R.id.counterCurrent)).setText(currentCount.toString());
		 ((TextView)findViewById(R.id.counterQoute)).setText(qoute.getText());
		
	}
	
	private void displayAlerts(Integer currentCount) {
	   BeerCountAlert alert = getFunnyAlert(currentCount);
		if(alert != null) {
		   new AlertDialog.Builder(this)
			.setMessage(alert.getText())
			.setPositiveButton(alert.getPositive(), alert.getListener())
	        .setNegativeButton(alert.getNegative(), null)
			.show();
		}
	}


	private BeerCountAlert getFunnyAlert(Integer currentCount) {
		if(currentCount == 5) {
			return new CallTaxiAlert(this);
		}
		else if(currentCount == 10) {
			return new NewLevelAlert(this,10);
		}
		return null;
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.settings:
	    	  startActivity(new Intent(this, SettingsActivity.class));
	        return true;
	    case R.id.callTaxi:
	    	startActivity(new Intent(this, TaxiListActivity.class));
	    	return true;
	    case R.id.clearCounter:
	    	resetCurrentCount();
	    	return true;
	    case R.id.submitPub:
	    	BeerRadarApp app = ((BeerRadarApp)getApplication());
	    	Utils.showPubSubmitDialog(this, app.getLastKnownLocation());
	    	return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
}
