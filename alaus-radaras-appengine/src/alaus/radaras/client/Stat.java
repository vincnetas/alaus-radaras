/**
 * 
 */
package alaus.radaras.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;

/**
 * @author Vincentas
 *
 */
public class Stat {

	private static HandlerManager handlerManager;
	
	private static BeerServiceAsync beerService;
	
	private static AdminBeerServiceAsync adminBeerService;
	
	public synchronized static HandlerManager getHandlerManager() {
		if (handlerManager == null) {
			handlerManager = new HandlerManager(null);
		}
		
		return handlerManager;
	}
	
	public synchronized static BeerServiceAsync getBeerService() {
		if (beerService == null) {
			beerService = GWT.create(BeerService.class);
		}
		
		return beerService;
	}
	
	public synchronized static AdminBeerServiceAsync getAdminBeerService() {
		if (adminBeerService == null) {
			adminBeerService = GWT.create(AdminBeerService.class);
		}
		
		return adminBeerService;
	}
	
	
}
