/**
 * 
 */
package alaus.radaras.client.events;

import alaus.radaras.client.ui.filter.PubFilter;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Vincentas
 *
 */
public class PubFilterEvent extends GwtEvent<PubFilterHandler> {

	private final PubFilter filter;
	
	public PubFilterEvent(PubFilter filter) {
		this.filter = filter;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<PubFilterHandler> getAssociatedType() {
		return PubFilterHandler.type;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(PubFilterHandler handler) {
		handler.filter(filter);
	}
	
}
