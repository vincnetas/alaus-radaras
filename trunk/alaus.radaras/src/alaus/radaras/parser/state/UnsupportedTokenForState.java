/**
 * 
 */
package alaus.radaras.parser.state;

import org.svenson.tokenize.Token;

/**
 * @author Vincentas
 *
 */
public class UnsupportedTokenForState extends RuntimeException {

	private State state;
	
	private Token token;
	
	public UnsupportedTokenForState(State state, Token token) {
		this.state = state;
		this.token = token;
	}
}
