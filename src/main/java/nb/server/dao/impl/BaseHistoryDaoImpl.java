/**
 * 
 */
package nb.server.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import nb.server.dao.BaseHistoryDao;
import nb.shared.model.BaseHistoryObject;
import nb.shared.model.BaseHistoryObject.State;

import org.apache.commons.lang.ObjectUtils;

/**
 * @author Vincentas
 *
 */
public abstract class BaseHistoryDaoImpl<T extends BaseHistoryObject> extends BaseDaoImpl<T> implements BaseHistoryDao<T>{

	/* (non-Javadoc)
	 * @see nb.server.dao.BaseDao#readAll()
	 */
	@Override
	public List<T> readAll() {
		T filter;
		try {
			filter = getClazz().newInstance();
		} catch (InstantiationException e) {
			throw new DaoError(e);
		} catch (IllegalAccessException e) {
			throw new DaoError(e);
		}
		
		filter.setState(State.CURRENT);
		return findBy(filter);
	}

	@Override
	public T readCurrent(final String objectId) {
		return readObjectWithState(objectId, State.CURRENT);		
	}
	
	private T readObjectWithState(final String objectId, final State state) {
		return execute(new PersistenceManagerCallback<T>() {
			
			@Override
			public T callback(PersistenceManager pm) {
				Query query = pm.newQuery(getClazz());			
				query.setFilter("objectId == objectIdParam && state == '" + state.toString() + "'");
				query.declareParameters("java.lang.String objectIdParam");
						
				List<T> list = (List<T>) query.execute(objectId);
				T result = null;
				if (!list.isEmpty()) {
					if (list.size() == 1) {
						result = list.get(0);						
					} else {
						throw new DaoError("Should return only one element");
					}
				}
				
				return result;
			}
		});		
	}

	@Override
	public T readDeleted(String objectId) {
		return readObjectWithState(objectId, State.DELETED);
	}

	@Override
	public List<T> readDeleted(final Date since) {
		return execute(new PersistenceManagerListCallback<T>() {

			@Override
			public List<T> callback(PersistenceManager pm) {
				Query query = pm.newQuery(getClazz());			
				query.setFilter("deletionDate >= since && state == '" + State.DELETED + "'");
				query.declareParameters("java.util.Date since");

				try {
					return (List<T>) query.execute(since);
				} finally {
					query.closeAll();
				}
			}
		});
	}

	@Override
	public List<T> readUpdated(final Date since) {
		return execute(new PersistenceManagerListCallback<T>() {

			@Override
			public List<T> callback(PersistenceManager pm) {
				Query query = pm.newQuery(getClazz());			
				query.setFilter("approvalDate >= since && state == '" + State.CURRENT + "'");
				query.declareParameters("java.util.Date since");

				try {
					return (List<T>) query.execute(since);
				} finally {
					query.closeAll();
				}
			}
		});
	}

	@Override
	public List<T> read(final String objectId, final State state) {
		return execute(new PersistenceManagerListCallback<T>() {

			@Override
			public List<T> callback(PersistenceManager pm) {
				Query query = pm.newQuery(getClazz());			
				query.setFilter("objectId == oId && state == '" + state.toString() + "'");
				query.declareParameters("java.lang.String oId");

				try {
					return (List<T>) query.execute(objectId);
				} finally {
					query.closeAll();
				}
			}
		});
	}

	/* (non-Javadoc)
	 * @see nb.server.dao.BaseDao#getFirstInstance(java.lang.String)
	 */
	@Override
	public T getFirstInstance(final String objectId) {
		return execute(new PersistenceManagerCallback<T>() {
			
			@Override
			public T callback(PersistenceManager pm) {
				Query query = pm.newQuery(getClazz());			
				query.setFilter("objectId == oId");
				query.declareParameters("java.lang.String oId");
						
				List<T> list = (List<T>) query.execute(objectId);
				T result = null;
				// TODO fix this mess
//				if (!list.isEmpty()) {
//					if (list.size() == 1) {
//						result = list.get(0);						
//					} else {
//						throw new Error("Should return only one element");
//					}
//				}

				for (T t : list) {
					if (ObjectUtils.equals(t.getCreationDate(), t.getFirstCreationDate())) {
						result = t;
						break;
					}
				}
				
				return result;
			}
		});		
	}
	
	/* (non-Javadoc)
	 * @see nb.server.dao.BaseDao#readAll(java.lang.String)
	 */
	@Override
	public List<T> readAll(final String objectId) {
		return execute(new PersistenceManagerListCallback<T>() {

			@Override
			public List<T> callback(PersistenceManager pm) {
				Query query = pm.newQuery(getClazz());			
				query.setFilter("objectId == oId");
				query.declareParameters("java.lang.String oId");

				try {
					return (List<T>) query.execute(objectId);
				} finally {
					query.closeAll();
				}
			}
		});
	}

	@SuppressWarnings("unchecked")
	public Class<T> getClazz() {
	    Type daoType = getClass().getGenericSuperclass();
	    Type[] params = ((ParameterizedType) daoType).getActualTypeArguments();
	    return (Class<T>) params[0];
	}
}
