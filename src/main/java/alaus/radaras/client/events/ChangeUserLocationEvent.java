/**
 * 
 */
package alaus.radaras.client.events;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Vincentas
 *
 */
public class ChangeUserLocationEvent extends GwtEvent<ChangeUserLocationHandler> {

	private final String location;
	
	public ChangeUserLocationEvent(String location) {
		this.location = location;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ChangeUserLocationHandler> getAssociatedType() {
		return ChangeUserLocationHandler.type;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(ChangeUserLocationHandler handler) {
		handler.changeLocation(location);
	}
	
}
