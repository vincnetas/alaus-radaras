package nb.server.dao;

import java.util.List;

public interface BaseDao<T> {

	T create(T object);

	/**
	 * Returns object with specified id
	 * 
	 * @param id
	 * @return Returns null if no object found
	 */
	T read(String id);

	/**
	 * Returns List of objects with specified id's. If object is not found for
	 * specified id, in result element at same index as id will be null.
	 * 
	 * @param ids
	 * @return
	 */
	List<T> read(List<String> ids);

	T update(T object);

	void delete(String id);

	List<T> findBy(T filter);

	/**
	 * Returns all
	 * @return
	 */
	public abstract List<T> readAll();

}