package alaus.radaras.parser.state;

import org.svenson.tokenize.Token;

import alaus.radaras.service.BeerUpdate;

public class StartState implements State {

    private ChooseModeState chooseModeState;
    
    public StartState(BeerUpdate beerUpdate) {
        chooseModeState = new ChooseModeState(this, beerUpdate);
    }
    
	@Override
	public State handle(Token token) {
		State result;
		
        switch (token.type()) {
        case BRACE_OPEN: {
            result = chooseModeState;
            break;
        }
        case END: {
            result = null;
            break;
        }
        default:
            throw new UnsupportedTokenForState(this, token);
        }
		
		return result;
	}

	
}
