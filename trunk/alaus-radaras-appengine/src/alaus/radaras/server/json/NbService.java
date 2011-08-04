/**
 * 
 */
package alaus.radaras.server.json;

import alaus.radaras.server.json.model.JPlace;

/**
 * @author vienozin
 *
 */
public interface NbService {
	
	JPlace[] getPlaces();
	
	void savePlace();
	
	String[] getPlaceTags(String term);

}
