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

    /**
     * 
     * @see alaus.radaras.client.AdminBeerService#applyUpdate(java.lang.String)
     */
    void applyUpdate(String id, AsyncCallback<Pub> callback);

    /**
     * 
     * @see alaus.radaras.client.AdminBeerService#getPubUpdates()
     */
    void getPubUpdates(AsyncCallback<List<UpdateRecord<Pub>>> callback);

    /**
     * 
     * @see alaus.radaras.client.AdminBeerService#rejectUpdate(java.lang.String)
     */
    void rejectUpdate(String id, AsyncCallback<Pub> callback);

}
