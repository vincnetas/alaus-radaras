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
public class ChooseModeState implements State {

	private State state;
	
	private State parent;
	
	public ChooseModeState(State parent) {
		this.parent = parent;
	}
	
	/* (non-Javadoc)
	 * @see alaus.radaras.parser.state.State#handle(org.svenson.tokenize.Token)
	 */
	@Override
	public State handle(Token token) {
		State result = this;
		
		if (token.isType(TokenType.STRING)) {
			if (token.value().equals("update")) {
				state = new UpdateState(this);
			} else if (token.value().equals("delete")) {
				state = new DeleteState(this);
			} else {
				throw new UnsupportedTokenForState(this, token);
			}
		} else if (token.isType(TokenType.COLON)) {
			/*
			 * Skip
			 */
		} else if (token.isType(TokenType.BRACE_OPEN)) {
			 result = state;
		} else if (token.isType(TokenType.COMMA)) {
			/*
			 * Skip
			 */
		} else if (token.isType(TokenType.BRACE_CLOSE)) {
			result = null;
		} else {
			throw new UnsupportedTokenForState(this, token);
		}
		
		return result;
	}

}
