/**
 * 
 */
package alaus.radaras.parser.state;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import alaus.radaras.service.BeerUpdate;
import alaus.radaras.shared.model.Brand;

/**
 * @author Vincentas
 *
 */
public class UpdateBrandState extends UpdateValueState<Brand> implements State {

    private static Map<String, Method> methods = new HashMap<String, Method>();
    static {
        try {
            methods.put("title", Brand.class.getMethod("setTitle", String.class));
            methods.put("icon", Brand.class.getMethod("setIcon", String.class));
            methods.put("homePage", Brand.class.getMethod("setHomePage", String.class));
            methods.put("hometown", Brand.class.getMethod("setHometown", String.class));            
            methods.put("country", Brand.class.getMethod("setCountry", String.class));
            methods.put("description", Brand.class.getMethod("setDescription", String.class));
            methods.put("id", Brand.class.getMethod("setId", String.class));          
            methods.put("tags", Brand.class.getMethod("setTags", Set.class));         
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

	public UpdateBrandState(State parent, BeerUpdate beerUpdate) {
	    super(parent, beerUpdate, new Brand(), methods);
	}

    /* (non-Javadoc)
     * @see alaus.radaras.parser.state.UpdateValueState#objectReady(java.lang.Object)
     */
    @Override
    protected void objectReady(Brand brand) {
        beerUpdate.updateCompany(brand);        
    }
}
