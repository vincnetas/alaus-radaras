package alaus.radaras;

import alaus.radaras.alerts.BeerCountAlert;
import alaus.radaras.alerts.CallTaxiAlert;
import alaus.radaras.alerts.NewLevelAlert;
import alaus.radaras.dao.BeerRadarDao;
import alaus.radaras.dao.model.Qoute;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class BeerCounterActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.counter);
	    
	    final SettingsManager settings = new SettingsManager(this);

	    update(settings.getCurrentCount());
	    
	    ImageView beerImage = (ImageView)findViewById(R.id.countBeer);
	    beerImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				  
			    settings.increaseCurrent();
			    Integer current = settings.getCurrentCount();
			    update(current);
			    displayAlerts(current);
			    
			}
	    });

			
	}

	private void update(Integer currentCount) {
		 Qoute qoute = BeerRadarDao.getInstance(this).getQoute(currentCount);
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
}
