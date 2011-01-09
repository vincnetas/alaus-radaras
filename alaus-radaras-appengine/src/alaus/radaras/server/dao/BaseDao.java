package alaus.radaras.server.dao;

import java.util.Date;
import java.util.List;

import alaus.radaras.shared.model.Updatable;

public interface BaseDao<T extends Updatable> {

	void save(T object);
	
	void save(List<T> list);

	List<T> getAll();
	
	List<T> getUpdated(Date since);
	
	List<T> getDeleted(Date since);
	
	/**
	 * 
	 * @param id
	 *            Object id
	 * @return Returns list of updates with status APPLIED and UPDATE that have
	 *         parentId of specified object
	 */
	List<T> getUpdates(String id);

}