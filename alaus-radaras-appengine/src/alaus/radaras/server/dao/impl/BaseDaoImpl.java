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
import javax.jdo.Query;

import alaus.radaras.server.dao.BaseDao;
import alaus.radaras.server.dao.IdProvider;
import alaus.radaras.server.dao.PMF;
import alaus.radaras.shared.model.Updatable;

import com.google.inject.Inject;

/**
 * @author Vincentas
 *
 */
public class BaseDaoImpl<T extends Updatable> implements BaseDao<T> {
	
	@Inject
	IdProvider idProvider;
	
	/* (non-Javadoc)
	 * @see alaus.radaras.server.dao.BaseDao#save(java.lang.Object)
	 */
	@Override
	public T add(T object) {
		object.setId(getIdProvider().getId());
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
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
	@Override
	public T save(T object) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(object);
		} finally {
			pm.close();
		}
		
		return object;
	}
	
	@Override
	public List<T> add(List<T> list) {
		for (T object : list) {
			object.setId(getIdProvider().getId());
		}
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
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
	@Override
	public List<T> getAll() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
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
			query.setFilter("lastUpdate >= since && parentId == null");

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
	@Override
	public List<T> getUpdates(String id) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
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
	@Override
	public List<T> getUpdates() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
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
	@Override
	public T get(String id) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			return pm.detachCopy(pm.getObjectById(getClazz(), id));
		} finally {
			pm.close();
		}
	}

	
	/* (non-Javadoc)
	 * @see alaus.radaras.server.dao.BaseDao#getApproved()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getApproved() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
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
	
}
