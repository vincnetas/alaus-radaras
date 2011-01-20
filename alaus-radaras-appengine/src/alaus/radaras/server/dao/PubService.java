/**
 * 
 */
package alaus.radaras.server.dao;

import java.util.List;

import alaus.radaras.shared.model.Location;
import alaus.radaras.shared.model.Pub;

/**
 * @author Vincentas
 *
 */
public interface PubService extends BaseService<Pub> {

	List<Pub> findPubs(Location location, double radius);

}
