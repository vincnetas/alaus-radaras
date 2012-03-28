/**
 * 
 */
package nb.server.service;

import java.util.List;

import nb.shared.model.Place;

/**
 * @author Vincentas
 * 
 */
public interface PlaceService extends BaseService<Place> {

	/**
	 * Returns suggestions for specified query.
	 * 
	 * @param query
	 * @param limit
	 * @return
	 */
	List<Place> getAutocomplete(String query, int limit);
	
	

}
