/**
 * 
 */
package alaus.radaras.client.events.saved;

import alaus.radaras.shared.model.Pub;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent.Type;

/**
 * @author Vincentas
 *
 */
public interface PubSavedHandler extends EventHandler {
	
	final Type<PubSavedHandler> type = new Type<PubSavedHandler>();
	
	void saved(Pub pub);
}
