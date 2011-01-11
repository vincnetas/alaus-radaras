package alaus.radaras.server;

import java.util.ArrayList;
import java.util.List;

import alaus.radaras.client.BeerService;
import alaus.radaras.server.dao.BeerDao;
import alaus.radaras.server.dao.PubDao;
import alaus.radaras.shared.model.Beer;
import alaus.radaras.shared.model.Location;
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
	@Override
	public List<Pub> findPubs(Location location, double radius) {
		return getPubDao().getAll();
	}

	@Override
	public void savePub(Pub pub) {
		getPubDao().save(pub);
	}

	@Override
	public List<Beer> getBeerSuggestions(String queryString, int limit) {
		List<Beer> beers = new ArrayList<Beer>();
		
		Beer beer; 

		beer = new Beer();
		beer.setTitle("Alus vienas " + limit);
		beers.add(beer);
		
		beer = new Beer();
		beer.setTitle("Alus du");
		beers.add(beer);
		
		beer = new Beer();
		beer.setTitle("Alus trys");
		beers.add(beer);
		
		return beers;
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
	
	
	
	
}
