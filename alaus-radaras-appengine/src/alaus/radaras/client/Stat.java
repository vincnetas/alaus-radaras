/**
 * 
 */
package alaus.radaras.client;

import alaus.radaras.client.command.Command;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;

/**
 * @author Vincentas
 *
 */
public class Stat {

	private static HandlerManager handlerManager;
	
	private static BeerServiceAsync beerService;
	
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
	
	public static void execute(Command command) {
		command.execute();
	}
	
}
