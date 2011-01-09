package alaus.radaras.client;

import java.util.List;

import alaus.radaras.shared.model.Location;
import alaus.radaras.shared.model.Pub;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface BeerServiceAsync {
	
	void greetServer(String input, AsyncCallback<List<Pub>> callback) throws IllegalArgumentException;

	void findPubs(Location location, double radius, AsyncCallback<List<Pub>> callback);

	void savePub(Pub pub, AsyncCallback<Void> callback);
	
}
