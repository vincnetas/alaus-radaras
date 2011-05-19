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

    private static final long serialVersionUID = -4208495319694197741L;

    private State state;
	
	private Token token;
	
	public UnsupportedTokenForState(State state, Token token) {
		this.state = state;
		this.token = token;
	}

    /* (non-Javadoc)
     * @see java.lang.Throwable#toString()
     */
    @Override
    public String toString() {    
        return "State : " + state + ", token : " + token;
    }
	
	
}
