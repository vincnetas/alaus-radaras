/**
 * 
 */
package alaus.radaras.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author Vincentas
 *
 */
public class AdminEngine implements EntryPoint {

	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	@Override
	public void onModuleLoad() {
		RootPanel.get().add(new AdminPanel());
	}

}
