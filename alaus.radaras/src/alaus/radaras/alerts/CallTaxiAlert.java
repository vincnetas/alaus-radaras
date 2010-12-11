package alaus.radaras.alerts;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;

public class CallTaxiAlert extends BeerCountAlert {

	
	public CallTaxiAlert(Context context) {
		super(context);
	}

	@Override
	public String getPositive() {
		return "Užteks, važiuoju";
	}

	@Override
	public String getNegative() {
		return "Dar vieną!";
	}

	@Override
	public OnClickListener getListener() {
		return new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Intent.ACTION_DIAL);
				intent.setData(Uri.parse("tel:+37052444888"));
				
			}
		};
	}

	@Override
	public CharSequence getText() {
		return "Tai gal jau taksi? A?";
	}

}
