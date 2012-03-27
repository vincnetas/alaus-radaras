/**
 * 
 */
package alaus.radaras.client.events.saved;

import alaus.radaras.shared.model.Brand;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent.Type;

/**
 * @author Vincentas
 *
 */
public interface BrandSavedHandler extends EventHandler {
	
	final Type<BrandSavedHandler> type = new Type<BrandSavedHandler>();
	
	void saved(Brand brand);
}
