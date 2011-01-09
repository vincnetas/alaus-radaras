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

	/* (non-Javadoc)
	 * @see alaus.radaras.server.dao.IdProvider#getId()
	 */
	@Override
	public String getId() {
		return String.valueOf(System.currentTimeMillis());
	}

}
