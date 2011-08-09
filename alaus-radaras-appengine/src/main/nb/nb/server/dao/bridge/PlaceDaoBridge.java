/**
 * 
 */
package nb.server.dao.bridge;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nb.server.dao.PlaceDao;
import nb.shared.model.BaseObject.State;
import nb.shared.model.Place;
import alaus.radaras.server.dao.PubDao;
import alaus.radaras.shared.model.Pub;

import com.google.inject.Inject;

/**
 * @author vienozin
 *
 */
public class PlaceDaoBridge implements PlaceDao {

	@Inject
	private PubDao pubDao;
	 
	/* (non-Javadoc)
	 * @see nb.server.dao.PlaceDao#acPlace(java.lang.String, int)
	 */
	@Override
	public List<Place> acPlace(String title, int max) {
		List<Place> places = convert(getPubDao().getApproved());
		List<Place> result = new ArrayList<Place>(max);
		
		for (Place place : places) {
			if (place.getTitle().toLowerCase().contains(title.toLowerCase())) {
				result.add(place);
			}
		}
		
		return result;
	}
	
	private List<Place> convert(List<Pub> list) {
		List<Place> result = new ArrayList<Place>(list.size());
		
		for (Pub pub : list) {
			result.add(convert(pub));
		}
		
		return result;
	}
	
	private Place convert(Pub pub) {
		Place result = new Place();
		
		result.setBeerIds(pub.getBeerIds());
		result.setCity(pub.getCity());
		result.setCountry(pub.getCountry());
		result.setHomepage(pub.getHomepage());
		result.setHours(pub.getHours());
		result.setIcon(null);
		result.setId(pub.getId());
		result.setLatitude(pub.getLatitude());
		result.setLongitude(pub.getLongitude());
		result.setObjectId(pub.getParentId());
		result.setPhone(pub.getPhone());
		result.setState(null);
		result.setStreetAddress(pub.getAddress());
		result.setTags(pub.getTags());
		result.setTitle(pub.getTitle());
		result.setType(null);
		
		return result;
	}

	/* (non-Javadoc)
	 * @see nb.server.dao.BaseDao#create(nb.shared.model.BaseObject)
	 */
	@Override
	public Place create(Place object) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nb.server.dao.BaseDao#read(java.lang.String)
	 */
	@Override
	public Place read(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nb.server.dao.BaseDao#read(java.util.List)
	 */
	@Override
	public List<Place> read(List<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nb.server.dao.BaseDao#update(nb.shared.model.BaseObject)
	 */
	@Override
	public Place update(Place object) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nb.server.dao.BaseDao#delete(java.lang.String)
	 */
	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see nb.server.dao.BaseDao#readCurrent(java.lang.String)
	 */
	@Override
	public Place readCurrent(String objectId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nb.server.dao.BaseDao#readDeleted(java.lang.String)
	 */
	@Override
	public Place readDeleted(String objectId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nb.server.dao.BaseDao#readDeleted(java.util.Date)
	 */
	@Override
	public List<Place> readDeleted(Date since) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nb.server.dao.BaseDao#readUpdated(java.util.Date)
	 */
	@Override
	public List<Place> readUpdated(Date since) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nb.server.dao.BaseDao#read(java.lang.String, nb.shared.model.BaseObject.State)
	 */
	@Override
	public List<Place> read(String objectId, State state) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nb.server.dao.BaseDao#readAll(java.lang.String)
	 */
	@Override
	public List<Place> readAll(String objectId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nb.server.dao.BaseDao#findBy(nb.shared.model.BaseObject)
	 */
	@Override
	public List<Place> findBy(Place filter) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nb.server.dao.BaseDao#getFirstInstance(java.lang.String)
	 */
	@Override
	public Place getFirstInstance(String objectId) {
		// TODO Auto-generated method stub
		return null;
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
	
	

}
