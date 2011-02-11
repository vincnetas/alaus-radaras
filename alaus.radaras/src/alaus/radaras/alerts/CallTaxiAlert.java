package alaus.radaras.alerts;

import alaus.radaras.R;
import alaus.radaras.TaxiListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;

public class CallTaxiAlert extends BeerCountAlert {

	
	public CallTaxiAlert(Context context) {
		super(context);
	}

	@Override
	public String getPositive() {
		return context.getString(R.string.alert_taxi_yes);
	}

	@Override
	public String getNegative() {
		return context.getString(R.string.alert_taxi_no);
	}

	@Override
	public OnClickListener getListener() {
		return new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				context.startActivity(new Intent(context, TaxiListActivity.class));
				
			}
		};
	}

	@Override
	public CharSequence getText() {
		return context.getString(R.string.alert_taxi_message);
	}

}
