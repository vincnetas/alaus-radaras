package alaus.radaras.alerts;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class NewLevelAlert extends BeerCountAlert {

	public NewLevelAlert(Context context, Integer count) {
		super(context);
	}

	@Override
	public String getPositive() {
		return "Varom toliau!";
	}

	@Override
	public String getNegative() {
		return "Nu dar vieną";
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
		return "Sveikiname!. Pasiekiete naują lygį - galite ropoti!";
	}

}
