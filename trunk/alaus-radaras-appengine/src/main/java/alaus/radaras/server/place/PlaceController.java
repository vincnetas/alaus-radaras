/**
 * 
 */
package alaus.radaras.server.place;

import org.zdevra.guice.mvc.Controller;
import org.zdevra.guice.mvc.RequestMapping;
import org.zdevra.guice.mvc.RequestParameter;

import alaus.radaras.server.dao.PubDao;
import alaus.radaras.shared.model.Pub;

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
    private PubDao pubDao;
    
    @RequestMapping(path = "/books", nameOfResult="place", toView="place.jsp")
    public Pub getPlace(@RequestParameter("id") String id) {
        return getPubDao().get(id);
    }

    /**
     * @return the pubDao
     */
    public PubDao getPubDao() {
        return pubDao;
    }

    /**
     * @param pubDao the pubDao to set
     */
    public void setPubDao(PubDao pubDao) {
        this.pubDao = pubDao;
    }
    
    
}
