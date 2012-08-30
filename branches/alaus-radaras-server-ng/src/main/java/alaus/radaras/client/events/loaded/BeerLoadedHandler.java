/**
 * 
 */
package alaus.radaras.client.events.loaded;

import alaus.radaras.shared.model.Beer;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent.Type;

/**
 * @author Vincentas
 *
 */
public interface BeerLoadedHandler extends EventHandler {
	
	final Type<BeerLoadedHandler> type = new Type<BeerLoadedHandler>();
	
	void loaded(Beer beer);
}
