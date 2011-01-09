/**
 * 
 */
package alaus.radaras.client.events;

import java.util.List;

import alaus.radaras.shared.model.BeerLocation;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent.Type;

/**
 * @author Vincentas
 *
 */
public interface BeerLocationsLoadedHandler extends EventHandler {
	
	final Type<BeerLocationsLoadedHandler> type = new Type<BeerLocationsLoadedHandler>();
	
	void locationsLoaded(List<BeerLocation> locations);
}
