/**
 * 
 */
package alaus.radaras.server.dao;

import java.util.List;

import alaus.radaras.shared.model.LocationBounds;
import alaus.radaras.shared.model.Pub;

/**
 * @author Vincentas
 *
 */
public interface PubService extends BaseService<Pub> {

	List<Pub> findPubs(LocationBounds bounds);

}
