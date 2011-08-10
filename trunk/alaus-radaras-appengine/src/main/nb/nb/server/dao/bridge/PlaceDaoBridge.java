/**
 * 
 */
package nb.server.dao.bridge;

import java.util.ArrayList;
import java.util.List;

import nb.server.dao.PlaceDao;
import nb.shared.model.Place;
import alaus.radaras.server.dao.BaseDao;
import alaus.radaras.server.dao.PubDao;
import alaus.radaras.shared.model.Pub;

import com.google.inject.Inject;

/**
 * @author vienozin
 *
 */
public class PlaceDaoBridge extends BaseDaoBridge<Place, Pub> implements PlaceDao {

	@Inject
	private PubDao pubDao;
	
	/* (non-Javadoc)
     * @see nb.server.dao.bridge.BaseDaoBridge#getBaseDao()
     */
    @Override
    protected BaseDao<Pub> getBaseDao() {
        return getPubDao();
    }

    /* (non-Javadoc)
	 * @see nb.server.dao.PlaceDao#acPlace(java.lang.String, int)
	 */
	@Override
	public List<Place> acPlace(String title, int max) {
		List<Place> places = getAll();
		List<Place> result = new ArrayList<Place>(max);
		
		for (Place place : places) {
			if (place.getTitle().toLowerCase().contains(title.toLowerCase())) {
				result.add(place);
			}
		}
		
		return result;
	}
	
	private long lastTime = 0;
	
	private List<Place> storedList;
	
	private List<Place> getAll() {
		if (System.currentTimeMillis() - lastTime > 1000 * 60) {
			storedList = convert(getPubDao().getApproved());
			lastTime = System.currentTimeMillis();
		}
		
		return storedList;
	}
	
	/* (non-Javadoc)
     * @see nb.server.dao.bridge.BaseDaoBridge#convert(alaus.radaras.shared.model.Updatable)
     */
    @Override
    protected Place convert(Pub pub) {
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
        result.setObjectId(pub.getId());
        result.setPhone(pub.getPhone());
        result.setState(null);
        result.setStreetAddress(pub.getAddress());
        result.setTags(pub.getTags());
        result.setTitle(pub.getTitle());
        result.setType(null);
        
        return result;
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
