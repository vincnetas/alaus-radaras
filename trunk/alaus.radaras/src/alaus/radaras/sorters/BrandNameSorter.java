package alaus.radaras.sorters;

import java.util.Comparator;

import alaus.radaras.service.model.Brand;
import alaus.radaras.utils.Utils;

public class BrandNameSorter implements Comparator<Brand> {

	@Override
	public int compare(Brand object1, Brand object2) {
		
		return Utils.COLLARATOR.compare(object1.getTitle(),object2.getTitle());
	}

}
