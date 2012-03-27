/**
 * 
 */
package nb.server.dao.impl;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import com.google.inject.Inject;

/**
 * @author vienozin
 *
 */
public class DataStoreTemplate {
	
	@Inject
	private PersistenceManagerFactory pmf;

	interface PersistenceManagerCallback<T> {
		
		T callback(PersistenceManager pm);
	}
	
	interface PersistenceManagerListCallback<T> {
		
		List<T> callback(PersistenceManager pm);
	}

	public <T> List<T> execute(PersistenceManagerListCallback<T> pmc) {
		PersistenceManager pm = pmf.getPersistenceManager();
		try {
			 List<T> result = pmc.callback(pm);
			 if (result != null) {
				 result = (List<T>) pm.detachCopyAll(result);
			 }
			 
			 return result;
		} finally {
			pm.close();
		}
	}
	
	public <T> T execute(PersistenceManagerCallback<T> pmc) {
		PersistenceManager pm = pmf.getPersistenceManager();
		try {
			 T result = pmc.callback(pm);
			 if (result != null) {
				 result = pm.detachCopy(result);
			 }
			 
			 return result;
		} finally {
			pm.close();
		}
	}

	/**
	 * @return the pmf
	 */
	public PersistenceManagerFactory getPmf() {
		return pmf;
	}

	/**
	 * @param pmf the pmf to set
	 */
	public void setPmf(PersistenceManagerFactory pmf) {
		this.pmf = pmf;
	}
}
