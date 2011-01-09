/**
 * 
 */
package alaus.radaras.server.dao.impl;

import alaus.radaras.server.dao.PubDao;
import alaus.radaras.shared.model.Pub;

/**
 * @author Vincentas
 *
 */
public class PubDaoImpl extends BaseDaoImpl<Pub> implements PubDao {

	@Override
	public Class<Pub> getClazz() {
		return Pub.class;
	}
}
