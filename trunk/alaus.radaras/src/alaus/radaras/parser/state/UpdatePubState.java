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
public class UpdatePubState implements State, UpdateValueState {

	private State parent;
	
	private String key;
	
	private Object value;
	
	private UpdatePubState(State parent) {
		this.parent = parent;
	}

	/* (non-Javadoc)
	 * @see alaus.radaras.parser.state.State#handle(org.svenson.tokenize.Token)
	 */
	@Override
	public State handle(Token token) {
		State result = this;
		
		if (token.isType(TokenType.STRING)) {
			key = token.value().toString();
		} else if (token.isType(TokenType.COLON)) {
			result = ReadValueState.getInstance(this);
		} else if (token.isType(TokenType.COMMA)) {

		} else if (token.isType(TokenType.BRACE_CLOSE)) {
			result = parent;
		} else {
			throw new UnsupportedTokenForState(this, token);
		}
		
		return result;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	
	private static UpdatePubState instance = null;

	public static UpdatePubState getInstance(State parent) {
		if (instance == null) {
			instance = new UpdatePubState(parent);
		}
		
		instance.parent = parent;
		return instance;
	}
}
