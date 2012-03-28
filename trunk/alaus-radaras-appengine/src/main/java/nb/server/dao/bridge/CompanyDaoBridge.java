/**
 * 
 */
package nb.server.dao.bridge;

import nb.server.dao.CompanyDao;
import nb.shared.model.Company;
import alaus.radaras.server.dao.BaseDao;
import alaus.radaras.server.dao.BrandDao;
import alaus.radaras.shared.model.Brand;

import com.google.inject.Inject;

/**
 * @author Vincentas Vienozinskis
 *
 */
public class CompanyDaoBridge extends BaseDaoBridge<Company, Brand> implements CompanyDao {

    @Inject
    private BrandDao brandDao;
    
    @Override
    protected BaseDao<Brand> getBaseDao() {    
        return getBrandDao();
    }

    @Override
    protected Company convert(Brand value) {
        Company result = new Company();

        result.setCountry(value.getCountry());
        result.setHomepage(value.getHomePage());
        result.setHometown(value.getHometown());
        result.setIcon(value.getIcon());
        result.setId(value.getId());
        result.setObjectId(value.getId());
        result.setTitle(value.getTitle());
        
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

    
    

}
