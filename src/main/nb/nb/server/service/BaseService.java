/**
 * 
 */
package nb.server.service;

import java.util.Date;
import java.util.List;

import nb.shared.model.BaseObject;


/**
 * @author Vincentas
 *
 */
public interface BaseService<T extends BaseObject> {

	/**
	 * Suggest modification for object or addition of a new object. If passed
	 * object has non null objectId property it is treated as suggestion for
	 * change. If property objectId is null it's considered as suggestion to add
	 * new object.
	 * 
	 * @param value
	 *            Modified value of the object
	 */
	void suggest(T value);
	
	void approve(String id);
	
	void reject(String id);
	
	void delete(String objectId);
	
	T getCurrent(String objectId);
	
	/**
	 * 
	 * @return Returns all current objects
	 */	
	List<T> getCurrent();
	
	List<T> getCurrent(List<String> objectIds);
	
	List<T> getDeleted(Date since);
	
	List<T> getUpdated(Date since);
	
	List<T> getSuggestions(String objectId);
	
	List<T> getHistory(String objectId);

}
