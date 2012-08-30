/**
 * 
 */
package alaus.radaras.client.events.saved;

import alaus.radaras.shared.model.Pub;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Vincentas
 *
 */
public class PubSavedEvent extends GwtEvent<PubSavedHandler> {

	private final Pub pub;
	
	public PubSavedEvent(Pub pub) {
		this.pub = pub;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<PubSavedHandler> getAssociatedType() {
		return PubSavedHandler.type;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(PubSavedHandler handler) {
		handler.saved(pub);
	}
	
}
