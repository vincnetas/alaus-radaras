package alaus.radaras.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import alaus.radaras.service.model.Brand;
import alaus.radaras.service.model.Country;
import alaus.radaras.service.model.Pub;
import alaus.radaras.service.model.Qoute;
import alaus.radaras.service.model.Tag;
import alaus.radaras.service.model.Taxi;
import android.database.Cursor;
import android.location.Location;

public class DataTransfomer {
	
	public static Brand toBrand(Cursor cursor) {
		Brand brand = new Brand();
		brand.setId(cursor.getString(0));
		brand.setTitle(cursor.getString(1));
		brand.setIcon(cursor.getString(2));
		brand.setDescription(cursor.getString(3));
		return brand;
	}
	
	public static <T> T to(Cursor cursor, Do<T> doe) {
		T result = null;
		
		try {
			if (cursor.moveToFirst()) {
				return doe.get(cursor);
			}
		} finally {
			closeCursor(cursor);
		}
		
		return result;
	}
	
	private static void closeCursor(Cursor cursor) {
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
	}
	
	public static <T> Set<T> toSet(Cursor cursor, Do<T> doe) {
		Set<T> result = new HashSet<T>();

		if (cursor.moveToFirst()) {
			do {
				result.add(doe.get(cursor));
			} while (cursor.moveToNext());
		}

		closeCursor(cursor);

		return result;
	}
	
	public static <T> List<T> toList(Cursor cursor, Do<T> doe) {
		List<T> result = new ArrayList<T>();

		if (cursor.moveToFirst()) {
			do {
				result.add(doe.get(cursor));
			} while (cursor.moveToNext());
		}

		closeCursor(cursor);

		return result;
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
	
	public static Qoute toQoute(Cursor cursor) {
		Qoute qoute = new Qoute();

		qoute.setText(cursor.getString(0));
		
		return qoute;
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
	
	interface Do<T> {		
		T get(Cursor cursor);
	}
	
	static class DoBrand implements Do<Brand> {
		
		public final static DoBrand instance = new DoBrand();
		
		@Override
		public Brand get(Cursor cursor) {
			return toBrand(cursor);
		}
		
	}
	
	static class DoCountry implements Do<Country> {

		public final static DoCountry instance = new DoCountry();
		
		@Override
		public Country get(Cursor cursor) {
			return toCountry(cursor);
		}
		
	}
	
	static class DoPub implements Do<Pub> {

		public final static DoPub instance = new DoPub();
		
		@Override
		public Pub get(Cursor cursor) {
			return toPub(cursor);
		}
		
	}
	
	static class DoTag implements Do<Tag> {

		public final static DoTag instance = new DoTag();
		
		public static final String[] columns = new String[] {"code", "title"};
		
		@Override
		public Tag get(Cursor cursor) {
			return toTag(cursor);
		}		
	}
	
	static class DoTaxi implements Do<Taxi> {

		public final static DoTaxi instance = new DoTaxi();
		
		@Override
		public Taxi get(Cursor cursor) {
			return toTaxi(cursor);
		}		
	}
	
	static class DoQoute implements Do<Qoute> {

		public final static DoQoute instance = new DoQoute();
		
		@Override
		public Qoute get(Cursor cursor) {
			return toQoute(cursor);
		}		
	}
}

