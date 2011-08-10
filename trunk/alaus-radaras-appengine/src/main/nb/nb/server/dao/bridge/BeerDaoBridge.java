/**
 * 
 */
package nb.server.dao.bridge;

import nb.server.dao.BeerDao;
import nb.shared.model.Beer;
import alaus.radaras.server.dao.BaseDao;

import com.google.inject.Inject;

/**
 * @author Vincentas Vienozinskis
 *
 */
public class BeerDaoBridge extends BaseDaoBridge<Beer, alaus.radaras.shared.model.Beer> implements BeerDao {

    @Inject
    private alaus.radaras.server.dao.BeerDao beerDao;
    
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
    
    

}
