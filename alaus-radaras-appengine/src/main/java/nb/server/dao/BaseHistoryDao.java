package nb.server.dao;

import java.util.Date;
import java.util.List;

import nb.shared.model.BaseHistoryObject;
import nb.shared.model.BaseHistoryObject.State;


public interface BaseHistoryDao<T extends BaseHistoryObject> extends BaseDao<T> {

	T readCurrent(String objectId);

	/**
	 * Returns deleted object with specified objectId. If object is not deleted
	 * or there is no such object, null is returned.
	 * 
	 * @param objectId
	 *            Object id
	 * @return Deleted object or null if it's not deleted or not found.
	 */
	T readDeleted(String objectId);

	List<T> readDeleted(Date since);

	List<T> readUpdated(Date since);

	List<T> read(String objectId, State state);
	
	/**
	 * Reads all history for object
	 * 
	 * @param objectId
	 *            Object id
	 * @return Returns all instances of object
	 */
	List<T> readAll(String objectId);
	
	/**
	 * Retrieves initially created instance of object. Creation date equals
	 * first creation date and object id's equal.
	 * 
	 * @param objectId Object id
	 * @return Returns first instance of this object or null if no found.
	 */
	T getFirstInstance(String objectId);
	
}