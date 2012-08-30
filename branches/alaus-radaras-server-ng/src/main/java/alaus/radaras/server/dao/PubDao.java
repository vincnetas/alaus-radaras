/**
 * 
 */
package alaus.radaras.server.dao;

import java.util.List;

import alaus.radaras.shared.model.Pub;

/**
 * @author Vincentas
 *
 */
public interface PubDao extends BaseDao<Pub> {

	List<Pub> getBeerPubs(String beerId);
}
