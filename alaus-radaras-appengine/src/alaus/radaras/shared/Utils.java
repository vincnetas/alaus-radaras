/**
 * 
 */
package alaus.radaras.shared;

import java.util.HashSet;
import java.util.Set;

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
}
