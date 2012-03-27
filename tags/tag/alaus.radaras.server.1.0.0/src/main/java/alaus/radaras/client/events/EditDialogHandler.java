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
public interface EditDialogHandler extends EventHandler {
	
	final Type<EditDialogHandler> type = new Type<EditDialogHandler>();
	
	void pubAdded(Pub pub);
}
