package alaus.radaras.sorters;

import java.util.Comparator;

import alaus.radaras.service.model.Pub;
import alaus.radaras.utils.Utils;

public class PubNameSorter implements Comparator<Pub> {

	@Override
	public int compare(Pub object1, Pub object2) {
		
		return Utils.COLLARATOR.compare(object1.getTitle(),object2.getTitle());
	}

}
