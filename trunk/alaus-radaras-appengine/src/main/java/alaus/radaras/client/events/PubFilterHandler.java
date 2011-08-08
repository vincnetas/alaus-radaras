/**
 * 
 */
package alaus.radaras.client.events;

import alaus.radaras.client.ui.filter.PubFilter;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent.Type;

/**
 * @author Vincentas
 *
 */
public interface PubFilterHandler extends EventHandler {
	
	final Type<PubFilterHandler> type = new Type<PubFilterHandler>();
	
	void filter(PubFilter filter);
}
