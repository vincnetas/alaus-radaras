package alaus.radaras.server;

import java.util.List;

import alaus.radaras.client.AdminBeerService;
import alaus.radaras.server.dao.PubService;
import alaus.radaras.shared.model.Pub;
import alaus.radaras.shared.model.UpdateRecord;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The server side implementation of the RPC service.
 */
@Singleton
public class AdminBeerServiceImpl extends RemoteServiceServlet implements AdminBeerService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2591649769173838411L;

	@Inject
	private PubService pubService;
	
	
	public List<UpdateRecord<Pub>> getPubUpdates() {
		return getPubService().getUpdates();
	}

	
	public Pub applyUpdate(String id) {
		return getPubService().applyUpdate(id);
	}

	
	public Pub rejectUpdate(String id) {
		return getPubService().rejectUpdate(id);
	}

	/**
	 * @return the pubService
	 */
	public PubService getPubService() {
		return pubService;
	}

	/**
	 * @param pubService the pubService to set
	 */
	public void setPubService(PubService pubService) {
		this.pubService = pubService;
	}
	
	
	
	

}
