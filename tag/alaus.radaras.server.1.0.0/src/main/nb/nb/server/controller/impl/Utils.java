/**
 * 
 */
package nb.server.controller.impl;

import java.util.Collection;
import java.util.Comparator;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.collections15.ComparatorUtils;

import nb.shared.model.Place;



/**
 * @author Vincentas
 *
 */
public class Utils {
	
	static public SortedSet<SortedSet<Place>> sortPlacesByCount(Collection<Place> places) {
		SortedMap<String,SortedSet<Place>> sortPlacesByCity = sortPlacesByCity(places);
				
		SortedSet<SortedSet<Place>> result = new TreeSet<SortedSet<Place>>(ComparatorUtils.chainedComparator(
			new Comparator<SortedSet<Place>>() {
	
				@Override
				public int compare(SortedSet<Place> o1, SortedSet<Place> o2) {
					return o2.size() - o1.size();
				}
			},
			new Comparator<SortedSet<Place>>() {

				@Override
				public int compare(SortedSet<Place> o1, SortedSet<Place> o2) {
					if (o1.isEmpty() && o2.isEmpty()) {
						return 0;
					}
					
					return o1.first().getCity().compareToIgnoreCase(o2.first().getCity());
				}
			}
		));
		
		result.addAll(sortPlacesByCity.values());
		
		return result;
	}
	
    static public SortedMap<String, SortedSet<Place>> sortPlacesByCity(Collection<Place> places) {
    	SortedMap<String, SortedSet<Place>> result = new TreeMap<String, SortedSet<Place>>();
    	
    	for (Place place : places) {
			SortedSet<Place> value = result.get(place.getCity());
			if (value == null) {
				value = new TreeSet<Place>(new Comparator<Place>() {

					@Override
					public int compare(Place o1, Place o2) {
						return o1.getTitle().compareToIgnoreCase(o2.getTitle());
					}
				});
				
				result.put(place.getCity(), value);
			}
			
			value.add(place);			
		}
    	
    	return result;
    }

}
