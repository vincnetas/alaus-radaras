/**
 * 
 */
package nb.server.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import nb.server.dao.BaseHistoryDao;
import nb.server.dao.IdProvider;
import nb.shared.model.BaseObject;
import nb.shared.model.BaseObject.State;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import com.google.inject.Inject;

/**
 * @author Vincentas
 *
 */
public abstract class BaseHistoryDaoImpl<T extends BaseObject> extends DataStoreTemplate implements BaseHistoryDao<T>{

	@Inject
	private IdProvider idProvider;

	/* (non-Javadoc)
	 * @see nb.server.dao.BaseDao#create(nb.shared.model.BaseObject)
	 */
	@Override
	public T create(final T object) {
		return execute(new PersistenceManagerCallback<T>() {
			
			@Override
			public T callback(PersistenceManager pm) {
				object.setId(getIdProvider().getId());
				return pm.makePersistent(object);
			}
		});
	}
	
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

	/* (non-Javadoc)
	 * @see nb.server.dao.BaseDao#read(java.lang.String)
	 */
	@Override
	public T read(final String id) {
		return execute(new PersistenceManagerCallback<T>() {

			@Override
			public T callback(PersistenceManager pm) {
				T result = null;
				try {
					result = pm.getObjectById(getClazz(), id);
				} catch (JDOObjectNotFoundException notFoundException) {
					/*
					 * Not found, return null
					 */
				}
				
				return result;
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see nb.server.dao.BaseDao#read(java.util.List)
	 */
	@Override
	public List<T> read(List<String> ids) {
		List<T> result = new ArrayList<T>(ids.size());
		
		for (String id : ids) {
			result.add(read(id));
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see nb.server.dao.BaseDao#update(nb.shared.model.BaseObject)
	 */
	@Override
	public T update(final T object) {
		if (object.getId() == null) {
			throw new DaoError("Object has no id. Update not possible");
		}
			
		return execute(new PersistenceManagerCallback<T>() {
			
			@Override
			public T callback(PersistenceManager pm) {
				try {
					pm.getObjectById(getClazz(), object.getId());
				} catch (JDOObjectNotFoundException notFoundException) {
					throw new DaoError("Object not found " + object.getId(), notFoundException);
				}
				
				return pm.makePersistent(object);
			}
		});
	}

	/* (non-Javadoc)
	 * @see nb.server.dao.BaseDao#delete(java.lang.String)
	 */
	@Override
	public void delete(final String id) {
		execute(new PersistenceManagerCallback<T>() {
			
			@Override
			public T callback(PersistenceManager pm) {
				T value = read(id);
				if (value == null) {
					throw new DaoError("No object to delete with id " + id);
				}
				
				pm.deletePersistent(value);
				return null;
			}
		});
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

	private static boolean filterClass(Class clazz) {
		return String.class.equals(clazz) || State.class.equals(clazz);
	}
	/* (non-Javadoc)
	 * @see nb.server.dao.BaseDao#findBy(nb.shared.model.BaseObject)
	 */
	@Override
	public List<T> findBy(final T filter) {
		return execute(new PersistenceManagerListCallback<T>() {

			@Override
			public List<T> callback(PersistenceManager pm) {
				List<String> filters = new ArrayList<String>();
				List<String> parameters = new ArrayList<String>();
				Map<String, Object> parameterValues = new HashMap<String, Object>();
				
				Map<String, Object> properties;
				try {
					properties = BeanUtils.describe(filter);
				} catch (IllegalAccessException e) {
					throw new DaoError(e);
				} catch (InvocationTargetException e) {
					throw new DaoError(e);
				} catch (NoSuchMethodException e) {
					throw new DaoError(e);
				}
				
				for (Entry<String, Object> entry : properties.entrySet()) {

					if (entry.getValue() != null) {
						Class propertyType;
						try {
							propertyType = PropertyUtils.getPropertyType(filter, entry.getKey());
						} catch (IllegalAccessException e) {
							throw new DaoError(e);
						} catch (InvocationTargetException e) {
							throw new DaoError(e);
						} catch (NoSuchMethodException e) {
							throw new DaoError(e);
						}
						
						if (filterClass(propertyType)) {
							String parameter = "param" + entry.getKey();
							filters.add(entry.getKey() + "==" + parameter);
							parameters.add(getPropertyClass(propertyType) + " " + parameter);
							parameterValues.put(parameter, getPropertyValue(entry.getValue()));
						}
					}
				}

				List<T> result;
				if (!parameterValues.isEmpty()) {				
					Query query = pm.newQuery(getClazz());			
					query.setFilter(StringUtils.join(filters, " && "));
					query.declareParameters(StringUtils.join(parameters, ','));
					
	
					try {
						result = (List<T>) query.executeWithMap(parameterValues);
					} finally {
						query.closeAll();
					}
				} else {
					result = Collections.EMPTY_LIST;
				}
				
				return result;
			}
		});
	}
	
	private String getPropertyClass(Class propertyType) {
		return String.class.getCanonicalName();
	}
	
	private Object getPropertyValue(Object propertyValue) {
		if (String.class.equals(propertyValue.getClass())){
			return propertyValue;
		} else if (State.class.equals(propertyValue.getClass())) {
			return propertyValue.toString();
		} else {
			throw new DaoError("unsuported property class " + propertyValue.getClass().getCanonicalName());
		}
	}
	
	

}
