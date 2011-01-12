/**
 * 
 */
package alaus.radaras.server.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import alaus.radaras.server.dao.BaseDao;
import alaus.radaras.server.dao.IdProvider;
import alaus.radaras.server.dao.PMF;
import alaus.radaras.shared.model.Status;
import alaus.radaras.shared.model.Updatable;

import com.google.inject.Inject;

/**
 * @author Vincentas
 *
 */
public abstract class BaseDaoImpl<T extends Updatable> implements BaseDao<T> {

	@Inject
	IdProvider idProvider;
	
	/* (non-Javadoc)
	 * @see alaus.radaras.server.dao.BaseDao#save(java.lang.Object)
	 */
	@Override
	public void save(T object) {
		if (object.getId() == null) {
			object.setId(getIdProvider().getId());
		}
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(object);
		} finally {
			pm.close();
		}
	}

	@Override
	public void save(List<T> list) {
		for (T object : list) {
			if (object.getId() == null) {
				object.setId(getIdProvider().getId());
			}
		}
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistentAll(list);
		} finally {
			pm.close();
		}
	}
	
	public abstract Class<T> getClazz();
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Query query = pm.newQuery(getClazz());
			query.setFilter("status == " + Status.ACTUAL);
			
			try {
				return (List<T>) pm.detachCopyAll((List<T>) query.execute());
			} finally {
				query.closeAll();
			}
		} finally {
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getUpdated(Date since) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Query query = pm.newQuery(getClazz());
			query.declareParameters("since");
			query.setFilter("lastUpdate >= since && status == " + Status.ACTUAL);

			try {
				return (List<T>) pm.detachCopyAll((List<T>) query.execute(since));
			} finally {
				query.closeAll();
			}
		} finally {
			pm.close();
		}
	}
	
	/* (non-Javadoc)
	 * @see alaus.radaras.server.dao.BaseDao#load(java.util.Set)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Set<T> load(Set<String> ids) {
		Set<T> result = new HashSet<T>();
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			for (String id : ids) {
				try {
					result.add(pm.detachCopy(pm.getObjectById(getClazz(), id)));
				} catch (JDOObjectNotFoundException notFoundException) {
					// Ignore
				}
			}
			return result;
		} finally {
			pm.close();
		}
	}

	/* (non-Javadoc)
	 * @see alaus.radaras.server.dao.BaseDao#getDeleted(java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getDeleted(Date since) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Query query = pm.newQuery(getClazz());
			query.declareParameters("since");
			query.setFilter("lastUpdate >= since && status == " + Status.DELETED);

			try {
				return (List<T>) pm.detachCopyAll((List<T>) query.execute(since));
			} finally {
				query.closeAll();
			}
		} finally {
			pm.close();
		}
	}

	/* (non-Javadoc)
	 * @see alaus.radaras.server.dao.BaseDao#getUpdates(java.lang.String)
	 */
	@Override
	public List<T> getUpdates(String id) {
		return new ArrayList<T>();
	}

	/**
	 * @return the idProvider
	 */
	public IdProvider getIdProvider() {
		return idProvider;
	}

	/**
	 * @param idProvider the idProvider to set
	 */
	public void setIdProvider(IdProvider idProvider) {
		this.idProvider = idProvider;
	}
}
