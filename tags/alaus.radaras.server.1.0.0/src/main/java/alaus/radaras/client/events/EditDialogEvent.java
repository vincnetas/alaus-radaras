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
public class EditDialogEvent extends GwtEvent<PubAddedHandler> {

	private final Pub pub;
	
	public EditDialogEvent(Pub pub) {
		this.pub = pub;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<PubAddedHandler> getAssociatedType() {
		return PubAddedHandler.type;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(PubAddedHandler handler) {
		handler.pubAdded(pub);
	}
	
}
