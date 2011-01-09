package alaus.radaras.server;

import java.util.List;

import alaus.radaras.client.BeerService;
import alaus.radaras.server.dao.PubDao;
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

	/**
	 * 
	 */
	private static final long serialVersionUID = -5405571273602475982L;

	public List<Pub> greetServer(String input) throws IllegalArgumentException {
		return getPubDao().getAll();
	}

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
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
