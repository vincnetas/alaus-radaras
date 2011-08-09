/**
 * 
 */
package nb.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import nb.server.dao.BaseDao;
import nb.server.dao.PlaceDao;
import nb.server.service.PlaceService;
import nb.shared.model.Place;

import com.google.inject.Inject;

/**
 * @author vienozin
 *
 */
public class PlaceServiceImpl extends BaseServiceImpl<Place> implements PlaceService {

	@Inject
	private PlaceDao placeDao;
	
	/* (non-Javadoc)
	 * @see nb.server.service.impl.BaseServiceImpl#getBaseDao()
	 */
	@Override
	public BaseDao<Place> getBaseDao() {
		return getPlaceDao();
	}

	/**
	 * @return the placeDao
	 */
	public PlaceDao getPlaceDao() {
		return placeDao;
	}

	/**
	 * @param placeDao the placeDao to set
	 */
	public void setPlaceDao(PlaceDao placeDao) {
		this.placeDao = placeDao;
	}

	@Override
	public List<Place> getAutocomplete(String query, int limit) {
		return getPlaceDao().acPlace(query, limit);
	}	
}
