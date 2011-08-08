package alaus.radaras.client;

import java.util.List;

import alaus.radaras.shared.model.Pub;
import alaus.radaras.shared.model.UpdateRecord;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("adminBeerService")
public interface AdminBeerService extends RemoteService {
	
	List<UpdateRecord<Pub>> getPubUpdates();
	
	Pub applyUpdate(String id);
	
	Pub rejectUpdate(String id);
}
