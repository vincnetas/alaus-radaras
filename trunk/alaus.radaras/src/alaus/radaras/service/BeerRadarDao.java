package alaus.radaras.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import alaus.radaras.R;
import alaus.radaras.dao.model.Brand;
import alaus.radaras.dao.model.Country;
import alaus.radaras.dao.model.FeelingLucky;
import alaus.radaras.dao.model.Location;
import alaus.radaras.dao.model.Pub;
import alaus.radaras.dao.model.Qoute;
import alaus.radaras.dao.model.Tag;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;

public class BeerRadarDao {

	private static Map<Context, BeerRadarDao> instances = 
		new HashMap<Context, BeerRadarDao>();
	
	private SQLiteDatabase db;
	
	private Context context;
	
	private BeerRadarDao(Context context) {
		db = new DatabaseAdapter(context).getReadableDatabase();
		this.context = context;
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
				"FROM brands b INNER JOIN pubs_brands pb ON b.id = pb.brand_id AND pb.pub_id = ?", 
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
	
	public List<Brand> getBrandsByCountry(String country) {
		List<Brand> brands = new ArrayList<Brand>();
		Cursor cursor = db.rawQuery(
				"SELECT id, title, icon, description " +
				"FROM brands b INNER JOIN brands_countries bc ON b.id = bc.brand_id AND bc.country = ?", 
				new String[] { country });
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
	
	public List<Brand> getBrandsByTag(String tag) {
		List<Brand> brands = new ArrayList<Brand>();
		Cursor cursor = db.rawQuery(
				"SELECT id, title, icon, description " +
				"FROM brands b INNER JOIN brands_tags bt ON b.id = bt.brand_id AND bt.tag = ?", 
				new String[] { tag });
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
			"FROM pubs p INNER JOIN pubs_brands pb ON p.id = pb.pub_id AND pb.brand_id = ?", 
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
	
	public List<Pub> getPubsByTag(String tag, Location location) {
		List<Pub> pubs = new ArrayList<Pub>();
		Cursor cursor = db.rawQuery(
			"SELECT DISTINCT id, title, address, notes, phone, url, latitude, longtitude " +
			"FROM pubs p " +
				"INNER JOIN pubs_brands pb ON p.id = pb.pub_id " +
				"INNER JOIN brands_tags bt ON bt.brand_id = pb.brand_id AND bt.tag = ?", 
			new String[] { tag });
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
	
	public List<Pub> getPubsByCountry(String country, Location location) {
		List<Pub> pubs = new ArrayList<Pub>();
		Cursor cursor = db.rawQuery(
			"SELECT DISTINCT id, title, address, notes, phone, url, latitude, longtitude " +
			"FROM pubs p " +
				"INNER JOIN pubs_brands pb ON p.id = pb.pub_id " +
				"INNER JOIN brands_countries bc ON bc.brand_id = pb.brand_id AND bc.country = ?", 
			new String[] { country });
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
			"pubs", 
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
			"pubs", 
			new String[] {"id", "title", "address", "notes", "phone", "url", "latitude", "longtitude"},
			"id = ?", 
			new String[] {pubId}, 
			null, 
			null, 
			"title asc");
		return (cursor.moveToFirst()) ? toPub(cursor) : null;
	}
	
	public Brand getBrand(String brandId) {		
		Cursor cursor = db.query(
				"brands", 
				new String[] {"id", "title", "icon", "description"},
				"id = ?", 
				new String[] {brandId}, 
				null, 
				null, 
				"title asc");
		return (cursor.moveToFirst()) ? toBrand(cursor) : null;
	}
	
	public FeelingLucky feelingLucky() {
		Cursor cursor = db.rawQuery(
				"SELECT id FROM pubs p " +
				"INNER JOIN pubs_brands pb ON p.id = pb.pub_id " +
				"ORDER BY RANDOM() LIMIT 1", null);

		String pubId = null;
		if (cursor.moveToFirst()) {
			pubId = cursor.getString(0);
		}
		
		Pub pub = getPub(pubId);
		List<Brand> brands = getBrandsByPubId(pubId);
		FeelingLucky lucky = new FeelingLucky();
		lucky.setPub(pub);
		lucky.setBrand(brands.get(new Random().nextInt(brands.size())));
		
		return lucky;
	}
	
	public Drawable getImage(String url) {
		Resources resources = context.getResources();
		Integer id = 0;
		if(url != null) {
			id = resources.getIdentifier("@drawable/brand_" + url, "drawable",
                    context.getPackageName());
		}
		if(id == 0) {
			id = R.drawable.brand_default;
		}
			
		return resources.getDrawable(id);
	}
	
	public Qoute getQoute(int amount)  {
		Qoute qoute = new Qoute();
		Cursor cursor = db.rawQuery(
				"SELECT text FROM qoutes q " +
				"WHERE q.amount = ? " +
				"ORDER BY RANDOM() LIMIT 1", 
				new String[] { Integer.valueOf(amount).toString() });
		if (cursor.moveToFirst()) {
			qoute.setText(cursor.getString(0));
			return qoute;
		} else {
			return null;
		}
	}
	
	public List<Country> getCountries() {
		List<Country> countries = new ArrayList<Country>();
		Cursor cursor = db.query(
				"countries", 
				new String[] {"code", "name"},
				null, 
				null, 
				null, 
				null, 
				"name asc");

		if (cursor.moveToFirst()) {
			do {
				countries.add(toCountry(cursor));
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return countries;
	}
	
	public List<Tag> getTags() {
		List<Tag> tags = new ArrayList<Tag>();
		Cursor cursor = db.query(
				"tags", 
				new String[] {"code", "title"},
				null, 
				null, 
				null, 
				null, 
				"title asc");
		
		if (cursor.moveToFirst()) {
			do {
				tags.add(toTag(cursor));
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return tags;
	}
	
	public Tag getTag(String code) {
		Cursor cursor = db.query(
				"tags", 
				new String[] {"code", "title"},
				"code = ?", 
				new String[] { code }, 
				null, 
				null, 
				null);
		if (cursor.moveToFirst()) {
			return toTag(cursor);
		}
		return null;
	}

	public Country getCountry(String code) {
		Cursor cursor = db.query(
				"countries", 
				new String[] {"code", "name"},
				"code = ?", 
				new String[] { code }, 
				null, 
				null, 
				null);
		if (cursor.moveToFirst()) {
			return toCountry(cursor);
		}
		return null;
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
	
	private Tag toTag(Cursor cursor) {
		Tag tag = new Tag();
		tag.setCode(cursor.getString(0));
		tag.setTitle(cursor.getString(1));
		return tag;
	}
	
	private Country toCountry(Cursor cursor) {
		Country country = new Country();
		country.setCode(cursor.getString(0));
		country.setName(cursor.getString(1));
		return country;
	}
}
