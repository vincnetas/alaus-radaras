package nb.server.service;

import java.util.List;

import nb.shared.model.Beer;
import nb.shared.model.Place;

/**
 * 
 * @author Vincentas
 *
 */
public interface BeerService extends BaseService<Beer> {

	List<Place> getPlaces(Beer beer);
}
