/**
 * 
 */
package alaus.radaras.client;

import java.util.List;
import java.util.Set;

import alaus.radaras.shared.model.Beer;
import alaus.radaras.shared.model.Brand;
import alaus.radaras.shared.model.IPLocation;
import alaus.radaras.shared.model.LocationBounds;
import alaus.radaras.shared.model.Pub;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Vincentas Vienozinskis
 *
 */
public interface BeerServiceAsync {

    /**
     * 
     * @see alaus.radaras.client.BeerService#addBeer(alaus.radaras.shared.model.Beer)
     */
    void addBeer(Beer beer, AsyncCallback<Beer> callback);

    /**
     * 
     * @see alaus.radaras.client.BeerService#addBrand(alaus.radaras.shared.model.Brand)
     */
    void addBrand(Brand brand, AsyncCallback<Brand> callback);

    /**
     * 
     * @see alaus.radaras.client.BeerService#addPub(alaus.radaras.shared.model.Pub)
     */
    void addPub(Pub pub, AsyncCallback<Pub> callback);

    /**
     * 
     * @see alaus.radaras.client.BeerService#findPubs(alaus.radaras.shared.model.LocationBounds)
     */
    void findPubs(LocationBounds bounds, AsyncCallback<List<Pub>> callback);

    /**
     * 
     * @see alaus.radaras.client.BeerService#getBeerSuggestions(java.lang.String, int)
     */
    void getBeerSuggestions(String queryString, int limit, AsyncCallback<List<Beer>> callback);

    /**
     * 
     * @see alaus.radaras.client.BeerService#getBeers()
     */
    void getBeers(AsyncCallback<List<Beer>> callback);

    /**
     * 
     * @see alaus.radaras.client.BeerService#getBrandSuggestions(java.lang.String, int)
     */
    void getBrandSuggestions(String queryString, int limit, AsyncCallback<List<Brand>> callback);

    /**
     * 
     * @see alaus.radaras.client.BeerService#getBrands()
     */
    void getBrands(AsyncCallback<List<Brand>> callback);

    /**
     * 
     * @see alaus.radaras.client.BeerService#getMyLocation()
     */
    void getMyLocation(AsyncCallback<IPLocation> callback);

    /**
     * 
     * @see alaus.radaras.client.BeerService#loadBeer(java.util.Set)
     */
    void loadBeer(Set<String> beerIds, AsyncCallback<Set<Beer>> callback);

    /**
     * 
     * @see alaus.radaras.client.BeerService#loadBrand(java.util.Set)
     */
    void loadBrand(Set<String> brandIds, AsyncCallback<Set<Brand>> callback);

    /**
     * 
     * @see alaus.radaras.client.BeerService#loadPub(java.util.Set)
     */
    void loadPub(Set<String> pubIds, AsyncCallback<Set<Pub>> callback);

    /**
     * 
     * @see alaus.radaras.client.BeerService#saveBeer(alaus.radaras.shared.model.Beer)
     */
    void saveBeer(Beer beer, AsyncCallback<Beer> callback);

    /**
     * 
     * @see alaus.radaras.client.BeerService#saveBrand(alaus.radaras.shared.model.Brand)
     */
    void saveBrand(Brand brand, AsyncCallback<Brand> callback);

    /**
     * 
     * @see alaus.radaras.client.BeerService#savePub(alaus.radaras.shared.model.Pub)
     */
    void savePub(Pub pub, AsyncCallback<Pub> callback);

    /**
     * 
     * @see alaus.radaras.client.BeerService#updatePub(alaus.radaras.shared.model.Pub)
     */
    void updatePub(Pub pub, AsyncCallback<Pub> callback);

}
