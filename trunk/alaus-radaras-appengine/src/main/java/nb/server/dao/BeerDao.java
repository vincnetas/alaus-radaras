/**
 * 
 */
package nb.server.dao;

import java.util.List;

import nb.shared.model.Beer;

/**
 * @author Vincentas
 *
 */
public interface BeerDao extends BaseHistoryDao<Beer> {

	List<Beer> acBeer(String query, int limit);

}
