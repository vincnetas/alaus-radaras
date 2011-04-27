/**
 * 
 */
package alaus.radaras.parser.state;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.svenson.tokenize.Token;
import org.svenson.tokenize.TokenType;

import alaus.radaras.shared.model.Beer;

/**
 * @author Vincentas
 *
 */
public class UpdateBeerState implements State, UpdateValueState {

	private State parent;
	
	private String key;
	
	private Object value;
	
	private Beer beer = new Beer();
	
	private UpdateBeerState(State parent) {
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
			try {
				BeanUtils.setProperty(beer, key, value);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (token.isType(TokenType.BRACE_CLOSE)) {
			try {
				BeanUtils.setProperty(beer, key, value);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

	private static UpdateBeerState instance = null;
	
	public static State getInstance(State parent) {
		if (instance == null) {
			instance = new UpdateBeerState(parent);
		}
		
		instance.parent = parent;
		return instance;
	}

}
