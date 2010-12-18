package alaus.radaras.server.dao;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import alaus.radaras.shared.model.Brand;
import alaus.radaras.shared.model.BrandCountryAssociation;
import alaus.radaras.shared.model.BrandPubAssociation;
import alaus.radaras.shared.model.BrandTagAssociation;
import alaus.radaras.shared.model.Country;
import alaus.radaras.shared.model.Pub;
import alaus.radaras.shared.model.Quote;
import alaus.radaras.shared.model.Tag;

public class BaseDao {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void save(List list) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistentAll(list);
		} finally {
			pm.close();
		}
	}
	
	public List<Brand> getBrands() {
		return get(Brand.class);
	}

	public List<Pub> getPubs() {
		return get(Pub.class);
	}

	public List<Country> getCountries() {
		return get(Country.class);
	}

	public List<Tag> getTags() {
		return get(Tag.class);
	}
	
	public List<BrandCountryAssociation> getBrandCountryAssociation() {
		return get(BrandCountryAssociation.class);
	}
	
	public List<BrandPubAssociation> getBrandPubAssociations() {
		return get(BrandPubAssociation.class);
	}
	
	public List<BrandPubAssociation> getQuotes() {
		return get(Quote.class);
	}
	
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
