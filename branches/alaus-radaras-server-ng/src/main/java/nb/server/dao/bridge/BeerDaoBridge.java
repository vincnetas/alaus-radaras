/**
 * 
 */
package nb.server.dao.bridge;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nb.server.dao.BeerDao;
import nb.shared.model.Beer;
import alaus.radaras.server.dao.BaseDao;
import alaus.radaras.server.dao.PubDao;
import alaus.radaras.shared.model.Pub;

import com.google.inject.Inject;

/**
 * @author Vincentas Vienozinskis
 *
 */
public class BeerDaoBridge extends BaseDaoBridge<Beer, alaus.radaras.shared.model.Beer> implements BeerDao {

    @Inject
    private alaus.radaras.server.dao.BeerDao beerDao;
    
    @Inject
    private PubDao pubDao;
    
    @Override
    protected BaseDao<alaus.radaras.shared.model.Beer> getBaseDao() {    
        return getBeerDao();
    }

    @Override
    protected Beer convert(alaus.radaras.shared.model.Beer value) {
        Beer result = new Beer();
        
        result.setCompanyId(value.getBrandId());
        result.setIcon(value.getIcon());
        result.setId(value.getId());        
        result.setTitle(value.getTitle());
        result.setObjectId(value.getId());
        result.setPlaceIds(getBeerPubs(value.getId()));
        
        return result;
    }
    
    private Set<String> getBeerPubs(String beerId) {
    	List<Pub> pubs = getPubDao().getBeerPubs(beerId);
    	Set<String> result = new HashSet<String>();
    	
    	for (Pub pub : pubs) {
			result.add(pub.getId());
		}
    	
    	return result;
    }

    /**
     * @return the beerDao
     */
    public alaus.radaras.server.dao.BeerDao getBeerDao() {
        return beerDao;
    }

    /**
     * @param beerDao the beerDao to set
     */
    public void setBeerDao(alaus.radaras.server.dao.BeerDao beerDao) {
        this.beerDao = beerDao;
    }

	/**
	 * @return the pubDao
	 */
	public PubDao getPubDao() {
		return pubDao;
	}

	/**
	 * @param pubDao the pubDao to set
	 */
	public void setPubDao(PubDao pubDao) {
		this.pubDao = pubDao;
	}

	@Override
	public List<Beer> acBeer(String title, int max) {
		List<Beer> beers = getAll();
		List<Beer> result = new ArrayList<Beer>(max);
		
		for (Beer beer : beers) {
			if (beer.getTitle().toLowerCase().contains(title.toLowerCase())) {
				result.add(beer);
				
				if (result.size() >= max) {
					break;
				}
			}
		}
		
		return result;
	}
    
    

}
