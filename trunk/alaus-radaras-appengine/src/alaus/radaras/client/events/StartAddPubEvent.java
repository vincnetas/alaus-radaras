/**
 * 
 */
package alaus.radaras.client.events;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Vincentas
 *
 */
public class StartAddPubEvent extends GwtEvent<StartAddPubHandler> {

	public StartAddPubEvent() {

	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<StartAddPubHandler> getAssociatedType() {
		return StartAddPubHandler.type;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(StartAddPubHandler handler) {
		handler.startAddPub();
	}
	
}
