/**
 * 
 */
package alaus.radaras.client.events.saved;

import alaus.radaras.shared.model.Beer;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Vincentas
 *
 */
public class BeerSavedEvent extends GwtEvent<BeerSavedHandler> {

	private final Beer beer;
	
	public BeerSavedEvent(Beer beer) {
		this.beer = beer;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<BeerSavedHandler> getAssociatedType() {
		return BeerSavedHandler.type;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(BeerSavedHandler handler) {
		handler.saved(beer);
	}
	
}
