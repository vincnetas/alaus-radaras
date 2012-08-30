/**
 * 
 */
package nb.server.service.impl;

import java.util.List;

import nb.server.dao.BaseHistoryDao;
import nb.server.dao.BeerDao;
import nb.server.service.BeerService;
import nb.shared.model.Beer;
import nb.shared.model.Place;

import com.google.inject.Inject;

/**
 * @author vienozin
 * 
 */
public class BeerServiceImpl extends BaseServiceImpl<Beer> implements
		BeerService {

	@Inject
	private BeerDao beerDao;

	@Override
	public List<Place> getPlaces(Beer beer) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see nb.server.service.BeerService#getAutocomplete(java.lang.String, int)
	 */
	@Override
	public List<Beer> getAutocomplete(String query, int limit) {
		return getBeerDao().acBeer(query, limit);
	}



	@Override
	public BaseHistoryDao<Beer> getBaseDao() {
		return getBeerDao();
	}

	/**
	 * @return the beerDao
	 */
	public BeerDao getBeerDao() {
		return beerDao;
	}

	/**
	 * @param beerDao
	 *            the beerDao to set
	 */
	public void setBeerDao(BeerDao beerDao) {
		this.beerDao = beerDao;
	}

}
