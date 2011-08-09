/**
 * 
 */
package alaus.radaras.shared;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import alaus.radaras.shared.model.Updatable;

/**
 * @author Vincentas Vienozinskis
 *
 */
public class Utils {

	public static <T> Set<T> set(T ... value) {
		Set<T> result = new HashSet<T>();
		
		for (T t : value) {
			result.add(t);
		}
		
		return result;
	}
	
	public static <T extends Updatable> SortedMap<Updatable, SortedSet<Updatable>> sortUpdates(Collection<Updatable> collection) {
		SortedMap<Updatable, SortedSet<Updatable>> result = new TreeMap<Updatable, SortedSet<Updatable>>();
		Map<String, Updatable> ids = new HashMap<String, Updatable>();

		for (Updatable updatable : collection) {
			if (updatable.getParentId() == null) {
				result.put(updatable, new TreeSet<Updatable>());
				ids.put(updatable.getId(), updatable);
			}
		}
		
		for (Updatable updatable : collection) {
			if (updatable.getParentId() != null) {
				
				result.put(updatable, new TreeSet<Updatable>());
				ids.put(updatable.getId(), updatable);
			}
		}

		return result;
	}

	public static <T, U> Map<T, U> map() {
		return new HashMap<T, U>();
	}
}
