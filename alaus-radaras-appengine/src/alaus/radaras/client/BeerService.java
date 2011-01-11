package alaus.radaras.client;

import java.util.List;
import java.util.Set;

import alaus.radaras.shared.model.Beer;
import alaus.radaras.shared.model.Brand;
import alaus.radaras.shared.model.Location;
import alaus.radaras.shared.model.Pub;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("beerService")
public interface BeerService extends RemoteService {

	Set<Beer> loadBeer(Set<String> beerIds);
	
	Set<Pub> loadPub(Set<String> pubIds);
	
	Set<Brand> loadBrand(Set<String> brandIds);
	
	List<Pub> findPubs(Location location, double radius);
	
	List<Beer> getBeerSuggestions(String queryString, int limit);

	List<Brand> getBrandSuggestions(String queryString, int limit);

	Beer saveBeer(Beer beer);
	
	Pub savePub(Pub pub);
	
	Brand saveBrand(Brand brand);
}
