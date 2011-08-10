/**
 * 
 */
package alaus.radaras.server.dao.impl;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import alaus.radaras.server.dao.PubDao;
import alaus.radaras.shared.model.Pub;

/**
 * @author Vincentas
 *
 */
public class PubDaoImpl extends BaseDaoImpl<Pub> implements PubDao {

	@Override
	public List<Pub> getBeerPubs(String beerId) {
		PersistenceManager pm = getPmf().getPersistenceManager();
		try {
			Query query = pm.newQuery(getClazz());
			query.setFilter("parentId == null && beerIds == beerId");
			query.declareParameters("java.lang.String beerId");
			
			try {
				return (List<Pub>) pm.detachCopyAll((List<Pub>) query.execute(beerId));
			} finally {
				query.closeAll();
			}
		} finally {
			pm.close();
		}
	}

	
}
