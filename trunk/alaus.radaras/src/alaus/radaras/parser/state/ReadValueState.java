/**
 * 
 */
package alaus.radaras.parser.state;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.svenson.tokenize.Token;

/**
 * @author Vincentas
 *
 */
public class ReadValueState implements State {

    private boolean isArray = false;
    
	private Set<Object> array = new HashSet<Object>();
	
	private UpdatableValueState parent;
	
	public ReadValueState(UpdatableValueState parent) {
		this.parent = parent;
	}

	/* (non-Javadoc)
	 * @see alaus.radaras.parser.state.State#handle(org.svenson.tokenize.Token)
	 */
	@Override
	public State handle(Token token) {
		State result;

		if (isArray) {
			result = this;
			
            switch (token.type()) {
            case STRING: {
                array.add(token.value());
                break;
            }
            case COMMA:
                break;
            case BRACKET_CLOSE: {
                parent.setValue(array);
                result = parent;
                break;
            }
            default:
                throw new UnsupportedTokenForState(this, token);
            }
		} else {
			result = parent;
			
            switch (token.type()) {
            case STRING: {
                parent.setValue(token.value());
                break;
            }
            case DECIMAL: {
                parent.setValue(((BigDecimal) token.value()).doubleValue());
                break;
            }
            case INTEGER: {
                parent.setValue(token.value());
                break;
            }
            case BRACKET_OPEN: {
                isArray = true;
                result = this;
                break;
            }
            default:
                throw new UnsupportedTokenForState(this, token);
            }
		}
			
		return result;
	}
	
	public void resetArray() {
	    array.clear();
	    isArray = false;
	}

}
