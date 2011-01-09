package alaus.radaras.server.dao;

import java.util.Date;
import java.util.List;

public interface BaseDao<T> {

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