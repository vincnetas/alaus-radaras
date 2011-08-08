/**
 * 
 */
package alaus.radaras.client.events.loaded;

import alaus.radaras.shared.model.Pub;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Vincentas
 *
 */
public class PubLoadedEvent extends GwtEvent<PubLoadedHandler> {

	private final Pub pub;
	
	public PubLoadedEvent(Pub pub) {
		this.pub = pub;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<PubLoadedHandler> getAssociatedType() {
		return PubLoadedHandler.type;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(PubLoadedHandler handler) {
		handler.loaded(pub);
	}
	
}
