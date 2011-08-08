/**
 * 
 */
package alaus.radaras.client.events.saved;

import alaus.radaras.shared.model.Brand;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Vincentas
 *
 */
public class BrandSavedEvent extends GwtEvent<BrandSavedHandler> {

	private final Brand brand;
	
	public BrandSavedEvent(Brand brand) {
		this.brand = brand;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<BrandSavedHandler> getAssociatedType() {
		return BrandSavedHandler.type;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(BrandSavedHandler handler) {
		handler.saved(brand);
	}
	
}
