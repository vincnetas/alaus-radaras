/**
 * 
 */
package alaus.radaras.server.json;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

import alaus.radaras.server.dao.PubDao;
import alaus.radaras.server.json.model.JPlace;
import alaus.radaras.shared.model.Pub;

/**
 * @author vienozin
 *
 */
public class NbServiceImpl implements NbService {

	@Inject
	private PubDao pubDao;
	
	@Override
	public void savePlace() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] getPlaceTags(String term) {
		return new String[] {"alio", "kaip", "girdit"};
	}

	@Override
	public JPlace[] getPlaces() {
		List<Pub> allPubs = getPubDao().getAll();
		List<JPlace> result = new ArrayList<JPlace>();
		
		for (Pub pub : allPubs) {
			JPlace place = new JPlace();
			place.setBeerIds(pub.getBeerIds().toArray(new String[0]));
			place.setCity(pub.getCity());
			place.setCountry(pub.getCountry());
			place.setHomepage(pub.getHomepage());
			place.setHours(pub.getHours());
			place.setIcon(null);
			place.setLatitude(pub.getLatitude());
			place.setLongitude(pub.getLongitude());
			place.setPhone(pub.getPhone());
			place.setStreetAddress(pub.getAddress());
			place.setTags(pub.getTags().toArray(new String[0]));
			place.setType(null);
			place.setTitle(pub.getTitle());
			place.setId(pub.getId());
			
			result.add(place);
		}
		
		
		return result.toArray(new JPlace[0]);
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
