/**
 * 
 */
package alaus.radaras.server.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;

import alaus.radaras.server.dao.BaseDao;
import alaus.radaras.server.dao.BaseService;
import alaus.radaras.shared.model.Updatable;
import alaus.radaras.shared.model.UpdateRecord;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.inject.Inject;

/**
 * @author Vincentas
 *
 */
public abstract class BaseServiceImpl<T extends Updatable> implements BaseService<T>{

	@Inject
	private UserService userService;
	
	/* (non-Javadoc)
	 * @see alaus.radaras.server.dao.BaseService#add(alaus.radaras.shared.model.Updatable)
	 */
	@Override
	public T add(T object) {
		if (object.getParentId() != null) {
			throw new Error("Base object can't have parent id " + object);
		}
		
		object.setApproved(null);
		object.setModified(true);
		object.setLastUpdate(new Date());		
		object.setUpdatedBy(getUserEmail());
		
		return getBaseDao().add(object);
	}

	@Override
	public T addUpdate(T update) {
		if (update.getParentId() == null) {
			throw new Error("No parent id specified for update " + update);
		}
		T parent = getParent(update);
		parent.setModified(true);
		
		prepareUpdate(update, parent);
		
		update.setApproved(null);
		update.setModified(false);
		update.setLastUpdate(new Date());		
		update.setUpdatedBy(getUserEmail());

		getBaseDao().save(parent);
		return getBaseDao().add(update);
	}

    private T getParent(T update) {
		T parent = getBaseDao().get(update.getParentId());
		if (parent == null) {
			throw new Error("Parent doesn't exists for update " + update);
		}
		
		return parent;
	}
	
	@Override
	public T applyUpdate(String id) {
		T update = getBaseDao().get(id);
		if (update.getParentId() == null) {
			/*
			 * If update id base object
			 */
			update.setApproved(true);
			update.setModified(false);
			
			return getBaseDao().save(update);
		} else {
			T parent = getParent(update);
	
			applyUpdate(parent, update);
			
			update.setApproved(true);
			parent.setLastUpdate(update.getLastUpdate());

			getBaseDao().save(update);
			List<T> updates = getBaseDao().getUpdates(update.getParentId());
			if (updates.isEmpty()) {
				parent.setModified(false);			
			}
			
			return getBaseDao().save(parent);
		}
	}
    
	protected abstract void prepareUpdate(T update, T parent);
	
	protected abstract void applyUpdate(T object, T update);

	@Override
	public T rejectUpdate(String id) {
		T update = getBaseDao().get(id);
		if (update.getParentId() == null) {
			/*
			 * If update id base object
			 */
			update.setApproved(false);
			update.setModified(false);
			
			return getBaseDao().save(update);
		} else {
			T parent = getParent(update);
			
			update.setApproved(false);

			getBaseDao().save(update);
			List<T> updates = getBaseDao().getUpdates(update.getParentId());
			if (updates.isEmpty()) {
				parent.setModified(false);
			}	
				
			return getBaseDao().save(parent);
		}
	}

	@Override
	public List<UpdateRecord<T>> getUpdates() {
		List<UpdateRecord<T>> result = new ArrayList<UpdateRecord<T>>();
		List<T> updates = getBaseDao().getUpdates();
		for (T object : updates) {
			result.add(new UpdateRecord<T>(object, getBaseDao().getUpdates(object.getId())));
		}
		
		return result;
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
	
	private String getUserEmail() {
		String result = null;
	
		User user = getUserService().getCurrentUser();
		if (user != null) {
			result = user.getEmail();
		}
		
		return result;
	}
	
	/**
	 * @return the baseDao
	 */
	public abstract BaseDao<T> getBaseDao();


    protected <R> R nullIfEqual(R update, R value) {
        R result = update;
        
        if (value instanceof Collection) {
            if (CollectionUtils.isEqualCollection((Collection) update, (Collection) value)) {
                result = null;
            }
        } else {
            if (ObjectUtils.equals(update, value)) {
                result = null;
            }
        }
        
        return result;
    }

	protected <R> R defaultIfNull(R value, R defaultValue) {
		return (value != null) ? value : defaultValue;
	}
}
