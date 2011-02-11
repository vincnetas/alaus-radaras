package alaus.radaras.service;

import java.util.Map;

import alaus.radaras.service.model.Brand;
import alaus.radaras.service.model.Country;
import alaus.radaras.service.model.Pub;
import alaus.radaras.service.model.Tag;
import alaus.radaras.service.model.Taxi;
import android.database.Cursor;
import android.location.Location;

public class DataTransfomer {
	

	public static void addToBrands(Cursor cursor, Map<String,Brand> brandMap) {
		String brandId = cursor.getString(0);
		Brand brand;
		if(!brandMap.containsKey(brandId)) {
			brand = toBrand(cursor);
			brandMap.put(brandId, brand);
		} else {
			brand = brandMap.get(brandId);
		}
		Location loc = new Location("");
		loc.setLatitude(cursor.getDouble(4));
		loc.setLongitude(cursor.getDouble(5));
		brand.getLocations().add(loc);	
	}
	
	public static void addToCountries(Cursor cursor, Map<String,Country> countryMap) {
		String countryCode = cursor.getString(0);
		Country country;
		if(!countryMap.containsKey(countryCode)) {
			country = toCountry(cursor);
			countryMap.put(countryCode, country);
		} else {
			country = countryMap.get(countryCode);
		}
		Location loc = new Location("");
		loc.setLatitude(cursor.getDouble(2));
		loc.setLongitude(cursor.getDouble(3));
		country.getLocations().add(loc);	
	}
	
	public static void addToTags(Cursor cursor, Map<String,Tag> tagMap) {
		String tagCode = cursor.getString(0);
		Tag tag;
		if(!tagMap.containsKey(tagCode)) {
			tag = toTag(cursor);
			tagMap.put(tagCode, tag);
		} else {
			tag = tagMap.get(tagCode);
		}
		Location loc = new Location("");
		loc.setLatitude(cursor.getDouble(2));
		loc.setLongitude(cursor.getDouble(3));
		tag.getLocations().add(loc);	
	}
	
	

	public static Brand toBrand(Cursor cursor) {
		Brand brand = new Brand();
		brand.setId(cursor.getString(0));
		brand.setTitle(cursor.getString(1));
		brand.setIcon(cursor.getString(2));
		brand.setDescription(cursor.getString(3));
		return brand;
	}
	
	public static Pub toPub(Cursor cursor) {
		Pub pub = new Pub();
		pub.setId(cursor.getString(0));
		pub.setTitle(cursor.getString(1));
		pub.setAddress(cursor.getString(2));
		pub.setNotes(cursor.getString(3));
		pub.setPhone(cursor.getString(4));
		pub.setUrl(cursor.getString(5));
		pub.setCity(cursor.getString(8));
		
		Location location = new Location("network");
		location.setLatitude(cursor.getDouble(6));
		location.setLongitude(cursor.getDouble(7));
		pub.setLocation(location);
		
		return pub;
	}
	
	public static Tag toTag(Cursor cursor) {
		Tag tag = new Tag();
		tag.setCode(cursor.getString(0));
		tag.setTitle(cursor.getString(1));
		return tag;
	}
	
	public static Country toCountry(Cursor cursor) {
		Country country = new Country();
		country.setCode(cursor.getString(0));
		country.setName(cursor.getString(1));
		return country;
	}

	public static Taxi toTaxi(Cursor cursor) {
		Taxi taxi = new Taxi();
		taxi.setTitle(cursor.getString(0));
		taxi.setPhone(cursor.getString(1));
		taxi.setCity(cursor.getString(2));
		
		Location location = new Location("network");
		location.setLatitude(cursor.getDouble(3));
		location.setLongitude(cursor.getDouble(4));
		taxi.setLocation(location);
		return taxi;
	}

}
