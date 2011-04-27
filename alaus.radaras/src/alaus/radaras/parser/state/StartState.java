package alaus.radaras.parser.state;

import org.svenson.tokenize.Token;
import org.svenson.tokenize.TokenType;

public class StartState implements State {

	@Override
	public State handle(Token token) {
		State result;
		
		if (token.isType(TokenType.BRACE_OPEN)) {
			result = new ChooseModeState(this);
		} else if (token.isType(TokenType.END)) {
			return null;
		} else {
			throw new UnsupportedTokenForState(this, token);	
		}
		
		return result;
	}

	
}
