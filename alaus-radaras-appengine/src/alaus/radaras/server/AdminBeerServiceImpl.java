package alaus.radaras.server;

import java.util.ArrayList;
import java.util.List;

import alaus.radaras.client.AdminBeerService;
import alaus.radaras.server.dao.PubDao;
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

	@Inject
	private PubDao pubDao;
	
	@Override
	public List<UpdateRecord<Pub>> getPubUpdates() {
		List<UpdateRecord<Pub>> result = new ArrayList<UpdateRecord<Pub>>();
		List<Pub> updates = getPubDao().getUpdates();
		for (Pub pub : updates) {
			result.add(new UpdateRecord<Pub>(pub, getPubDao().getUpdates(pub.getId())));
		}
		
		return result;
	}

	/**
	 * @return the pubDao
	 */
	public PubDao getPubDao() {
		return pubDao;
	}

	/**
	 * @param pubDao the pubDao to set
	 */
	public void setPubDao(PubDao pubDao) {
		this.pubDao = pubDao;
	}
	
	

}
