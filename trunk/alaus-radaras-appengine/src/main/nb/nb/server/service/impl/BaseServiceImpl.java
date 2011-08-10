/**
 * 
 */
package nb.server.service.impl;

import java.util.Date;
import java.util.List;

import nb.server.dao.BaseDao;
import nb.server.dao.IdProvider;
import nb.server.service.BaseService;
import nb.server.service.RoleHandler;
import nb.server.service.UserService;
import nb.shared.model.BaseObject;
import nb.shared.model.BaseObject.State;
import nb.shared.model.User;

import com.google.inject.Inject;

/**
 * @author Vincentas
 * 
 */
public abstract class BaseServiceImpl<T extends BaseObject> implements
		BaseService<T> {

	@Inject
	private UserService userService;

	@Inject
	private RoleHandler roleHandler;
	
	@Inject
	private IdProvider idProvider;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nb.server.service.BaseService#suggest(nb.shared.model.BaseObject)
	 */
	@Override
	public void suggest(T value) {
		if (value.getObjectId() != null) {
			checkDeleted(value.getObjectId());
		}
		
		if (!getRoleHandler().canSuggest(getUserService().getCurrentUser(), value)) {
			throw new Error("Can't suggest");
		}

		value.setCreatedBy(getCurrentUserId());
		value.setCreationDate(new Date());		
		value.setState(State.SUGGESTION);
		
		if (value.getObjectId() == null) {			
			value.setObjectId(getIdProvider().getId());
			value.setFirstCreationDate(new Date());
		} else {
			value.setFirstCreationDate(getFirstCreationDate(value));
		}

		T result = getBaseDao().create(value);
		
		if (getRoleHandler().canApprove(getUserService().getCurrentUser(), value)) {
			approve(result.getId());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nb.server.service.BaseService#approve(nb.shared.model.BaseObject)
	 */
	@Override
	public void approve(String id) {
		T value = getBaseDao().read(id);
		if (value == null) {
			throw new Error("Object not found : " + id);
		}

		checkDeleted(value.getObjectId());

		if (!getRoleHandler().canApprove(getUserService().getCurrentUser(), value)) {
			throw new Error("Can't approve");
		}

		if (!value.getState().equals(State.SUGGESTION)) {
			throw new Error("Only PROPOSAL objects can be approved");
		}
		
		T currentValue = getBaseDao().readCurrent(value.getObjectId());
		if (currentValue != null) {
			currentValue.setState(State.HISOTRY);
			getBaseDao().update(currentValue);
		}
		
		value.setApprovedBy(getCurrentUserId());
		value.setApprovalDate(new Date());
		value.setState(State.CURRENT);
		getBaseDao().update(value);
	}

	/* (non-Javadoc)
	 * @see nb.server.service.BaseService#reject(nb.shared.model.BaseObject)
	 */
	@Override
	public void reject(String id) {
		T value = getBaseDao().read(id);
		if (value == null) {
			throw new Error("Object not found : " + id);
		}
		
		checkDeleted(value.getObjectId());
		
		if (!getRoleHandler().canReject(getUserService().getCurrentUser(), value)) {
			throw new Error("Can't reject");
		}
		
		if (!value.getState().equals(State.SUGGESTION)) {
			throw new Error("Only PROPOSAL objects can be rejected");
		}
		
		value.setApprovedBy(getCurrentUserId());
		value.setApprovalDate(new Date());
		value.setState(State.REJECTED);
		getBaseDao().update(value);
	}
	
	/* (non-Javadoc)
	 * @see nb.server.service.BaseService#delete(java.lang.String)
	 */
	@Override
	public void delete(String objectId) {
		checkDeleted(objectId);
		T value = getBaseDao().readCurrent(objectId);
		if (value == null) {
			throw new Error("No current object found : " + objectId);
		}
			
		value.setState(State.DELETED);
		value.setDeletedBy(getCurrentUserId());
		value.setDeletionDate(new Date());
		getBaseDao().update(value);
	}

	/* (non-Javadoc)
	 * @see nb.server.service.BaseService#getDeleted(java.util.Date)
	 */
	@Override
	public List<T> getDeleted(Date since) {
		return getBaseDao().readDeleted(since);
	}

	/* (non-Javadoc)
	 * @see nb.server.service.BaseService#getUpdated(java.util.Date)
	 */
	@Override
	public List<T> getUpdated(Date since) {
		return getBaseDao().readUpdated(since);
	}

	/* (non-Javadoc)
	 * @see nb.server.service.BaseService#getSuggestions(java.lang.String)
	 */
	@Override
	public List<T> getSuggestions(String objectId) {
		return getBaseDao().read(objectId, State.SUGGESTION);
	}

	private String getCurrentUserId() {
		User currentUser = getUserService().getCurrentUser();
		return currentUser != null ? currentUser.getUserId() : null;
	}

	/**
	 * Returns date when first instance of passed in object was created.
	 * 
	 * @param object
	 *            Object to check.
	 * @return Returns first creation date or if this object is the first, then
	 *         it's creation date.
	 */
	private Date getFirstCreationDate(T object) {
		assert object.getCreationDate() != null;

		Date result = object.getCreationDate();

		T firstInstance = getBaseDao().getFirstInstance(object.getObjectId());
		if (firstInstance != null) {
			result = firstInstance.getFirstCreationDate();
		}

		return result;
	}
	
	/* (non-Javadoc)
	 * @see nb.server.service.BaseService#getHistory(java.lang.String)
	 */
	@Override
	public List<T> getHistory(String objectId) {
		return getBaseDao().readAll(objectId);
	}
	
	/* (non-Javadoc)
	 * @see nb.server.service.BaseService#getCurrent(java.lang.String)
	 */
	@Override
	public T getCurrent(String objectId) {
		return getBaseDao().readCurrent(objectId);
	}

	/* (non-Javadoc)
     * @see nb.server.service.BaseService#getCurrent(java.util.List)
     */
    @Override
    public List<T> getCurrent(List<String> objectIds) {
        return getBaseDao().read(objectIds);
    }

    private void checkDeleted(String objectId) {
		T value = getBaseDao().readDeleted(objectId);
		if (value != null) {
			throw new Error("Object is deleted");
		}
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * @param userService
	 *            the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public abstract BaseDao<T> getBaseDao();

	/**
	 * @return the roleHandler
	 */
	public RoleHandler getRoleHandler() {
		return roleHandler;
	}

	/**
	 * @param roleHandler the roleHandler to set
	 */
	public void setRoleHandler(RoleHandler roleHandler) {
		this.roleHandler = roleHandler;
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
