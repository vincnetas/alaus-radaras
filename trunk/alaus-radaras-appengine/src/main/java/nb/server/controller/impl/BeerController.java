/**
 * 
 */
package nb.server.controller.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nb.server.service.BeerService;
import nb.server.service.CompanyService;
import nb.server.service.PlaceService;
import nb.shared.model.Beer;

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
@Controller(path="/beer/*")
public class BeerController {

    @Inject
    private PlaceService placeService;
    
    @Inject
    private BeerService beerService;
    
    @Inject
    private CompanyService companyService;
    
    @Path("/(\\d+)")
    @View("view/beer.jsp")
    public ModelMap getBeer(@UriParameter(1) String id) {
        ModelMap model = new ModelMap();
        
        Beer beer = getBeerService().getCurrent(id);
        if (beer != null) {        	
        	model.addObject("beer", beer);
        	model.addObject("company", getCompanyService().getCurrent(beer.getCompanyId()));
        	model.addObject("places", Utils.sortPlacesByCount(getPlaceService().getCurrent(new ArrayList<String>(beer.getPlaceIds()))));        	
        }        
        
        return model;
    }
        
    @Path("")
    @View("view/beers.jsp")
    @Model("beers")
    public List<Beer> getBeers() {
        List<Beer> beers =  getBeerService().getCurrent();
        Collections.sort(beers, new Comparator<Beer>() {

			@Override
			public int compare(Beer o1, Beer o2) {			
				return o1.getTitle().compareToIgnoreCase(o2.getTitle());
			}
		});
        
        return beers;
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

	/**
	 * @return the companyService
	 */
	public CompanyService getCompanyService() {
		return companyService;
	}

	/**
	 * @param companyService the companyService to set
	 */
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}
    
    
	
	
}
