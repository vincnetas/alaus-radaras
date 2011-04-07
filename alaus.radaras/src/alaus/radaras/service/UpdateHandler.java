/**
 * 
 */
package alaus.radaras.service;

import alaus.radaras.shared.model.Beer;
import alaus.radaras.shared.model.Beer;
import alaus.radaras.shared.model.Pub;

/**
 * @author Vincentas
 *
 */
public interface UpdateHandler {

	void onPubUpdate(Pub pub);
	
	void onBrandUpdate(Beer brand);
	
	void onBeerUpdate(Beer beer);
	
	void onPubDelete(Pub pub);
	
	void onBrandDelete(Beer brand);
	
	void onBeerDelete(Beer beer);
}
