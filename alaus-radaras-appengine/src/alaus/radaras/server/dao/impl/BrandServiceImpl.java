/**
 * 
 */
package alaus.radaras.server.dao.impl;

import alaus.radaras.server.dao.BaseDao;
import alaus.radaras.server.dao.BrandDao;
import alaus.radaras.server.dao.BrandService;
import alaus.radaras.shared.model.Brand;

import com.google.inject.Inject;

/**
 * @author Vincentas
 *
 */
public class BrandServiceImpl extends BaseServiceImpl<Brand> implements BrandService {

	@Inject
	private BrandDao brandDao;
	
	@Override
	protected void applyUpdate(Brand brand, Brand update) {
		brand.setTitle(defaultIfNull(update.getTitle(), brand.getTitle()));
		brand.setIcon(defaultIfNull(update.getIcon(), brand.getIcon()));
		brand.setHomePage(defaultIfNull(update.getHomePage(), brand.getHomePage()));
		brand.setCountry(defaultIfNull(update.getCountry(), brand.getCountry()));
		brand.setHomeTown(defaultIfNull(update.getHomeTown(), brand.getHomeTown()));
		brand.setDescription(defaultIfNull(update.getDescription(), brand.getDescription()));
		if (!update.getTags().isEmpty()) {
			brand.setTags(update.getTags());
		}
	}
	
	/* (non-Javadoc)
     * @see alaus.radaras.server.dao.impl.BaseServiceImpl#prepareUpdate(alaus.radaras.shared.model.Updatable, alaus.radaras.shared.model.Updatable)
     */
    @Override
    protected void prepareUpdate(Brand update, Brand brand) {
    	update.setTitle(nullIfEqual(update.getTitle(), brand.getTitle()));
    	update.setIcon(nullIfEqual(update.getIcon(), brand.getIcon()));
    	update.setHomePage(nullIfEqual(update.getHomePage(), brand.getHomePage()));
    	update.setCountry(nullIfEqual(update.getCountry(), brand.getCountry()));
    	update.setHomeTown(nullIfEqual(update.getHomeTown(), brand.getHomeTown()));
    	update.setDescription(nullIfEqual(update.getDescription(), brand.getDescription()));
    	update.setTags(nullIfEqual(update.getTags(), brand.getTags()));
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

	@Override
	public BaseDao<Brand> getBaseDao() {
		return getBrandDao();
	}
}
