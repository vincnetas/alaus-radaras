/**
 * 
 */
package alaus.radaras.server.dao.impl;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import alaus.radaras.server.dao.BaseDao;
import alaus.radaras.server.dao.PMF;
import alaus.radaras.shared.model.Brand;
import alaus.radaras.shared.model.BrandCountryAssociation;
import alaus.radaras.shared.model.BrandPubAssociation;
import alaus.radaras.shared.model.BrandTagAssociation;
import alaus.radaras.shared.model.Country;
import alaus.radaras.shared.model.Pub;
import alaus.radaras.shared.model.Quote;
import alaus.radaras.shared.model.Tag;

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
	 * @see alaus.radaras.server.dao.impl.a#getCountries()
	 */
	@Override
	public List<Country> getCountries() {
		return get(Country.class);
	}

	/* (non-Javadoc)
	 * @see alaus.radaras.server.dao.impl.a#getTags()
	 */
	@Override
	public List<Tag> getTags() {
		return get(Tag.class);
	}
	
	/* (non-Javadoc)
	 * @see alaus.radaras.server.dao.impl.a#getBrandCountryAssociation()
	 */
	@Override
	public List<BrandCountryAssociation> getBrandCountryAssociation() {
		return get(BrandCountryAssociation.class);
	}
	
	/* (non-Javadoc)
	 * @see alaus.radaras.server.dao.impl.a#getBrandPubAssociations()
	 */
	@Override
	public List<BrandPubAssociation> getBrandPubAssociations() {
		return get(BrandPubAssociation.class);
	}
	
	/* (non-Javadoc)
	 * @see alaus.radaras.server.dao.impl.a#getQuotes()
	 */
	@Override
	public List<BrandPubAssociation> getQuotes() {
		return get(Quote.class);
	}
	
	/* (non-Javadoc)
	 * @see alaus.radaras.server.dao.impl.a#getBrandTagAssociations()
	 */
	@Override
	public List<BrandTagAssociation> getBrandTagAssociations() {
		return get(BrandTagAssociation.class);
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
