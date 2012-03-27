/**
 * 
 */
package alaus.radaras.client.events.saved;

import alaus.radaras.shared.model.Beer;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent.Type;

/**
 * @author Vincentas
 *
 */
public interface BeerSavedHandler extends EventHandler {
	
	final Type<BeerSavedHandler> type = new Type<BeerSavedHandler>();
	
	void saved(Beer beer);
}
