/**
 * 
 */
package alaus.radaras.server.dao.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;

import alaus.radaras.server.dao.BeerDao;
import alaus.radaras.shared.model.Beer;

/**
 * @author Vincentas
 *
 */
public class BeerDaoImpl extends BaseDaoImpl<Beer> implements BeerDao {

	/* (non-Javadoc)
	 * @see alaus.radaras.server.dao.impl.BaseDaoImpl#load(java.util.Set)
	 */
	@Override
	public Set<Beer> load(Set<String> ids) {
		Set<Beer> result = new HashSet<Beer>();
		
		for (String id : ids) {
			result.add(getBeer(id));
		}		
		
		return result;
	}
	
	private static Beer getBeer(String id) {
		Beer result = new Beer();
		
		result.setBrandId(Integer.toString(RandomUtils.nextInt(10)));
		result.setDescription(RandomStringUtils.randomAlphabetic(RandomUtils.nextInt(80) + 20));
		result.setId(id);
		result.setTags(getBeerTags());
		result.setTitle("Beer " + id);
		
		return result;
	}
	
	private static Set<String> getBeerTags() {
		String[] tags = new String[] {"Tamsus", "Stiprus", "Sviesus", "Silpnas", "Nealkoholinis", "Naminis", "Raudonas", "Baltas", "Kartus"};		
		Set<String> result = new HashSet<String>();
		
		for (int i = 0, n = RandomUtils.nextInt(2) + 1; i < n; i++) {
			result.add(tags[RandomUtils.nextInt(tags.length)]);
		}
		
		return result;
	}

	
}
