/**
 * 
 */
package alaus.radaras.client.events.loaded;

import alaus.radaras.shared.model.Pub;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent.Type;

/**
 * @author Vincentas
 *
 */
public interface PubLoadedHandler extends EventHandler {
	
	final Type<PubLoadedHandler> type = new Type<PubLoadedHandler>();
	
	void loaded(Pub pub);
}
