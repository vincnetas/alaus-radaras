/**
 * 
 */
package alaus.radaras.parser.state;

import org.svenson.tokenize.Token;

import alaus.radaras.service.BeerUpdate;

/**
 * @author Vincentas
 *
 */
public class ChooseModeState implements State {

	private State state;
	
	private State parent;
	
	private State deleteState;
	
	private State updateState;
	
	public ChooseModeState(State parent, BeerUpdate beerUpdate) {
		this.parent = parent;
		
		updateState = new UpdateState(this, beerUpdate);
		deleteState = new DeleteState(this, beerUpdate);
	}
	
	/* (non-Javadoc)
	 * @see alaus.radaras.parser.state.State#handle(org.svenson.tokenize.Token)
	 */
	@Override
	public State handle(Token token) {
		State result = this;
		
        switch (token.type()) {
        case STRING: {
            if (token.value().equals("update")) {
                state = updateState;
            } else if (token.value().equals("delete")) {
                state = deleteState;
            } else {
                throw new UnsupportedTokenForState(this, token);
            }
            break;
        }
        case COLON:
        case COMMA: {
            break;
        }
        case BRACE_OPEN: {
            result = state;
            break;
        }
        case BRACE_CLOSE: {
            result = null;
            break;
        }
        default:
            throw new UnsupportedTokenForState(this, token);
        }

		return result;
	}

}
