/**
 * 
 */
package alaus.radaras.client.events.loaded;

import alaus.radaras.shared.model.Brand;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent.Type;

/**
 * @author Vincentas
 *
 */
public interface BrandLoadedHandler extends EventHandler {
	
	final Type<BrandLoadedHandler> type = new Type<BrandLoadedHandler>();
	
	void loaded(Brand brand);
}
