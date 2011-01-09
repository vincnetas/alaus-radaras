/**
 * 
 */
package alaus.radaras.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent.Type;

/**
 * @author Vincentas
 *
 */
public interface StartAddPubHandler extends EventHandler {
	
	final Type<StartAddPubHandler> type = new Type<StartAddPubHandler>();
	
	void startAddPub();
}
