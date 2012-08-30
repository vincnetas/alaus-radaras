package alaus.radaras.server;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import alaus.radaras.shared.model.Beer;
import alaus.radaras.shared.model.Brand;
import alaus.radaras.shared.model.Pub;
import alaus.radaras.shared.model.Quote;

public class UpdateTest {

	@Test
	public void testToJson() {
		Update update = new Update();
		
		update.setDeletedBeers(asSet(getBeer().getId(), getBeerWithNoTags().getId()));
		update.setDeletedBrands(asSet(getBrand().getId(), getBrandWithNoTags().getId()));
		update.setDeletedPubs(asSet(getPub().getId(), getPubWithNoTags().getId()));
		update.setDeteledQuotes(asSet(getQuote()));

		update.setUpdatedBeers(asSet(getBeer(), getBeerWithNoTags()));
		update.setUpdatedBrands(asSet(getBrand(), getBrandWithNoTags()));
		update.setUpdatedPubs(asSet(getPub(), getPubWithNoTags()));
		update.setUpdatedQuotes(asSet(getQuote()));
		
		String json = Update.toJson(update);
		
		System.out.println(json);
		Update parse = Update.parse(json);
		assertTrue(equals(update, parse));
	}
	
	private boolean equals(Update a, Update b) {
		return 
			a.getDeletedBeers().equals(b.getDeletedBeers()) &&
			a.getDeletedBrands().equals(b.getDeletedBrands()) &&
			a.getDeletedPubs().equals(b.getDeletedPubs()) &&
			a.getUpdatedBeers().equals(b.getUpdatedBeers()) &&
			a.getUpdatedBrands().equals(b.getUpdatedBrands()) &&
			a.getUpdatedPubs().equals(b.getUpdatedPubs())
			;
	}

	private static <T> Set<T> asSet(T... values) {
		return new HashSet<T>(Arrays.asList(values));
	}
	
	private Beer getBeerWithNoTags() {
		Beer result = getBeer();
		result.setTags(new HashSet<String>());
		return result;
	}
	
	private Beer getBeer() {
		Beer result = new Beer();
		
		result.setBrandId("brandId");
		result.setDescription("description");
		result.setIcon("icon");
		result.setTags("tags");
		result.setId("beerid" + Math.random());
		result.setTitle("title");
		
		return result;
	}
	
	private Brand getBrandWithNoTags() {
		Brand result = getBrand();
		result.setTags(new HashSet<String>());
		return result;
	}
	
	private Brand getBrand() {
		Brand result = new Brand();
		
		result.setCountry("country");
		result.setDescription("description");
		result.setHomePage("homePage");
		result.setHometown("homeTown");
		result.setIcon("icon");
		result.setId("brandid" + Math.random());
		result.setTags("tags");
		result.setTitle("title");
		
		return result;
	}
	
	private Pub getPubWithNoTags() {
		Pub result = getPub();
		result.setTags(new HashSet<String>());
		return result;
	}
	
	private Pub getPub() {
		Pub result = new Pub();
		
		result.setAddress("address");
		result.setBeerIds(asSet("1", "2", "3"));
		result.setCity("city");
		result.setCountry("country");
		result.setDescription("description");
		result.setHomepage("homepage");
		result.setHours("hours");
		result.setId("pubid" + Math.random());
		result.setLatitude(0.0);
		result.setLongitude(0.0);
		result.setPhone("phone");
		result.setTags("tags");
		result.setTitle("title");
		
		return result;
	}
	
	private Quote getQuote() {
		Quote result = new Quote();
		
		result.setId("id");
		result.setIndex(0);
		result.setText("text");
		
		return result;
	}

}
