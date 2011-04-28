/**
 * 
 */
package alaus.radaras.parser.state;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.svenson.tokenize.Token;

import alaus.radaras.service.BeerUpdate;

/**
 * @author Vincentas
 *
 */
public abstract class UpdateValueState<T> implements UpdatableValueState {

   private State parent;
    
    private String key;
    
    private Object value;
    
    private ReadValueState readValueState;
    
    private T object; 
    
    protected BeerUpdate beerUpdate;
    
    public UpdateValueState(State parent, BeerUpdate beerUpdate, T object) {
        this.parent = parent;
        this.beerUpdate = beerUpdate;
        this.object = object;
        
        readValueState = new ReadValueState(this);
    }

    /* (non-Javadoc)
     * @see alaus.radaras.parser.state.State#handle(org.svenson.tokenize.Token)
     */
    @Override
    public State handle(Token token) {
        State result = this;
        
        switch (token.type()) {
        case STRING: {
            key = token.value().toString();
            break;
        }
        case COLON: {
            readValueState.resetArray();
            result = readValueState;
            break;
        }
        case COMMA: {
            setProperty(key, value);
            break;
        }
        case BRACE_CLOSE: {
            setProperty(key, value);
            objectReady(object);
            result = parent;
            break;
        }
        default:
            throw new UnsupportedTokenForState(this, token);
        }
        
        return result;
    }
    
    protected abstract void objectReady(T object);
    
    protected abstract Method getMethod(String key);
    
    private void setProperty(String key, Object value) {        
        Method method = getMethod(key);
        if (method != null) {
            try {
                method.invoke(object, value);
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }   

    /**
     * @param value the value to set
     */
    public void setValue(Object value) {
        this.value = value;
    }

}
