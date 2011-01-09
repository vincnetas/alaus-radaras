package alaus.radaras.client;

import java.util.List;

import alaus.radaras.shared.model.Location;
import alaus.radaras.shared.model.Pub;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("beerService")
public interface BeerService extends RemoteService {
	List<Pub> greetServer(String name) throws IllegalArgumentException;
	
	List<Pub> findPubs(Location location, double radius);
	
	void savePub(Pub pub);
}
