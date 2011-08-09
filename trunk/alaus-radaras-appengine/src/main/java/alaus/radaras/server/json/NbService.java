/**
 * 
 */
package alaus.radaras.server.json;

import alaus.radaras.server.json.model.JBeer;
import alaus.radaras.server.json.model.JCompany;
import alaus.radaras.server.json.model.JPlace;

/**
 * @author vienozin
 *
 */
public interface NbService {
	
	JPlace[] getPlaces();
	
	JPlace savePlace(JPlace place);
	
	JPlace[] getPlaceSuggestions(String placeId);
	
	
	JBeer[] getBeers();
	
	JBeer saveBeer(JBeer beer);
	
	JBeer[] getBeerSuggestions(String beerId);
	
	
	JCompany[] getCompanies();
	
	JCompany saveCompany(JCompany company);
	
	
	void approve(String id);
	
	void reject(String id);

}
