package alaus.radaras.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import alaus.radaras.R;
import alaus.radaras.settings.SettingsManager;
import alaus.radaras.service.model.Brand;
import alaus.radaras.service.model.Country;
import alaus.radaras.service.model.FeelingLucky;
import alaus.radaras.service.model.Pub;
import alaus.radaras.service.model.Qoute;
import alaus.radaras.service.model.Tag;
import alaus.radaras.service.model.Taxi;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.util.Log;

class BeerRadarSqlite extends BeerRadar {
	
	private SQLiteDatabase db;
	
	private Context context;
	
	private SettingsManager settings;
	
	BeerRadarSqlite(Context context) {
		this.db = new BeerRadarSQLiteOpenHelper(context).getReadableDatabase();
		this.settings = new alaus.radaras.settings.SettingsManager(context);
		this.context = context;
	}
	

	public List<Brand> getBrandsByTag(String tag, Location location) {
		
		return getBrands("SELECT b.id, b.title, b.icon, b.description, p.latitude, p.longtitude FROM brands b " +
				"INNER JOIN brands_tags bt ON b.id = bt.brand_id AND bt.tag = ? "  +
				"INNER JOIN pubs_brands as pb on pb.brand_id = b.id " +
				"INNER JOIN pubs as p on p.id=pb.pub_id " +
				"ORDER BY b.title asc ",
				new String[] { tag },
				location);
		
	}
	
	public List<Brand> getBrands(Location location) {
		return getBrands("SELECT b.id, b.title, b.icon, b.description, p.latitude, p.longtitude FROM brands b " +
				"INNER JOIN pubs_brands as pb on pb.brand_id = b.id " +
				"INNER JOIN pubs as p on p.id=pb.pub_id " +
				"ORDER by b.title asc",
				new String[]{}, 
				location);
	}
	

	public List<Brand> getBrandsByCountry(String country, Location location) {
		
		return getBrands("SELECT b.id, b.title, b.icon, b.description, p.latitude, p.longtitude  FROM brands b " +
				"INNER JOIN brands_countries bc ON b.id = bc.brand_id AND bc.country = ? " +
				"INNER JOIN pubs_brands as pb on pb.brand_id = b.id " +
				"INNER JOIN pubs as p on p.id=pb.pub_id " +
				"ORDER BY b.title asc ",
				new String[] { country },
				location);
	}
	
