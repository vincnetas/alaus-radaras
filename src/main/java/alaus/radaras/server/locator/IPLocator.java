/**
 * 
 */
package alaus.radaras.server.locator;

import alaus.radaras.shared.model.IPLocation;

/**
 * @author Vincentas Vienozinskis
 *
 */
public interface IPLocator {

	IPLocation locate(String remoteAddr);

	
}
