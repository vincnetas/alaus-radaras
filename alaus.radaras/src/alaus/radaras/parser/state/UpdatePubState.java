/**
 * 
 */
package alaus.radaras.parser.state;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import alaus.radaras.service.BeerUpdate;
import alaus.radaras.shared.model.Pub;

/**
 * @author Vincentas
 *
 */
public class UpdatePubState extends UpdateValueState<Pub> implements State {

    private static Map<String, Method> methods = new HashMap<String, Method>();
    static {
        try {
            methods.put("title", Pub.class.getMethod("setTitle", String.class));
            methods.put("longitude", Pub.class.getMethod("setLongitude", Double.class));
            methods.put("latitude", Pub.class.getMethod("setLatitude", Double.class));
            methods.put("country", Pub.class.getMethod("setCountry", String.class));
            methods.put("city", Pub.class.getMethod("setCity", String.class));
            methods.put("address", Pub.class.getMethod("setAddress", String.class));
            methods.put("phone", Pub.class.getMethod("setPhone", String.class));
            methods.put("homepage", Pub.class.getMethod("setHomepage", String.class));
            methods.put("id", Pub.class.getMethod("setId", String.class));          
            methods.put("beerIds", Pub.class.getMethod("setBeerIds", Set.class));
            methods.put("tags", Pub.class.getMethod("setTags", Set.class));         
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	
	public UpdatePubState(State parent, BeerUpdate beerUpdate) {
	    super(parent, beerUpdate, new Pub());
	}

    /* (non-Javadoc)
     * @see alaus.radaras.parser.state.UpdateValueState#objectReady(java.lang.Object)
     */
    @Override
    protected void objectReady(Pub pub) {
        beerUpdate.updatePub(pub);        
    }

    /* (non-Javadoc)
     * @see alaus.radaras.parser.state.UpdateValueState#getMethod(java.lang.String)
     */
    @Override
    protected Method getMethod(String key) {
        return methods.get(key);
    }
}
