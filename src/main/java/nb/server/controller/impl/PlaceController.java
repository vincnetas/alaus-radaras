/**
 * 
 */
package nb.server.controller.impl;

import java.util.ArrayList;
import java.util.SortedSet;

import nb.server.service.BeerService;
import nb.server.service.PlaceService;
import nb.shared.model.Place;

import org.zdevra.guice.mvc.ModelMap;
import org.zdevra.guice.mvc.annotations.Controller;
import org.zdevra.guice.mvc.annotations.Model;
import org.zdevra.guice.mvc.annotations.Path;
import org.zdevra.guice.mvc.annotations.UriParameter;
import org.zdevra.guice.mvc.annotations.View;

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
    
    @Path("/(\\d+)")
    @View("view/place.jsp")
    public ModelMap getPlace(@UriParameter(1) String id) {
        ModelMap model = new ModelMap();
        
        Place place = getPlaceService().getCurrent(id);
        if (place != null) {
            model.addObject("place", place);
            model.addObject("beers", getBeerService().getCurrent(new ArrayList<String>(place.getBeerIds())));
        }
        
        return model;
    }
    
    @Path("")
    @View("view/places.jsp")
    @Model("places")
    public SortedSet<SortedSet<Place>> getPlaces() {
    	return Utils.sortPlacesByCount(getPlaceService().getCurrent());
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
