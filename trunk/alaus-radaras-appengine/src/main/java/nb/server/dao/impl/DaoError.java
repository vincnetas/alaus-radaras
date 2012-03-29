/**
 * 
 */
package nb.server.dao.impl;

/**
 * @author vincentas
 *
 */
public class DaoError extends Error {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3615897490440761426L;

	public DaoError(String message) {
		super(message);
	}
	
	public DaoError(Throwable throwable) {
		super(throwable);
	}

	public DaoError(String message, Throwable throwable) {
		super(message, throwable);
	}
}
