/**
 * 
 */
package alaus.radaras.client.events;

import alaus.radaras.shared.model.Pub;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Vincentas
 *
 */
public class PubRemovedEvent extends GwtEvent<PubRemovedHandler> {

	private final Pub pub;
	
	public PubRemovedEvent(Pub pub) {
		this.pub = pub;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<PubRemovedHandler> getAssociatedType() {
		return PubRemovedHandler.type;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(PubRemovedHandler handler) {
		handler.pubRemoved(pub);
	}
	
}
