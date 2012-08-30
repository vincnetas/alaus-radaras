package nb.server.dao.impl;

import java.util.List;

import nb.server.dao.PlaceDao;
import nb.shared.model.Place;

public class PlaceDaoImpl extends BaseHistoryDaoImpl<Place> implements PlaceDao {

	@Override
	public List<Place> acPlace(String title, int max) {
		return null;
	}

}
