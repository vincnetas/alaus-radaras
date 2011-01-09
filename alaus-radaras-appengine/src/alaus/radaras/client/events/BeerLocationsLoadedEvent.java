/**
 * 
 */
package alaus.radaras.client.events;

import java.util.List;

import alaus.radaras.shared.model.BeerLocation;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Vincentas
 *
 */
public class BeerLocationsLoadedEvent extends GwtEvent<BeerLocationsLoadedHandler> {

	private final List<BeerLocation> locations;
	
	public BeerLocationsLoadedEvent(List<BeerLocation> locations) {
		this.locations = locations;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<BeerLocationsLoadedHandler> getAssociatedType() {
		return BeerLocationsLoadedHandler.type;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(BeerLocationsLoadedHandler handler) {
		handler.locationsLoaded(locations);
	}
	
}
