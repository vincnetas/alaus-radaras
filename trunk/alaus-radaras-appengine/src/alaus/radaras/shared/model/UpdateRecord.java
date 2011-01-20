/**
 * 
 */
package alaus.radaras.shared.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author Vincentas Vienozinskis
 *
 */
public class UpdateRecord<T extends Updatable> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8678389279770992957L;

	/**
	 * Current value
	 */
	private T current;
	
	/**
	 * Updates for current record sorted by update date in ascending order.
	 * Oldest update will be at index 0, most recent update will be in the end
	 * of the list.
	 */
	private List<T> updates;

	public UpdateRecord() {
		/*
		 * Empty default constructor
		 */
	}
	
	public UpdateRecord(T current, List<T> updates) {
		this.current = current;
		this.updates = updates;
	}
	
	/**
	 * @return the current
	 */
	public T getCurrent() {
		return current;
	}

	/**
	 * @param current the current to set
	 */
	public void setCurrent(T current) {
		this.current = current;
	}

	/**
	 * @return the updates
	 */
	public List<T> getUpdates() {
		return updates;
	}

	/**
	 * @param updates the updates to set
	 */
	public void setUpdates(List<T> updates) {
		this.updates = updates;
	}
	
	
}
