/**
 * 
 */
package alaus.radaras.client.events;

import alaus.radaras.shared.model.Pub;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent.Type;

/**
 * @author Vincentas
 *
 */
public interface PubAddedHandler extends EventHandler {
	
	final Type<PubAddedHandler> type = new Type<PubAddedHandler>();
	
	void pubAdded(Pub pub);
}
