package alaus.radaras.alerts;

import android.content.Context;
import android.content.DialogInterface.OnClickListener;

public abstract class BeerCountAlert {

	protected Context context;
	public BeerCountAlert(Context context) {
		this.context = context;
	}
	public abstract String getPositive();
	public abstract String getNegative();
	public abstract OnClickListener getListener();
	public abstract CharSequence getText();

}
