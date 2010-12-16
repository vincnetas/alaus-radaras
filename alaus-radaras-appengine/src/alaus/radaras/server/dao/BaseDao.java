package alaus.radaras.server.dao;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import alaus.radaras.shared.model.Brand;
import alaus.radaras.shared.model.Country;
import alaus.radaras.shared.model.Pub;
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
	
	@SuppressWarnings("unchecked")
	public List<Brand> getBrands() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Query query = pm.newQuery(Brand.class);
			try {
				return (List<Brand>) pm.detachCopyAll((List<Brand>) query.execute());
			} finally {
				query.closeAll();
			}
		} finally {
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Pub> getPubs() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Query query = pm.newQuery(Pub.class);
			try {				
				return (List<Pub>) pm.detachCopyAll((List<Pub>) query.execute());
			} finally {
				query.closeAll();
			}
		} finally {
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Country> getCountries() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Query query = pm.newQuery(Country.class);
			try {
				return (List<Country>) pm.detachCopyAll((List<Country>) query.execute());
			} finally {
				query.closeAll();
			}
		} finally {
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Tag> getTags() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Query query = pm.newQuery(Tag.class);
			try {
				return (List<Tag>) pm.detachCopyAll((List<Tag>) query.execute());
			} finally {
				query.closeAll();
			}
		} finally {
			pm.close();
		}
	}
}
