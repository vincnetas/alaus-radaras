/**
 * 
 */
package nb.server.controller.impl;

import nb.server.service.PlaceService;
import nb.shared.model.Place;

import org.zdevra.guice.mvc.Controller;
import org.zdevra.guice.mvc.RequestMapping;
import org.zdevra.guice.mvc.UriParameter;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Vincentas Vienozinskis
 *
 */
@Singleton
@Controller
public class PlaceController {

    @Inject
    private PlaceService placeService;
    
    @RequestMapping(path = "/(.*)", nameOfResult="place", toView="view/place.jsp")
    public Place getPlace(@UriParameter(1) String id) {
    	return getPlaceService().getCurrent(id);
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
}
