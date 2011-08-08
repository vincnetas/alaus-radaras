/**
 * 
 */
package alaus.radaras.server.dao.impl;

import java.util.List;

import alaus.radaras.server.dao.BaseDao;
import alaus.radaras.server.dao.PubDao;
import alaus.radaras.server.dao.PubService;
import alaus.radaras.shared.model.LocationBounds;
import alaus.radaras.shared.model.Pub;

import com.google.inject.Inject;

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
		
		if (!update.getBeerIds().isEmpty()) {
			pub.setBeerIds(update.getBeerIds());
		}
		
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
	
	/* (non-Javadoc)
     * @see alaus.radaras.server.dao.impl.BaseServiceImpl#prepareUpdate(alaus.radaras.shared.model.Updatable, alaus.radaras.shared.model.Updatable)
     */
    @Override
    protected void prepareUpdate(Pub update, Pub pub) {
        update.setAddress(nullIfEqual(update.getAddress(), pub.getAddress()));
        update.setBeerIds(nullIfEqual(update.getBeerIds(), pub.getBeerIds()));
        update.setCity(nullIfEqual(update.getCity(), pub.getCity()));
        update.setCountry(nullIfEqual(update.getCountry(), pub.getCountry()));
        update.setDescription(nullIfEqual(update.getDescription(), pub.getDescription()));
        update.setHomepage(nullIfEqual(update.getHomepage(), pub.getHomepage()));
        update.setHours(nullIfEqual(update.getHours(), pub.getHours()));
        update.setLatitude(nullIfEqual(update.getLatitude(), pub.getLatitude()));
        update.setLongitude(nullIfEqual(update.getLongitude(), pub.getLongitude()));
        update.setPhone(nullIfEqual(update.getPhone(), pub.getPhone()));
        update.setTags(nullIfEqual(update.getTags(), pub.getTags()));
        update.setTitle(nullIfEqual(update.getTitle(), pub.getTitle()));
    }

    @Override
	public List<Pub> findPubs(LocationBounds bounds) {
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
