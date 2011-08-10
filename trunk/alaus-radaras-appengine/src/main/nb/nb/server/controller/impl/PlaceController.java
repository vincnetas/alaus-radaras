/**
 * 
 */
package nb.server.controller.impl;

import java.util.ArrayList;

import nb.server.service.BeerService;
import nb.server.service.PlaceService;
import nb.shared.model.Place;

import org.zdevra.guice.mvc.Controller;
import org.zdevra.guice.mvc.Model;
import org.zdevra.guice.mvc.ModelAndView;
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
    
    @Inject
    private BeerService beerService;
    
    @RequestMapping(path = "/(.*)", nameOfResult="place", toView="view/place.jsp")
    public Model getPlace(@UriParameter(1) String id) {
        Model model = new Model();
        Place place = getPlaceService().getCurrent(id);
        if (place != null) {
            model.addObject("place", place);
            model.addObject("beers", getBeerService().getCurrent(new ArrayList<String>(place.getBeerIds())));
        }
        
        return model;
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
