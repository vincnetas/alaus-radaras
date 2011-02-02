/**
 * 
 */
package alaus.radaras.server.dao.impl;

import alaus.radaras.server.dao.IdProvider;

/**
 * @author Vincentas
 *
 */
public class IdProviderImpl implements IdProvider {

	private long lastId = System.currentTimeMillis();
	
	/* (non-Javadoc)
	 * @see alaus.radaras.server.dao.IdProvider#getId()
	 */
	@Override
	public synchronized String getId() {
		return String.valueOf(lastId++);
	}

}
