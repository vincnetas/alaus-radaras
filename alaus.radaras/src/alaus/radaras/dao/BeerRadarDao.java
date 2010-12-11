package alaus.radaras.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import alaus.radaras.dao.model.Brand;
import alaus.radaras.dao.model.FeelingLucky;
import alaus.radaras.dao.model.Location;
import alaus.radaras.dao.model.Pub;
import alaus.radaras.dao.model.Qoute;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;

public class BeerRadarDao {

	private static Map<Context, BeerRadarDao> instances = 
		new HashMap<Context, BeerRadarDao>();
	
	private SQLiteDatabase db;
	
	private BeerRadarDao(Context context) {
		db = new DatabaseAdapter(context).getReadableDatabase();
	}
	
	public static BeerRadarDao getInstance(Context context) {
		BeerRadarDao instance = instances.get(context);
		if (instance == null) {
			instance = new BeerRadarDao(context);
			instances.put(context, instance);
		}
		return instance;
	}
	
	public List<Brand> getBrands() {
		List<Brand> brands = new ArrayList<Brand>();
		Cursor cursor = db.query(
			"brands", 
			new String[] {"id", "title", "icon", "description"},
			null, 
			null, 
			null, 
			null, 
			"title asc");
		if (cursor.moveToFirst()) {
			do {
				brands.add(toBrand(cursor));
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return brands;
	}
	
	public List<Brand> getBrandsByPubId(String pubId) {
		List<Brand> brands = new ArrayList<Brand>();
		Cursor cursor = db.rawQuery(
				"SELECT id, title, icon, description " +
				"FROM brands b INNER JOIN pubs_brands pb ON b.id = pb.brand_id AND pb.pub_id = ?s", 
				new String[] { pubId });
		if (cursor.moveToFirst()) {
			do {
				brands.add(toBrand(cursor));
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return brands;
	}
	
	public List<Pub> getPubsByBrandId(String brandId, Location location) {
		List<Pub> pubs = new ArrayList<Pub>();
		Cursor cursor = db.rawQuery(
			"SELECT id, title, address, notes, phone, url, latitude, longtitude " +
			"FROM pubs p INNER JOIN pubs_brands pb ON p.id = pb.pub_id AND pb.brand_id = ?s", 
			new String[] { brandId });
		if (cursor.moveToFirst()) {
			do {
				pubs.add(toPub(cursor));
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return pubs;
	}
	
	public List<Pub> getNearbyPubs(Location location) {
		List<Pub> pubs = new ArrayList<Pub>();
		Cursor cursor = db.query(
			"os", 
			new String[] {"id", "title", "address", "notes", "phone", "url", "latitude", "longtitude"},
			null, 
			null, 
			null, 
			null, 
			"title asc");
		if (cursor.moveToFirst()) {
			do {
				pubs.add(toPub(cursor));
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return pubs;
	}
	
	public Pub getPub(String pubId) {
		Cursor cursor = db.query(
			"os", 
			new String[] {"id", "title", "address", "notes", "phone", "url", "latitude", "longtitude"},
			"id = ?s", 
			new String[] {pubId}, 
			null, 
			null, 
			"title asc");
		return (cursor.moveToFirst()) ? toPub(cursor) : null;
	}
	
	public FeelingLucky feelingLucky() {
		return new FeelingLucky();
	}
	
	public Drawable getImage(String url) {
		return null;
	}
	
	public Qoute getQoute(int amount)  {
		return new Qoute();
	}

	
	private Brand toBrand(Cursor cursor) {
		Brand brand = new Brand();
		brand.setId(cursor.getString(0));
		brand.setTitle(cursor.getString(1));
		brand.setIcon(cursor.getString(2));
		brand.setDescription(cursor.getString(3));
		return brand;		
	}
	
	private Pub toPub(Cursor cursor) {
		Pub pub = new Pub();
		pub.setId(cursor.getString(0));
		pub.setTitle(cursor.getString(1));
		pub.setAddress(cursor.getString(2));
		pub.setNotes(cursor.getString(3));
		pub.setPhone(cursor.getString(4));
		pub.setUrl(cursor.getString(5));
		
		Location location = new Location();
		location.setLatitude(cursor.getDouble(6));
		location.setLongtitude(cursor.getDouble(7));
		pub.setLocation(location);
		
		return pub;
	}
}
