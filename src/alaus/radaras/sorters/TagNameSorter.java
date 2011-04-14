package alaus.radaras.sorters;

import java.util.Comparator;

import alaus.radaras.service.model.Tag;
import alaus.radaras.utils.Utils;

public class TagNameSorter implements Comparator<Tag> {

	@Override
	public int compare(Tag object1, Tag object2) {
		return Utils.COLLARATOR.compare(object1.getTitle(),object2.getTitle());
	}

}
