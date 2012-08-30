/**
 * 
 */
package alaus.radaras.client.events.loaded;

import alaus.radaras.shared.model.Brand;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Vincentas
 *
 */
public class BrandLoadedEvent extends GwtEvent<BrandLoadedHandler> {

	private final Brand brand;
	
	public BrandLoadedEvent(Brand brand) {
		this.brand = brand;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<BrandLoadedHandler> getAssociatedType() {
		return BrandLoadedHandler.type;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(BrandLoadedHandler handler) {
		handler.loaded(brand);
	}
	
}
