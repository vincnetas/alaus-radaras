/**
 * 
 */
package nb.server.json;

import java.util.List;

import nb.server.json.model.JBeer;
import nb.server.json.model.JCompany;
import nb.server.json.model.JPlace;
import nb.server.service.BeerService;
import nb.server.service.PlaceService;
import nb.shared.model.Beer;
import nb.shared.model.Place;
import alaus.radaras.shared.model.Pub;

import com.google.inject.Inject;

/**
 * @author vienozin
 *
 */
public class NbServiceImpl implements NbService {

	@Inject
	private PlaceService placeService;
	
	@Inject
	private BeerService beerService;
	
	/* (non-Javadoc)
	 * @see nb.server.json.NbService#acPlace(java.lang.String, int)
	 */
	@Override
	public JPlace[] acPlace(String title, int max) {
		return convertPlace(getPlaceService().getAutocomplete(title, max));
	}
	
	/* (non-Javadoc)
	 * @see nb.server.json.NbService#acBeer(java.lang.String, int)
	 */
	@Override
	public JBeer[] acBeer(String title, int max) {
		return convertBeer(getBeerService().getAutocomplete(title, max));
	}

	private JBeer[] convertBeer(List<Beer> list) {
		JBeer[] result = new JBeer[list.size()];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = convert(list.get(i));
		}
		
		return result;
	}

	private JPlace[] convertPlace(List<Place> list) {
		JPlace[] result = new JPlace[list.size()];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = convert(list.get(i));
		}
		
		return result;
	}
	
	private JBeer convert(Beer beer) {
		JBeer result = new JBeer();
		
		result.setAlcohol(beer.getAlcohol());
		result.setBitterness(beer.getBitterness());
		result.setColor(beer.getColor());
		result.setCompanyId(beer.getCompanyId());
		result.setGravity(beer.getGravity());
		result.setIcon(beer.getIcon());
		result.setId(beer.getId());
		result.setPlaceIds(beer.getPlaceIds().toArray(new String[0]));
		result.setTitle(beer.getTitle());
			
		return result;
	}
	
	private JPlace convert(Place place) {
		JPlace result = new JPlace();
		
		result.setBeerIds(place.getBeerIds().toArray(new String[0]));
		result.setCity(place.getCity());
		result.setCountry(place.getCountry());
		result.setHomepage(place.getHomepage());
		result.setHours(place.getHours());
		result.setIcon(place.getIcon());
		result.setId(place.getId());
		result.setLatitude(place.getLatitude());
		result.setLongitude(place.getLongitude());
		result.setPhone(place.getPhone());
		result.setStreetAddress(place.getStreetAddress());
		result.setTags(place.getTags().toArray(new String[0]));
		result.setTitle(place.getTitle());
		result.setType(place.getType());	
		
		return result;
	}	

    @Override
    public JPlace savePlace(JPlace place) {
    	
        return null;
    }
    
    private Place convert(JPlace place) {
        Place result = new Place();
        
        return result;
    }

    @Override
    public JPlace[] getPlaceSuggestions(String placeId) {
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
	 * @return the placeService
	 */
	public PlaceService getPlaceService() {
		return placeService;
	}

	/**
	 * @param placeService the placeService to set
	 */
	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}

	@Override
	public JPlace getPlace(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JBeer[] getBeer(String[] ids) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the beerService
	 */
	public BeerService getBeerService() {
		return beerService;
	}

	/**
	 * @param beerService the beerService to set
	 */
	public void setBeerService(BeerService beerService) {
		this.beerService = beerService;
	}
	
	
    

    
}
