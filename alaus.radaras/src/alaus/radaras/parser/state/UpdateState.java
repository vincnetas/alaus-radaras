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
public class UpdateState implements State {

	private State state;
	
	private State parent;
	
	private UpdateBrandState updateBrandState;
	
	private UpdatePubState updatePubState;
	
	private UpdateBeerState updateBeerState;
	
	public UpdateState(State parent, BeerUpdate beerUpdate) {
		this.parent = parent;
		
		updateBeerState = new UpdateBeerState(this, beerUpdate);
		updateBrandState = new UpdateBrandState(this, beerUpdate);
		updatePubState = new UpdatePubState(this, beerUpdate);
	}
	
	/* (non-Javadoc)
	 * @see alaus.radaras.parser.state.State#handle(org.svenson.tokenize.Token)
	 */
	@Override
	public State handle(Token token) {
		State result = this;
		
        switch (token.type()) {
        case STRING: {
            if (token.value().equals("brands")) {
                state = updateBrandState;
            } else if (token.value().equals("pubs")) {
                state = updatePubState;
            } else if (token.value().equals("beers")) {
                state = updateBeerState;
            } else {
                throw new UnsupportedTokenForState(this, token);
            }
            break;
        }
        case BRACE_OPEN: {
            result = state;
            break;
        }
        case BRACE_CLOSE: {
            result = parent;
            break;
        }
        case COLON:
        case COMMA:
        case BRACKET_OPEN:
        case BRACKET_CLOSE:
            break;
        default:
            throw new UnsupportedTokenForState(this, token);
        }
		
		return result;
	}

}
