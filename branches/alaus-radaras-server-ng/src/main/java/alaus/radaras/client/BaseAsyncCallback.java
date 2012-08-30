/**
 * 
 */
package alaus.radaras.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Vincentas Vienozinskis
 *
 */
public abstract class BaseAsyncCallback<T> implements AsyncCallback<T> {

	
	public void onFailure(Throwable caught) {
		Window.alert(caught.toString());		
	}

}
