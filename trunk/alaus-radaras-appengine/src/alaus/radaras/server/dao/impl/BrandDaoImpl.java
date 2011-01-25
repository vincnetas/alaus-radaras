/**
 * 
 */
package alaus.radaras.server.dao.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;

import alaus.radaras.server.dao.BrandDao;
import alaus.radaras.shared.model.Brand;

/**
 * @author Vincentas
 *
 */
public class BrandDaoImpl extends BaseDaoImpl<Brand> implements BrandDao {

	/* (non-Javadoc)
	 * @see alaus.radaras.server.dao.impl.BaseDaoImpl#load(java.util.Set)
	 */
	@Override
	public Set<Brand> load(Set<String> ids) {
		Set<Brand> result = new HashSet<Brand>();
		
		for (String id : ids) {
			result.add(getBrand(id));
		}		
		
		return result;
	}
	
	private static Brand getBrand(String id) {
		Brand result = new Brand();
		
		result.setCountry(getCountry());
		result.setDescription(RandomStringUtils.randomAlphabetic(RandomUtils.nextInt(80) + 20));
		result.setId(id);
		result.setTitle("Brand " + id);
		
		return result;
	}
	
	private static String getCountry() {
		String[] tags = new String[] {"Lietuviskas", "Latviskas", "Estiskas", "Suomiskas", "Svediskas", "Lenkiskas"};
		return tags[RandomUtils.nextInt(tags.length)];
	}
	

}
