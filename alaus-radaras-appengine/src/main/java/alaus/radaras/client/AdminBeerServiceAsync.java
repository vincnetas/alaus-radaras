/**
 * 
 */
package alaus.radaras.client;

import java.util.List;

import alaus.radaras.shared.model.Pub;
import alaus.radaras.shared.model.UpdateRecord;

import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * @author Vincentas Vienozinskis
 *
 */
public interface AdminBeerServiceAsync {

	void getPubUpdates(AsyncCallback<List<UpdateRecord<Pub>>> callback);

	void applyUpdate(String id, AsyncCallback<Pub> callback);

	void rejectUpdate(String id, AsyncCallback<Pub> callback);
}
