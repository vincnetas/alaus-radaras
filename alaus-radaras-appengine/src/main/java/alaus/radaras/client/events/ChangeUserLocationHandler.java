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
public interface ChangeUserLocationHandler extends EventHandler {
	
	final Type<ChangeUserLocationHandler> type = new Type<ChangeUserLocationHandler>();
	
	void changeLocation(String location);
}
