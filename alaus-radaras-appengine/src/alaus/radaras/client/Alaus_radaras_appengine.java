package alaus.radaras.client;

import alaus.radaras.client.ui.BasePanel;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Alaus_radaras_appengine implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		if (!Maps.isLoaded()) {
			Window.alert("The Maps API is not installed." + "  The <script> tag that loads the Maps API may be missing or your Maps key may be wrong.");
			return;
		}

		if (!Maps.isBrowserCompatible()) {
			Window.alert("The Maps API is not compatible with this browser.");
			return;
		}
		
		RootPanel.get().add(new BasePanel());
	}
}
