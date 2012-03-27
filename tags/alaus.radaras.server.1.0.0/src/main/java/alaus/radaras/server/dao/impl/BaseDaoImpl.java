/**
 * 
 */
package alaus.radaras.server.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import alaus.radaras.server.dao.BaseDao;
import alaus.radaras.server.dao.IdProvider;
import alaus.radaras.shared.model.Updatable;

import com.google.inject.Inject;

/**
 * @author Vincentas
 *
 */
public class BaseDaoImpl<T extends Updatable> implements BaseDao<T> {
	
	@Inject
	PersistenceManagerFactory pmf;
	
	@Inject
	IdProvider idProvider;
	
	/* (non-Javadoc)
	 * @see alaus.radaras.server.dao.BaseDao#save(java.lang.Object)
	 */
	
	public T add(T object) {
		object.setId(getIdProvider().getId());
		
		PersistenceManager pm = getPmf().getPersistenceManager();
		try {
			pm.makePersistent(object);
		} finally {
			pm.close();
		}
		
		return object;
	}
	
	/* (non-Javadoc)
	 * @see alaus.radaras.server.dao.BaseDao#save(alaus.radaras.shared.model.Updatable)
	 */
	
	public T save(T object) {
		PersistenceManager pm = getPmf().getPersistenceManager();
		try {
			pm.makePersistent(object);
		} finally {
			pm.close();
		}
		
		return object;
	}
	
	
	public List<T> add(List<T> list) {
		for (T object : list) {
			object.setId(getIdProvider().getId());
		}
		
		PersistenceManager pm = getPmf().getPersistenceManager();
		try {
			pm.makePersistentAll(list);
		} finally {
			pm.close();
		}
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public Class<T> getClazz() {
	    Type daoType = getClass().getGenericSuperclass();
	    Type[] params = ((ParameterizedType) daoType).getActualTypeArguments();
	    return (Class<T>) params[0];
	}
	
	@SuppressWarnings("unchecked")
	
	public List<T> getAll() {
		PersistenceManager pm = getPmf().getPersistenceManager();
		try {
			Query query = pm.newQuery(getClazz());
			query.setFilter("parentId == null");
			
			try {
				return (List<T>) pm.detachCopyAll((List<T>) query.execute());
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
	
	public Set<T> load(Set<String> ids) {
		Set<T> result = new HashSet<T>();
		PersistenceManager pm = getPmf().getPersistenceManager();
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
	
	public List<T> getDeleted(Date since) {
		PersistenceManager pm = getPmf().getPersistenceManager();
		try {
			Query query = pm.newQuery(getClazz());			
			query.setFilter("lastUpdate >= since && parentId == null && deleted == 1");
			query.declareParameters("java.util.Date since");

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
	 * @see alaus.radaras.server.dao.BaseDao#delete(java.lang.String)
	 */
	
	public void delete(String id) {
		PersistenceManager pm = getPmf().getPersistenceManager();
		try {
			pm.deletePersistent(get(id));
		} finally {
			pm.close();
		}
	}

	/* (non-Javadoc)
	 * @see alaus.radaras.server.dao.BaseDao#getUpdated(java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	
	public List<T> getUpdated(Date since) {
		PersistenceManager pm = getPmf().getPersistenceManager();
		try {
			Query query = pm.newQuery(getClazz());			
			query.setFilter("lastUpdate >= since && parentId == null && deleted == NULL");
			query.declareParameters("java.util.Date since");

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
	@SuppressWarnings("unchecked")
	
	public List<T> getUpdates(String id) {
		PersistenceManager pm = getPmf().getPersistenceManager();
		try {
			Query query = pm.newQuery(getClazz());			
			query.setFilter("parentId == id && approved == null");
			query.setOrdering("lastUpdate ASC");
			query.declareParameters("String id");

			try {
				return (List<T>) pm.detachCopyAll((List<T>) query.execute(id));
			} finally {
				query.closeAll();
			}
		} finally {
			pm.close();
		}
	}	
	
	/* (non-Javadoc)
	 * @see alaus.radaras.server.dao.BaseDao#getUpdates()
	 */
	@SuppressWarnings("unchecked")
	
	public List<T> getUpdates() {
		PersistenceManager pm = getPmf().getPersistenceManager();
		try {			
			List<T> result = new ArrayList<T>();			
			Query query = pm.newQuery(getClazz());
			query.setFilter("parentId == null && modified == true");
			
			result.addAll(pm.detachCopyAll((List<T>)query.execute()));
			
			return result;
		} finally {
			pm.close();
		}
	}

	/* (non-Javadoc)
	 * @see alaus.radaras.server.dao.BaseDao#get(java.lang.String)
	 */
	
	public T get(String id) {
		PersistenceManager pm = getPmf().getPersistenceManager();
		try {
			return pm.detachCopy(pm.getObjectById(getClazz(), id));
		} catch (JDOObjectNotFoundException e) {
		    return null;
		} finally {
			pm.close();
		}
	}

	
	/* (non-Javadoc)
	 * @see alaus.radaras.server.dao.BaseDao#getApproved()
	 */
	@SuppressWarnings("unchecked")
	
	public List<T> getApproved() {
		PersistenceManager pm = getPmf().getPersistenceManager();
		try {
			Query query = pm.newQuery(getClazz());			
			query.setFilter("parentId == null && approved == true");

			try {
				return (List<T>) pm.detachCopyAll((List<T>) query.execute());
			} finally {
				query.closeAll();
			}
		} finally {
			pm.close();
		}
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

	/**
	 * @return the pmf
	 */
	public PersistenceManagerFactory getPmf() {
		return pmf;
	}

	/**
	 * @param pmf the pmf to set
	 */
	public void setPmf(PersistenceManagerFactory pmf) {
		this.pmf = pmf;
	}
	
	
	
}
