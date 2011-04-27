/**
 * 
 */
package alaus.radaras.parser.state;

import java.util.ArrayList;
import java.util.List;

import org.svenson.tokenize.Token;
import org.svenson.tokenize.TokenType;

/**
 * @author Vincentas
 *
 */
public class ReadValueState implements State {

	private List<Object> array = null;
	
	private UpdateValueState parent;
	
	private static ReadValueState instance  = null;
	
	public static ReadValueState getInstance(UpdateValueState parent) {
		if (instance == null) {
			instance = new ReadValueState(parent);
		}
		
		instance.parent = parent;
		instance.array = null;
		
		return instance;
	}
	
	private ReadValueState(UpdateValueState parent) {
		this.parent = parent;
	}

	/* (non-Javadoc)
	 * @see alaus.radaras.parser.state.State#handle(org.svenson.tokenize.Token)
	 */
	@Override
	public State handle(Token token) {
		State result;

		if (array != null) {
			result = this;
			if (token.isType(TokenType.STRING)) {
				array.add(token.value());
			} else if (token.isType(TokenType.COMMA)) {
			
			} else if (token.isType(TokenType.BRACKET_CLOSE)) {
				parent.setValue(array);
				result = parent;
			} else {
				throw new UnsupportedTokenForState(this, token);
			}
		} else {
			result = parent;
			if (token.isType(TokenType.STRING)) {
				parent.setValue(token.value());
			} else if (token.isType(TokenType.DECIMAL)) {
				parent.setValue(token.value());
			} else if (token.isType(TokenType.INTEGER)) {
				parent.setValue(token.value());
			} else if (token.isType(TokenType.BRACKET_OPEN)) {
				array = new ArrayList<Object>();
				result = this;
			} else {
				throw new UnsupportedTokenForState(this, token);
			}
		}
			
		return result;
	}

}
