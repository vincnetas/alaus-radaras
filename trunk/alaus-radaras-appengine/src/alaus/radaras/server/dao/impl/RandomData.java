package alaus.radaras.server.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;

import alaus.radaras.shared.model.Beer;
import alaus.radaras.shared.model.Brand;
import alaus.radaras.shared.model.LocationBounds;
import alaus.radaras.shared.model.Pub;

public class RandomData {

	private static Set<Brand> randomBrands(Set<String> ids) {
		Set<Brand> result = new HashSet<Brand>();
		
		for (String id : ids) {
			result.add(getBrand(id));
		}		
		
		return result;
	}
	
	private Set<Beer> randomBeer(Collection<String> ids) {
		Set<Beer> result = new HashSet<Beer>();
		
		for (String id : ids) {
			result.add(getBeer(id));
		}		
		
		return result;
	}
	
	private static List<Pub> randomPubs(LocationBounds bounds) {
		List<Pub> result = new ArrayList<Pub>();

		for (int i = 0, n = RandomUtils.nextInt(10) + 20; i < n; i++) {
			result.add(getPub(bounds, Integer.toString(i)));
		}

		return result;
	}

	private static Pub getPub(LocationBounds bounds, String id) {
		Pub pub = new Pub();

		pub.setAddress(RandomStringUtils.randomAlphabetic(RandomUtils.nextInt(20) + 8));
		pub.setBeerIds(getBeerIds());
		pub.setCity(getCity());
		pub.setCountry("Country " + RandomStringUtils.randomAlphabetic(RandomUtils.nextInt(10) + 8));
		pub.setDescription(RandomStringUtils.randomAlphabetic(RandomUtils.nextInt(80) + 20));
		pub.setHomepage("http://www.delfi.lt");
		pub.setId(id);
		pub.setLatitude(getDouble(bounds.getSouthWest().getLatitude(), bounds.getNorthEast().getLatitude()));
		pub.setLongitude(getDouble(bounds.getSouthWest().getLongitude(), bounds.getNorthEast().getLongitude()));
		pub.setPhone("+34552242342");
		pub.setTags(getPubTags());
		pub.setTitle("Title " + id);

		return pub;
	}

	private static Set<String> getBeerIds() {
		Set<String> result = new HashSet<String>();

		for (int i = 0, n = RandomUtils.nextInt(8) + 3; i < n; i++) {
			result.add(Integer.toString(RandomUtils.nextInt(10)));
		}

		return result;
	}

	private static double getDouble(double min, double max) {
		return RandomUtils.nextDouble() * (max - min) + min;
	}

	private static Set<String> getPubTags() {
		String[] tags = new String[] { "Kavinë", "Restoranas", "Pubas", "Krautuvë", "Rûsys", "Bravoras" };
		Set<String> result = new HashSet<String>();

		for (int i = 0, n = RandomUtils.nextInt(2) + 1; i < n; i++) {
			result.add(tags[RandomUtils.nextInt(tags.length)]);
		}

		return result;
	}

	private static String getCity() {
		String[] city = new String[] { "Vilnius", "Kaunas", "Klaipeda" };
		return city[RandomUtils.nextInt(city.length)];
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
		String[] tags = new String[] { "Tamsus", "Stiprus", "Sviesus", "Silpnas", "Nealkoholinis", "Naminis", "Raudonas", "Baltas", "Kartus" };
		Set<String> result = new HashSet<String>();

		for (int i = 0, n = RandomUtils.nextInt(2) + 1; i < n; i++) {
			result.add(tags[RandomUtils.nextInt(tags.length)]);
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
