/**
 * 
 */
package alaus.radaras.server.dao.impl;

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

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.inject.Inject;

/**
 * @author Vincentas
 *
 */
public abstract class BaseDaoImpl<T extends Updatable> implements BaseDao<T> {

	@Inject
	UserService userService;
	
	@Inject
	IdProvider idProvider;
	
	/* (non-Javadoc)
	 * @see alaus.radaras.server.dao.BaseDao#save(java.lang.Object)
	 */
	@Override
	public T add(T object) {
		object.setId(getIdProvider().getId());
		setUpdateInfo(object);
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(object);
		} finally {
			pm.close();
		}
		
		return object;
	}
	
	private void setUpdateInfo(T object) {
		object.setLastUpdate(new Date());
		User user = getUserService().getCurrentUser();
		if (user != null) {
			object.setUpdatedBy(user.getEmail());
			object.setApproved(true);
		}
	}

	/* (non-Javadoc)
	 * @see alaus.radaras.server.dao.BaseDao#save(alaus.radaras.shared.model.Updatable)
	 */
	@Override
	public T save(T object) {
		setUpdateInfo(object);
		
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
			setUpdateInfo(object);
		}
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistentAll(list);
		} finally {
			pm.close();
		}
		
		return list;
	}
	
	public abstract Class<T> getClazz();
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Query query = pm.newQuery(getClazz());
			query.setFilter("parentId == NULL");
			
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
			query.setFilter("lastUpdate >= since && parentId == NULL");

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
			query.setFilter("lastUpdate >= since && parentId == NULL");

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
			query.declareParameters("id");
			query.setFilter("parentId == id AND approved == NULL ORDER BY lastUpdate ASC");

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
			Query query = pm.newQuery(getClazz());
			query.setFilter("parentId == NULL AND (modified == TRUE OR approved == NULL)");

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
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}



	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	
	
}
