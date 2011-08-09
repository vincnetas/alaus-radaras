/**
 * 
 */
package nb.server.json;

import nb.server.json.model.JBeer;
import nb.server.json.model.JCompany;
import nb.server.json.model.JPlace;

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
