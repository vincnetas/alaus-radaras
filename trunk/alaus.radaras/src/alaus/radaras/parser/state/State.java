package alaus.radaras.parser.state;

import org.svenson.tokenize.Token;

public interface State {

	State handle(Token token);
}
