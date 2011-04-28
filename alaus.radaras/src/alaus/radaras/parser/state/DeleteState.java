/**
 * 
 */
package alaus.radaras.parser.state;

import java.util.Collection;

import org.svenson.tokenize.Token;

import alaus.radaras.service.BeerUpdate;

/**
 * @author Vincentas
 *
 */
public class DeleteState implements State, UpdatableValueState {

	private State parent;
	
	private String key;
	
	private Object value;
	
	private ReadValueState readValueState;
	
	private BeerUpdate beerUpdate;	
	
	public DeleteState(State parent, BeerUpdate beerUpdate) {
		this.parent = parent;
		this.beerUpdate = beerUpdate;
		
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
            break;
        }
        case BRACE_CLOSE: {
            performDelete(key, value);
            result = parent;
            break;
        }
        default:
            throw new UnsupportedTokenForState(this, token);
        }
		
		return result;
	}
	
	private void performDelete(String key, Object value) {
	    @SuppressWarnings("unchecked")
        Collection<String> ids = (Collection<String>) value;
	    
	    if (key.equals("pubs")) {
            for (String id : ids) {
                beerUpdate.deletePub(id);
            }
	    } else if (key.equals("brands")) {
            for (String id : ids) {
                beerUpdate.deleteCompany(id);
            }
	    } else if (key.equals("beers")) {
           for (String id : ids) {
                beerUpdate.deleteBrand(id);
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
