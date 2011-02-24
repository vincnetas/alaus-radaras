package alaus.radaras.sorters;

import java.util.Comparator;

import alaus.radaras.service.model.Country;
import alaus.radaras.utils.Utils;

public class CountryNameSorter implements Comparator<Country> {

	@Override
	public int compare(Country object1, Country object2) {

		return Utils.COLLARATOR.compare(object1.getName(),object2.getName());
	}

}
