package alaus.radaras.alerts;

import alaus.radaras.R;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class NewLevelAlert extends BeerCountAlert {

	public NewLevelAlert(Context context, Integer count) {
		super(context);
	}

	@Override
	public String getPositive() {
		return context.getString(R.string.alert_new_level_yes);
	}

	@Override
	public String getNegative() {
		return context.getString(R.string.alert_new_level_no);
	}

	@Override
	public OnClickListener getListener() {
		return new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		};
	}

	@Override
	public CharSequence getText() {
		return context.getString(R.string.alert_new_level_message);
	}

}
