/**
 * 
 */
package alaus.radaras.server.dao;

import java.util.List;

import alaus.radaras.shared.model.Updatable;
import alaus.radaras.shared.model.UpdateRecord;

/**
 * @author Vincentas
 *
 */
public interface BaseService<T extends Updatable> {

	T add(T object);
	
	T addUpdate(T object);

	T applyUpdate(String id);

	T rejectUpdate(String id);

	List<UpdateRecord<T>> getUpdates();
	
	
}
