/**
 * 
 */
package alaus.radaras.server.dao.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;

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
    	List<Pub> result = new ArrayList<Pub>();
    	
    	for (int i = 0, n = RandomUtils.nextInt(30) + 5; i < n; i++) {
    		result.add(getPub(bounds));    		
    	}
    
    	return result;
//		return getPubDao().getApproved();
    }
    	
	private static Pub getPub(LocationBounds bounds) {
		Pub pub = new Pub();

		pub.setAddress(RandomStringUtils.randomAlphabetic(RandomUtils.nextInt(20) + 8));
		pub.setBeerIds(getBeerIds());
		pub.setCity(getCity());
		pub.setCountry("Country " + RandomStringUtils.randomAlphabetic(RandomUtils.nextInt(10) + 8));
		pub.setDescription(RandomStringUtils.randomAlphabetic(RandomUtils.nextInt(80) + 20));
		pub.setHomepage("http://www.delfi.lt");
		pub.setId(Long.toString(System.currentTimeMillis()));
		pub.setLatitude(getDouble(bounds.getSouthWest().getLatitude(), bounds.getNorthEast().getLatitude()));
		pub.setLongitude(getDouble(bounds.getSouthWest().getLongitude(), bounds.getNorthEast().getLongitude()));
		pub.setPhone("+34552242342");
		pub.setTags(getPubTags());
		pub.setTitle("Title " + RandomStringUtils.randomAlphabetic(RandomUtils.nextInt(20) + 8));

		return pub;
	}
	
	private static Set<String> getBeerIds() {
		Set<String> result = new HashSet<String>();
		
		for (int i = 0, n = RandomUtils.nextInt(8) + 3; i < n; i++) {
			result.add(Integer.toString(RandomUtils.nextInt()));
		}
		
		return result;
	}
	
	private static double getDouble(double min, double max) {
		return RandomUtils.nextDouble() * (max - min) + min;
	}
	
	private static Set<String> getPubTags() {
		String[] tags = new String[] {"Kavinë", "Restoranas", "Pubas", "Krautuvë", "Rûsys", "Bravoras"};		
		Set<String> result = new HashSet<String>();
		
		for (int i = 0, n = RandomUtils.nextInt(2) + 1; i < n; i++) {
			result.add(tags[RandomUtils.nextInt(tags.length)]);
		}
		
		return result;
	}
	
	private static String getCity() {
		String[] city = new String [] {"Vilnius", "Kaunas", "Klaipeda"};
		return city[RandomUtils.nextInt(city.length)];
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
