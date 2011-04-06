package alaus.radaras.server.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import alaus.radaras.shared.model.Updatable;

public interface BaseDao<T extends Updatable> {

	T add(T object);
	
	T save(T object);
	
	List<T> add(List<T> list);
	
	Set<T> load(Set<String> ids);

	List<T> getAll();
	
	List<T> getDeleted(Date since);
	
	List<T> getUpdated(Date since);
	
	/**
	 * 
	 * @param id
	 *            Object id
	 * @return Returns not moderated object with specified parent id.
	 */
	List<T> getUpdates(String id);
	
	/**
	 * 
	 * @return Returns base objects that have any changes pending for
	 *         moderation.
	 */
	List<T> getUpdates();
	
	T get(String id);
	
	List<T> getApproved();

	
}