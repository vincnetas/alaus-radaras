package alaus.radaras.client;

import java.util.List;
import java.util.Set;

import alaus.radaras.shared.model.Beer;
import alaus.radaras.shared.model.Brand;
import alaus.radaras.shared.model.IPLocation;
import alaus.radaras.shared.model.LocationBounds;
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

	List<Pub> findPubs(LocationBounds bounds);

	List<Beer> getBeerSuggestions(String queryString, int limit);

	List<Brand> getBrandSuggestions(String queryString, int limit);

	IPLocation getMyLocation();



	List<Brand> getBrands();
	
	List<Beer> getBeers();

	Beer addBeer(Beer beer);
	
	Beer saveBeer(Beer beer);

	Brand addBrand(Brand brand);
	
	Brand saveBrand(Brand brand);
		
	Pub savePub(Pub pub);
	
	
	
	/**
	 * This method adds update for pub indicated by parentId. Passed in object
	 * values will be compared with current base object values and all but
	 * changed values will be set to null and stored
	 * 
	 * @param pub Pub update information
	 * @return Returns stored update information.
	 */
	Pub updatePub(Pub pub);
	
	/**
	 * This method adds pub to system. Pub will be added as unmoderated object
	 * and will not appear in results. If adding user has rights to approve,
	 * object will be automatically approved. If added pub was approved can be
	 * determined by returned object.
	 * 
	 * @param pub
	 *            Pub to add
	 * @return Returns added pub
	 */
	Pub addPub(Pub pub);
	
}
