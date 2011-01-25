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
public interface PubRemovedHandler extends EventHandler {
	
	final Type<PubRemovedHandler> type = new Type<PubRemovedHandler>();
	
	void pubRemoved(Pub pub);
}
