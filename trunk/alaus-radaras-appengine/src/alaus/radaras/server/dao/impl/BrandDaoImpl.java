/**
 * 
 */
package alaus.radaras.server.dao.impl;

import alaus.radaras.server.dao.BrandDao;
import alaus.radaras.shared.model.Brand;

/**
 * @author Vincentas
 *
 */
public class BrandDaoImpl extends BaseDaoImpl<Brand> implements BrandDao {

	@Override
	public Class<Brand> getClazz() {
		return Brand.class;
	}

}
