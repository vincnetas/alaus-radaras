/**
 * 
 */
package alaus.radaras.server.json;

import java.util.ArrayList;
import java.util.List;

import alaus.radaras.server.dao.PubDao;
import alaus.radaras.server.dao.PubService;
import alaus.radaras.server.json.model.JBeer;
import alaus.radaras.server.json.model.JCompany;
import alaus.radaras.server.json.model.JPlace;
import alaus.radaras.shared.model.Pub;

import com.google.inject.Inject;

/**
 * @author vienozin
 *
 */
public class NbServiceImpl implements NbService {

	@Inject
	private PubDao pubDao;
	
	@Inject
	private PubService pubService;
 
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

    @Override
    public JPlace savePlace(JPlace place) {
        // TODO Auto-generated method stub
        return null;
    }
    
    private Pub convert(JPlace place) {
        
    }

    @Override
    public JPlace[] getPlaceSuggestions(String placeId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public JBeer[] getBeers() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public JBeer saveBeer(JBeer beer) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public JBeer[] getBeerSuggestions(String beerId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public JCompany[] getCompanies() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public JCompany saveCompany(JCompany company) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void approve(String id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void reject(String id) {
        // TODO Auto-generated method stub
        
    }

    /**
     * @return the pubService
     */
    public PubService getPubService() {
        return pubService;
    }

    /**
     * @param pubService the pubService to set
     */
    public void setPubService(PubService pubService) {
        this.pubService = pubService;
    }
    
    

}
