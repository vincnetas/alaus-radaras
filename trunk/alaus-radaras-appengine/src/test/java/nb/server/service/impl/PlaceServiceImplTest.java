/**
 * 
 */
package nb.server.service.impl;

import nb.server.service.BaseService;
import nb.server.service.PlaceService;
import nb.shared.model.Place;


/**
 * @author vienozin
 *
 */
public class PlaceServiceImplTest extends BaseServiceImplTest<Place> {

	/* (non-Javadoc)
	 * @see nb.server.service.impl.BaseServiceImplTest#getBaseService()
	 */
	@Override
	public BaseService<Place> getBaseService() {
		return getGuice().getInstance(PlaceService.class);
		
	}

	@Override
	public Place getSample() {
		Place result = new Place();
		
		return result;
	}

	
}
