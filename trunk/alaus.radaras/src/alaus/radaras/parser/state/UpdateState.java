/**
 * 
 */
package alaus.radaras.parser.state;

import org.svenson.tokenize.Token;
import org.svenson.tokenize.TokenType;

/**
 * @author Vincentas
 *
 */
public class UpdateState implements State {

	private State state;
	
	private State parent;
	
	public UpdateState(State parent) {
		this.parent = parent;
	}
	
	/* (non-Javadoc)
	 * @see alaus.radaras.parser.state.State#handle(org.svenson.tokenize.Token)
	 */
	@Override
	public State handle(Token token) {
		State result = this;
		
		if (token.isType(TokenType.STRING)) {
			if (token.value().equals("brands")) {
				state = UpdateBrandState.getInstance(this);
			} else if (token.value().equals("pubs")) {
				state = UpdatePubState.getInstance(this);
			} else if (token.value().equals("beers")) {
				state = UpdateBeerState.getInstance(this);
			} else {
				throw new UnsupportedTokenForState(this, token);
			}
		} else if (token.isType(TokenType.COLON)) {
			
		} else if (token.isType(TokenType.COMMA)) {
			
		} else if (token.isType(TokenType.BRACKET_OPEN)) {
			
		} else if (token.isType(TokenType.BRACKET_CLOSE)) {
			
		} else if (token.isType(TokenType.BRACE_CLOSE)) {
			result = parent;
		} else if (token.isType(TokenType.BRACE_OPEN)) {
			result = state;
		} else {
			throw new UnsupportedTokenForState(this, token);
		}
		
		return result;
	}

}
