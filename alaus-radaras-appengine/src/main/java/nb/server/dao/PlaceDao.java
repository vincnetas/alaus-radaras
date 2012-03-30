/**
 * 
 */
package nb.server.dao;

import java.util.List;

import nb.shared.model.Place;

/**
 * @author Vincentas
 *
 */
public interface PlaceDao extends BaseHistoryDao<Place> {

	List<Place> acPlace(String title, int max);

}
