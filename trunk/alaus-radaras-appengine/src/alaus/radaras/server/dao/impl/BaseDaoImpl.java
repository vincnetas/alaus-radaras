/**
 * 
 */
package alaus.radaras.server.dao.impl;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import alaus.radaras.server.dao.BaseDao;
import alaus.radaras.server.dao.PMF;
import alaus.radaras.shared.model.Beer;
import alaus.radaras.shared.model.Brand;
import alaus.radaras.shared.model.Pub;
import alaus.radaras.shared.model.Quote;

/**
 * @author Vincentas
 *
 */
public class BaseDaoImpl implements BaseDao {

	/* (non-Javadoc)
	 * @see alaus.radaras.server.dao.impl.a#save(java.util.List)
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void save(List list) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistentAll(list);
		} finally {
			pm.close();
		}
	}
	
	/* (non-Javadoc)
	 * @see alaus.radaras.server.dao.impl.a#getBrands()
	 */
	@Override
	public List<Brand> getBrands() {
		return get(Brand.class);
	}

	/* (non-Javadoc)
	 * @see alaus.radaras.server.dao.impl.a#getPubs()
	 */
	@Override
	public List<Pub> getPubs() {
		return get(Pub.class);
	}	

	/* (non-Javadoc)
	 * @see alaus.radaras.server.dao.BaseDao#getBeers()
	 */
	@Override
	public List<Beer> getBeers() {
		return get(Beer.class);
	}

	/* (non-Javadoc)
	 * @see alaus.radaras.server.dao.BaseDao#getQuotes()
	 */
	@Override
	public List<Quote> getQuotes() {
		return get(Quote.class);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private <T> List<T> get(Class clazz) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Query query = pm.newQuery(clazz);
			try {
				return (List<T>) pm.detachCopyAll((List<T>) query.execute());
			} finally {
				query.closeAll();
			}
		} finally {
			pm.close();
		}
	}
}
