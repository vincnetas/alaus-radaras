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

	/**
	 * Suggest places for auto complete.
	 * 
	 * @param title
	 *            Part of place title
	 * @param max
	 *            Maximum suggestions
	 * @return Returns suggested places.
	 */
	JPlace[] acPlace(String title, int max);
	
	/**
	 * Suggest beer for auto complete.
	 * 
	 * @param title
	 *            Part of beer title
	 * @param max
	 *            Maximum suggestions
	 * @return Returns suggested beers.
	 */
	JBeer[] acBeer(String title, int max);

	JPlace getPlace(String id);

	JPlace savePlace(JPlace place);

	JPlace[] getPlaceSuggestions(String placeId);

	JBeer[] getBeer(String[] ids);

	JBeer saveBeer(JBeer beer);

	JBeer[] getBeerSuggestions(String beerId);

	JCompany[] getCompanies();

	JCompany saveCompany(JCompany company);

	void approve(String id);

	void reject(String id);

}
