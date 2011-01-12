package alaus.radaras.client;

import java.util.List;
import java.util.Set;

import alaus.radaras.shared.model.Beer;
import alaus.radaras.shared.model.Brand;
import alaus.radaras.shared.model.IPLocation;
import alaus.radaras.shared.model.Location;
import alaus.radaras.shared.model.Pub;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface BeerServiceAsync {
	
	void findPubs(Location location, double radius, AsyncCallback<List<Pub>> callback);



	void getBeerSuggestions(String queryString, int limit, AsyncCallback<List<Beer>> callback);

	void getBrandSuggestions(String queryString, int limit, AsyncCallback<List<Brand>> callback);

	void savePub(Pub pub, AsyncCallback<Pub> callback);
	
	void saveBeer(Beer beer, AsyncCallback<Beer> asyncCallback);

	void saveBrand(Brand brand, AsyncCallback<Brand> callback);



	void loadBeer(Set<String> beerIds, AsyncCallback<Set<Beer>> callback);



	void loadPub(Set<String> pubIds, AsyncCallback<Set<Pub>> callback);



	void loadBrand(Set<String> brandIds, AsyncCallback<Set<Brand>> callback);



	void getMyLocation(AsyncCallback<IPLocation> callback);
	
}
