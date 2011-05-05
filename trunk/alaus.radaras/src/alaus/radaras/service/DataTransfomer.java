package alaus.radaras.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import alaus.radaras.service.model.Brand;
import alaus.radaras.service.model.Pub;
import alaus.radaras.service.model.Qoute;
import alaus.radaras.service.model.Taxi;
import android.database.Cursor;
import android.location.Location;

public class DataTransfomer {
	
	public static <T> T to(Cursor cursor, Do<T> doe) {
		try {
			T result = null;
			
			if (cursor.moveToFirst()) {
				result = doe.get(cursor);
			}
			
			return result;
		} finally {
			closeCursor(cursor);
		}		
	}
	
	private static void closeCursor(Cursor cursor) {
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
	}
	
	public static <T> Set<T> toSet(Cursor cursor, Do<T> doe) {
		try {
			Set<T> result = new HashSet<T>();
	
			if (cursor.moveToFirst()) {
				do {
					result.add(doe.get(cursor));
				} while (cursor.moveToNext());
			}
	
			return result;
		} finally {
			closeCursor(cursor);
		}
	}
	
	public static <T> List<T> toList(Cursor cursor, Do<T> doe) {
		try {
			List<T> result = new ArrayList<T>();
	
			if (cursor.moveToFirst()) {
				do {
					result.add(doe.get(cursor));
				} while (cursor.moveToNext());
			}
			
			return result;
		} finally {
			closeCursor(cursor);
		}		
	}
	
	interface Do<T> {		
		T get(Cursor cursor);
	}
	
	static class DoBrand implements Do<Brand> {
		
		public final static DoBrand instance = new DoBrand();
		
		public static String[] columns = new String[] {"id", "title", "icon", "description"};
		
		@Override
		public Brand get(Cursor cursor) {
			Brand brand = new Brand();

			brand.setId(cursor.getString(0));
			brand.setTitle(cursor.getString(1));
			brand.setIcon(cursor.getString(2));
			brand.setDescription(cursor.getString(3));
			
			return brand;
		}
		
	}
	
	static class DoString implements Do<String> {
		
		public final static DoString instance = new DoString();
		
		public static String[] columns = new String[] {"id", "title", "icon", "description"};
		
		@Override
		public String get(Cursor cursor) {
			return cursor.getString(0);
		}
		
	}
	
	static class DoPub implements Do<Pub> {

		public final static DoPub instance = new DoPub();
		
		@Override
		public Pub get(Cursor cursor) {
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
	}
	
	static class DoTaxi implements Do<Taxi> {

		public final static DoTaxi instance = new DoTaxi();
		
		@Override
		public Taxi get(Cursor cursor) {
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
	
	static class DoQoute implements Do<Qoute> {

		public final static DoQoute instance = new DoQoute();
		
		@Override
		public Qoute get(Cursor cursor) {
			Qoute qoute = new Qoute();

			qoute.setText(cursor.getString(0));
			
			return qoute;
		}		
	}
}

