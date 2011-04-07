package alaus.radaras.sorters;

import java.util.Comparator;

import alaus.radaras.service.model.Country;
import alaus.radaras.utils.Utils;
import android.content.Context;

public class CountryNameSorter implements Comparator<Country> {

	private Context context;
	
	public CountryNameSorter(Context context) {
		this.context = context;
	}

	@Override
	public int compare(Country object1, Country object2) {
		return Utils.COLLARATOR.compare(object1.getName(context),object2.getName(context));
	}

}
