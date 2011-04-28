/**
 * 
 */
package alaus.radaras.parser.state;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import alaus.radaras.service.BeerUpdate;
import alaus.radaras.shared.model.Beer;

/**
 * @author Vincentas
 *
 */
public class UpdateBeerState extends UpdateValueState<Beer> implements State {
	
    private static Map<String, Method> methods = new HashMap<String, Method>();
    static {
        try {
            methods.put("title", Beer.class.getMethod("setTitle", String.class));
            methods.put("brandId", Beer.class.getMethod("setBrandId", String.class));
            methods.put("icon", Beer.class.getMethod("setIcon", String.class));            
            methods.put("description", Beer.class.getMethod("setDescription", String.class));
            methods.put("id", Beer.class.getMethod("setId", String.class));          
            methods.put("tags", Beer.class.getMethod("setTags", Set.class));         
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public UpdateBeerState(State parent, BeerUpdate beerUpdate) {
        super(parent, beerUpdate, new Beer());
    }
    
    /* (non-Javadoc)
     * @see alaus.radaras.parser.state.UpdateValueState#objectReady(java.lang.Object)
     */
    @Override
    protected void objectReady(Beer beer) {
        beerUpdate.updateBrand(beer);        
    }

    /* (non-Javadoc)
     * @see alaus.radaras.parser.state.UpdateValueState#getMethog(java.lang.String)
     */
    @Override
    protected Method getMethod(String key) {    
        return methods.get(key);
    }
}