	private List<Brand> getBrands(String query, String[] params, Location location) {
		Map<String,Brand> brandMap = new HashMap<String, Brand>();
		Cursor cursor = db.rawQuery(query, params );
		if (cursor.moveToFirst()) {
			do {
				DataTransfomer.addToBrands(cursor, brandMap);
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		List<Brand> values = new ArrayList<Brand>(brandMap.values());
		LocationFilter.filterByLocations(values, location, getMaxDistance());
		return values;
		
	}
	
	public List<Brand> getBrandsByPubId(String pubId) {
		List<Brand> brands = new ArrayList<Brand>();
		Cursor cursor = db.rawQuery(
				"SELECT id, title, icon, description " +
				"FROM brands b INNER JOIN pubs_brands pb ON b.id = pb.brand_id AND pb.pub_id = ? " +
				"ORDER BY title asc ", 
				new String[] { pubId });
		if (cursor.moveToFirst()) {
			do {
				brands.add(DataTransfomer.toBrand(cursor));
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
			"SELECT id, title, address, notes, phone, url, latitude, longtitude, city " +
			"FROM pubs p INNER JOIN pubs_brands pb ON p.id = pb.pub_id AND pb.brand_id = ? " +
			"ORDER by title asc", 
			new String[] { brandId });
		if (cursor.moveToFirst()) {
			do {
				pubs.add(DataTransfomer.toPub(cursor));
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		LocationFilter.filterBySingleLocation(pubs,location, getMaxDistance());
		return pubs;
	}
	


	public List<Pub> getPubsByTag(String tag, Location location) {
		List<Pub> pubs = new ArrayList<Pub>();
		Cursor cursor = db.rawQuery(
			"SELECT DISTINCT id, title, address, notes, phone, url, latitude, longtitude, city " +
			"FROM pubs p " +
				"INNER JOIN pubs_brands pb ON p.id = pb.pub_id " +
				"INNER JOIN brands_tags bt ON bt.brand_id = pb.brand_id AND bt.tag = ? " +
				"ORDER BY title asc", 
			new String[] { tag });
		if (cursor.moveToFirst()) {
			do {
				pubs.add(DataTransfomer.toPub(cursor));
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		LocationFilter.filterBySingleLocation(pubs,location, getMaxDistance());
		return pubs;
	}
	
	public List<Pub> getPubsByCountry(String country, Location location) {
		List<Pub> pubs = new ArrayList<Pub>();
		Cursor cursor = db.rawQuery(
			"SELECT DISTINCT id, title, address, notes, phone, url, latitude, longtitude, city " +
			"FROM pubs p " +
				"INNER JOIN pubs_brands pb ON p.id = pb.pub_id " +
				"INNER JOIN brands_countries bc ON bc.brand_id = pb.brand_id AND bc.country = ? " +
				"ORDER by title asc", 
			new String[] { country });
		if (cursor.moveToFirst()) {
			do {
				pubs.add(DataTransfomer.toPub(cursor));
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		LocationFilter.filterBySingleLocation(pubs,location, getMaxDistance());
		return pubs;
	}
	
	
	public List<Pub> getNearbyPubs(Location location) {
		List<Pub> pubs = new ArrayList<Pub>();
		Cursor cursor = db.query(
			"pubs", 
			new String[] {"id", "title", "address", "notes", "phone", "url", "latitude", "longtitude", "city"},
			null, 
			null, 
			null, 
			null, 
			"title asc");
		if (cursor.moveToFirst()) {
			do {
				pubs.add(DataTransfomer.toPub(cursor));
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		LocationFilter.filterBySingleLocation(pubs,location, getMaxDistance());
		return pubs;
	}
	
	public Pub getPub(String pubId) {
		Cursor cursor = db.query(
			"pubs", 
			new String[] {"id", "title", "address", "notes", "phone", "url", "latitude", "longtitude", "city"},
			"id = ?", 
			new String[] {pubId}, 
			null, 
			null, 
			"title asc");
		return (cursor.moveToFirst()) ? DataTransfomer.toPub(cursor) : null;
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
		return (cursor.moveToFirst()) ? DataTransfomer.toBrand(cursor) : null;
	}
	

	public FeelingLucky feelingLucky(Location location) {
		
		
		List<Pub> pubs = getNearbyPubs(location);
		
		Pub pub;
		
		if(pubs.size() > 0) {
			pub = pubs.get(new Random().nextInt(pubs.size()));
		} else {
			pub = getRandomPub();
		}
		List<Brand> brands = getBrandsByPubId(pub.getId());
		FeelingLucky lucky = new FeelingLucky();
		lucky.setPub(pub);
		if(brands.size() > 0) {
			lucky.setBrand(brands.get(new Random().nextInt(brands.size())));
		} else {
			lucky.setBrand(null);
		}
		 
		
		return lucky;
	
	}
	
	private Pub getRandomPub() {
		
		Cursor cursor = db.rawQuery(
				"SELECT id FROM pubs p " +
				"INNER JOIN pubs_brands pb ON p.id = pb.pub_id " +
				"ORDER BY RANDOM() LIMIT 1", null);

		String pubId = null;
		if (cursor.moveToFirst()) {
			pubId = cursor.getString(0);
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		Pub pub = getPub(pubId);
		return pub;
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
	
		public List<Country> getCountries(Location location) {
		
		Map<String,Country> countryMap = new HashMap<String, Country>();
		Cursor cursor = db.rawQuery("SELECT c.code,c.name, p.latitude, p.longtitude FROM  countries as c "+
				"INNER JOIN brands_countries as bc on bc.country = c.code " +
				"INNER JOIN pubs_brands as pb on pb.brand_id = bc.brand_id " +
				"INNER JOIN pubs as p on p.id=pb.pub_id order by c.name asc", new String[] {});

		if (cursor.moveToFirst()) {
			do {
				DataTransfomer.addToCountries(cursor, countryMap);
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		List<Country> values = new ArrayList<Country>(countryMap.values());
		LocationFilter.filterByLocations(values, location, getMaxDistance());
		return values;
	}

	
	
	public List<Tag> getTags(Location location) {
		Map<String,Tag> tagMap = new HashMap<String, Tag>();
		Cursor cursor = db.rawQuery("SELECT t.code,t.title, p.latitude, p.longtitude FROM  tags as t "+
				"INNER JOIN brands_tags as bt on bt.tag = t.code " +
				"INNER JOIN pubs_brands as pb on pb.brand_id = bt.brand_id " +
				"INNER JOIN pubs as p on p.id=pb.pub_id order by t.title asc", new String[] {});

		if (cursor.moveToFirst()) {
			do {
				DataTransfomer.addToTags(cursor, tagMap);
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		List<Tag> values = new ArrayList<Tag>(tagMap.values());
		LocationFilter.filterByLocations(values, location, getMaxDistance());
		return values;
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
			return DataTransfomer.toTag(cursor);
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
			return DataTransfomer.toCountry(cursor);
		}
		return null;
	}


	@Override
	public double getMaxDistance() {
		return settings.getMaxDistance();
	}


	@Override
	public List<Taxi> getTaxies(Location location) {
		List<Taxi> taxies = new ArrayList<Taxi>();
		Cursor cursor = db.query(
			"taxi", 
			new String[] {"title", "phone", "city", "latitude", "longitude"},
			null, 
			null, 
			null, 
			null, 
			"title asc");
		if (cursor.moveToFirst()) {
			do {
				taxies.add(DataTransfomer.toTaxi(cursor));
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		Log.e("d",String.valueOf(taxies.size()));
		Log.e("dlt",String.valueOf(taxies.get(0).getLocation().getLatitude()));
		Log.e("dlng",String.valueOf(taxies.get(0).getLocation().getLongitude()));
		
		Log.e("dlt1",String.valueOf(location.getLatitude()));
		Log.e("dlng1",String.valueOf(location.getLongitude()));
		
		LocationFilter.filterBySingleLocation(taxies,location, 20000);
		return taxies;
	}
	
}
