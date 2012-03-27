/**
 * 
 */
package alaus.radaras.client.events.loaded;

import alaus.radaras.shared.model.Beer;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Vincentas
 *
 */
public class BeerLoadedEvent extends GwtEvent<BeerLoadedHandler> {

	private final Beer beer;
	
	public BeerLoadedEvent(Beer beer) {
		this.beer = beer;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<BeerLoadedHandler> getAssociatedType() {
		return BeerLoadedHandler.type;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(BeerLoadedHandler handler) {
		handler.loaded(beer);
	}
	
}
