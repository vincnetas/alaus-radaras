/**
 * 
 */
package alaus.radaras.server.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import alaus.radaras.server.dao.BaseDao;
import alaus.radaras.server.dao.PMF;
import alaus.radaras.shared.model.Status;

/**
 * @author Vincentas
 *
 */
public abstract class BaseDaoImpl<T> implements BaseDao<T> {

	@Override
	public void save(List<T> list) {
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
}
