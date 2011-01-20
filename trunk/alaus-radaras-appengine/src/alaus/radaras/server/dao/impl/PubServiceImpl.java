/**
 * 
 */
package alaus.radaras.server.dao.impl;

import java.util.List;

import com.google.inject.Inject;

import alaus.radaras.server.dao.BaseDao;
import alaus.radaras.server.dao.PubDao;
import alaus.radaras.server.dao.PubService;
import alaus.radaras.shared.model.Location;
import alaus.radaras.shared.model.Pub;

/**
 * @author Vincentas
 *
 */
public class PubServiceImpl extends BaseServiceImpl<Pub> implements PubService {

	@Inject
	private PubDao pubDao;
	
	@Override
	protected void applyUpdate(Pub pub, Pub update) {
		pub.setAddress(defaultIfNull(update.getAddress(), pub.getAddress()));
		pub.setBeerIds(defaultIfNull(update.getBeerIds(), pub.getBeerIds()));
		pub.setCity(defaultIfNull(update.getCity(), pub.getCity()));
		pub.setCountry(defaultIfNull(update.getCountry(), pub.getCountry()));
		pub.setDescription(defaultIfNull(update.getDescription(), pub.getDescription()));
		pub.setHomepage(defaultIfNull(update.getHomepage(), pub.getHomepage()));
		pub.setHours(defaultIfNull(update.getHours(), pub.getHours()));
		pub.setLatitude(defaultIfNull(update.getLatitude(), pub.getLatitude()));
		pub.setLongitude(defaultIfNull(update.getLongitude(), pub.getLongitude()));
		pub.setPhone(defaultIfNull(update.getPhone(), pub.getPhone()));
		pub.setTags(defaultIfNull(update.getTags(), pub.getTags()));
		pub.setTitle(defaultIfNull(update.getTitle(), pub.getTitle()));
	}

	@Override
	public List<Pub> findPubs(Location location, double radius) {
		return getPubDao().getApproved();
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

	@Override
	public BaseDao<Pub> getBaseDao() {
		return getPubDao();
	}
}
