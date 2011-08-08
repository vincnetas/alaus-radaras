package alaus.radaras.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import alaus.radaras.client.BeerService;
import alaus.radaras.server.dao.BeerDao;
import alaus.radaras.server.dao.BrandDao;
import alaus.radaras.server.dao.BrandService;
import alaus.radaras.server.dao.PubDao;
import alaus.radaras.server.dao.PubService;
import alaus.radaras.server.locator.IPLocator;
import alaus.radaras.shared.model.Beer;
import alaus.radaras.shared.model.Brand;
import alaus.radaras.shared.model.IPLocation;
import alaus.radaras.shared.model.LocationBounds;
import alaus.radaras.shared.model.Pub;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The server side implementation of the RPC service.
 */
@Singleton
public class BeerServiceImpl extends RemoteServiceServlet implements BeerService {

	
	@Inject
	private PubDao pubDao;
	
	@Inject
	private BeerDao beerDao;
	
	@Inject
	private BrandDao brandDao;
	
	@Inject
	private IPLocator locator;
	
	@Inject
	private PubService pubService;
	
	@Inject
	private BrandService brandService;
	
	@Inject
	private alaus.radaras.server.dao.BeerService beerService;

	/**
	 * 
	 */
	private static final long serialVersionUID = -5405571273602475982L;

	/**
	 * @return the pubDao
	 */
	public PubDao getPubDao() {
		return pubDao;
	}

	/**
	 * @param pubDao
	 *            the pubDao to set
	 */
	public void setPubDao(PubDao pubDao) {
		this.pubDao = pubDao;
	}

	/* (non-Javadoc)
	 * @see alaus.radaras.client.GreetingService#findPubs(alaus.radaras.shared.model.Location, double)
	 */
	public List<Pub> findPubs(LocationBounds bounds) {
		return getPubService().findPubs(bounds);
	}

	public List<Beer> getBeerSuggestions(String queryString, int limit) {
		String lowerCaseQuery = queryString.toLowerCase();
		List<Beer> result = new ArrayList<Beer>();
		
		for (Beer beer : getBeerDao().getAll()) {
			if (beer.getTitle().toLowerCase().startsWith(lowerCaseQuery)) {
				result.add(beer);
			}
		}		
		
		return result;
	}

	/**
	 * @return the beerDao
	 */
	public BeerDao getBeerDao() {
		return beerDao;
	}

	/**
	 * @param beerDao the beerDao to set
	 */
	public void setBeerDao(BeerDao beerDao) {
		this.beerDao = beerDao;
	}

	public List<Brand> getBrandSuggestions(String queryString, int limit) {
		String lowerCaseQuery = queryString.toLowerCase();
		List<Brand> result = new ArrayList<Brand>();
		
		for (Brand brand : getBrandDao().getAll()) {
			if (brand.getTitle().toLowerCase().startsWith(lowerCaseQuery)) {
				result.add(brand);
			}
		}		
		
		return result;
	}

	/**
	 * @return the brandDao
	 */
	public BrandDao getBrandDao() {
		return brandDao;
	}

	/**
	 * @param brandDao the brandDao to set
	 */
	public void setBrandDao(BrandDao brandDao) {
		this.brandDao = brandDao;
	}

	public Pub updatePub(Pub pub) {
		return getPubService().addUpdate(pub);
	}
	
	/* (non-Javadoc)
	 * @see alaus.radaras.client.BeerService#saveBeer(alaus.radaras.shared.model.Beer)
	 */
	public Beer saveBeer(Beer beer) {
		return getBeerService().addUpdate(beer);
	}

	public Set<Beer> loadBeer(Set<String> beerIds) {
		return getBeerDao().load(beerIds);
	}

	public Set<Pub> loadPub(Set<String> pubIds) {
		return getPubDao().load(pubIds);
	}

	public Set<Brand> loadBrand(Set<String> brandIds) {
		return getBrandDao().load(brandIds);
	}

	/* (non-Javadoc)
	 * @see alaus.radaras.client.BeerService#getMyLocation()
	 */
	public IPLocation getMyLocation() {
		HttpServletRequest request = perThreadRequest.get();
		return getLocator().locate(request.getRemoteAddr());
	}

	/**
	 * @return the locator
	 */
	public IPLocator getLocator() {
		return locator;
	}

	/**
	 * @param locator the locator to set
	 */
	public void setLocator(IPLocator locator) {
		this.locator = locator;
	}
	
	/* (non-Javadoc)
	 * @see alaus.radaras.client.BeerService#addPub(alaus.radaras.shared.model.Pub)
	 */
	public Pub addPub(Pub pub) {
		return getPubService().add(pub);
	}

	/* (non-Javadoc)
	 * @see alaus.radaras.client.BeerService#addBeer(alaus.radaras.shared.model.Beer)
	 */
	public Beer addBeer(Beer beer) {
		return getBeerService().add(beer);
	}

	/**
	 * @return the pubService
	 */
	public PubService getPubService() {
		return pubService;
	}

	/**
	 * @param pubService the pubService to set
	 */
	public void setPubService(PubService pubService) {
		this.pubService = pubService;
	}

	public Brand addBrand(Brand brand) {
		return getBrandService().add(brand);
	}

	public Brand saveBrand(Brand brand) {
		return getBrandService().addUpdate(brand);
	}

	public List<Brand> getBrands() {
		return getBrandDao().getAll();
	}

	public List<Beer> getBeers() {	
		return getBeerDao().getAll();
	}

	public Pub savePub(Pub pub) {
		return getPubService().addUpdate(pub);
	}

	/**
	 * @return the brandService
	 */
	public BrandService getBrandService() {
		return brandService;
	}

	/**
	 * @param brandService the brandService to set
	 */
	public void setBrandService(BrandService brandService) {
		this.brandService = brandService;
	}

	/**
	 * @return the beerService
	 */
	public alaus.radaras.server.dao.BeerService getBeerService() {
		return beerService;
	}

	/**
	 * @param beerService the beerService to set
	 */
	public void setBeerService(alaus.radaras.server.dao.BeerService beerService) {
		this.beerService = beerService;
	}
	
	
	
}
