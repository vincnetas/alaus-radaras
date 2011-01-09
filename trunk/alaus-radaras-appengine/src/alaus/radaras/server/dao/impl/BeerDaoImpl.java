/**
 * 
 */
package alaus.radaras.server.dao.impl;

import alaus.radaras.server.dao.BeerDao;
import alaus.radaras.shared.model.Beer;

/**
 * @author Vincentas
 *
 */
public class BeerDaoImpl extends BaseDaoImpl<Beer> implements BeerDao {

	@Override
	public Class<Beer> getClazz() {
		return Beer.class;
	}

}
