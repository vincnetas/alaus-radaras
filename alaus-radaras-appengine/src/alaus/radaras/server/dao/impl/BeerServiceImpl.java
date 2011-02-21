/**
 * 
 */
package alaus.radaras.server.dao.impl;

import alaus.radaras.server.dao.BaseDao;
import alaus.radaras.server.dao.BeerDao;
import alaus.radaras.server.dao.BeerService;
import alaus.radaras.shared.model.Beer;

import com.google.inject.Inject;

/**
 * @author Vincentas
 *
 */
public class BeerServiceImpl extends BaseServiceImpl<Beer> implements BeerService {

	@Inject
	private BeerDao beerDao;
	
	@Override
	protected void applyUpdate(Beer beer, Beer update) {
		beer.setTitle(defaultIfNull(update.getTitle(), beer.getTitle()));
		beer.setBrandId(defaultIfNull(update.getBrandId(), beer.getBrandId()));
		beer.setDescription(defaultIfNull(update.getDescription(), beer.getDescription()));
		beer.setTags(defaultIfNull(update.getTags(), beer.getTags()));
	}
	
	/* (non-Javadoc)
     * @see alaus.radaras.server.dao.impl.BaseServiceImpl#prepareUpdate(alaus.radaras.shared.model.Updatable, alaus.radaras.shared.model.Updatable)
     */
    @Override
    protected void prepareUpdate(Beer update, Beer beer) {
    	update.setTitle(nullIfEqual(update.getTitle(), beer.getTitle()));
    	update.setBrandId(nullIfEqual(update.getBrandId(), beer.getBrandId()));
    	update.setDescription(nullIfEqual(update.getDescription(), beer.getDescription()));
    	update.setTags(nullIfEqual(update.getTags(), beer.getTags()));
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

	@Override
	public BaseDao<Beer> getBaseDao() {
		return getBeerDao();
	}
}
