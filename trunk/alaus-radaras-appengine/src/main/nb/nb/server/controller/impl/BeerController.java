/**
 * 
 */
package nb.server.controller.impl;

import java.util.ArrayList;
import java.util.List;

import nb.server.service.BeerService;
import nb.server.service.CompanyService;
import nb.server.service.PlaceService;
import nb.shared.model.Beer;

import org.zdevra.guice.mvc.Controller;
import org.zdevra.guice.mvc.Model;
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
public class BeerController {

    @Inject
    private PlaceService placeService;
    
    @Inject
    private BeerService beerService;
    
    @Inject
    private CompanyService companyService;
    
    @RequestMapping(path = "/(\\d+)", toView="view/beer.jsp")
    public Model getBeer(@UriParameter(1) String id) {
        Model model = new Model();
        
        Beer beer = getBeerService().getCurrent(id);
        if (beer != null) {        
        	model.addObject("beer", beer);
        	model.addObject("company", getCompanyService().getCurrent(beer.getCompanyId()));
        	model.addObject("places", getPlaceService().getCurrent(new ArrayList<String>(beer.getPlaceIds())));
        }        
        
        return model;
    }
    
    @RequestMapping(path = "", toView="view/beers.jsp")
    public Model getBeers() {
        Model model = new Model();
        
        List<Beer> beers = getBeerService().getCurrent();
        model.addObject("beers", beers);
        
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
