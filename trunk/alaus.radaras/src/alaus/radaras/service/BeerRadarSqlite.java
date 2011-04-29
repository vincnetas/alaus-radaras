package alaus.radaras.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

import alaus.radaras.R;
import alaus.radaras.service.DataTransfomer.DoBrand;
import alaus.radaras.service.DataTransfomer.DoCountry;
import alaus.radaras.service.DataTransfomer.DoPub;
import alaus.radaras.service.DataTransfomer.DoQoute;
import alaus.radaras.service.DataTransfomer.DoTag;
import alaus.radaras.service.DataTransfomer.DoTaxi;
import alaus.radaras.service.model.Brand;
import alaus.radaras.service.model.Country;
import alaus.radaras.service.model.FeelingLucky;
import alaus.radaras.service.model.Pub;
import alaus.radaras.service.model.Qoute;
import alaus.radaras.service.model.Tag;
import alaus.radaras.service.model.Taxi;
import alaus.radaras.settings.SettingsManager;
import alaus.radaras.shared.model.Beer;
import alaus.radaras.sorters.BrandNameSorter;
import alaus.radaras.sorters.CountryNameSorter;
import alaus.radaras.sorters.PubNameSorter;
import alaus.radaras.sorters.TagNameSorter;
import alaus.radaras.utils.Bounds;
import alaus.radaras.utils.DistanceCalculator;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.graphics.drawable.Drawable;
import android.location.Location;

public class BeerRadarSqlite implements BeerRadar, BeerUpdate {
	
	private SQLiteDatabase db;
	
	private Context context;
	
	private SettingsManager settings;
	
	private final static ContentValues WRITE_LOCK = new ContentValues();
	
	private final DatabaseUtils.InsertHelper brandTagInsert;
	
	private final DatabaseUtils.InsertHelper brandsInsert;
	
	private final DatabaseUtils.InsertHelper pubsInsert;
	
	private final DatabaseUtils.InsertHelper pubBrandsInsert;
	
	private final DatabaseUtils.InsertHelper companiesInsert;	
	
	public BeerRadarSqlite(Context context) {
		BeerRadarSQLiteOpenHelper helper = new BeerRadarSQLiteOpenHelper(context);
		this.db = helper.getReadableDatabase();
		this.settings = new alaus.radaras.settings.SettingsManager(context);
		this.context = context;
		
		brandTagInsert = new DatabaseUtils.InsertHelper(db, "brands_tags");
		brandsInsert = new DatabaseUtils.InsertHelper(db, "brands");
		pubsInsert = new DatabaseUtils.InsertHelper(db, "pubs");
		pubBrandsInsert = new DatabaseUtils.InsertHelper(db, "pubs_brands");
		companiesInsert = new DatabaseUtils.InsertHelper(db, "companies");
	}
	
	@Override
	public List<Brand> getBrandsByTag(String tag, Location location) {
		Bounds bounds = DistanceCalculator.getBounds(location, getMaxDistance());
		return getBrands("SELECT DISTINCT b.id, b.title, b.icon, b.description FROM brands b " +
				"INNER JOIN brands_tags bt ON b.id = bt.brand_id AND bt.tag = ? "  +
				"INNER JOIN pubs_brands as pb on pb.brand_id = b.id " +
				"INNER JOIN pubs as p on p.id=pb.pub_id " +
				"WHERE p.latitude < ? AND p.latitude > ? AND p.longtitude < ? AND p.longtitude > ?",
				new String[] { tag , 
					Double.toString(bounds.getMaxLatitude()), 
					Double.toString(bounds.getMinLatitude()), 
					Double.toString(bounds.getMaxLongitude()), 
					Double.toString(bounds.getMinLongitude())
				},
				location);
		
	}
	
	@Override
	public List<Brand> getBrands(Location location) {
		Bounds bounds = DistanceCalculator.getBounds(location, getMaxDistance());
		return getBrands("SELECT DISTINCT b.id, b.title, b.icon, b.description FROM brands b " +
				"INNER JOIN pubs_brands as pb on pb.brand_id = b.id " +
				"INNER JOIN pubs as p on p.id=pb.pub_id " +
				"WHERE p.latitude < ? AND p.latitude > ? AND p.longtitude < ? AND p.longtitude > ?",
				new String[] { 
					Double.toString(bounds.getMaxLatitude()), 
					Double.toString(bounds.getMinLatitude()), 
					Double.toString(bounds.getMaxLongitude()), 
					Double.toString(bounds.getMinLongitude())
				}, 
				location);
	}
	
	@Override
	public List<Brand> getBrandsByCountry(String country, Location location) {	
		Bounds bounds = DistanceCalculator.getBounds(location, getMaxDistance());
		return getBrands("SELECT DISTINCT b.id, b.title, b.icon, b.description FROM brands b " +
				"INNER JOIN companies c ON b.companyId = c.id AND c.country = ? " +
				"INNER JOIN pubs_brands as pb on pb.brand_id = b.id " +
				"INNER JOIN pubs as p on p.id=pb.pub_id " +
				"WHERE p.latitude < ? AND p.latitude > ? AND p.longtitude < ? AND p.longtitude > ?",
				new String[] { country , 
					Double.toString(bounds.getMaxLatitude()), 
					Double.toString(bounds.getMinLatitude()), 
					Double.toString(bounds.getMaxLongitude()), 
					Double.toString(bounds.getMinLongitude())
				},
				location);
	}
	
	private List<Brand> getBrands(String query, String[] params, Location location) {
		Cursor cursor = db.rawQuery(query, params);
		List<Brand> values = DataTransfomer.toList(cursor, DoBrand.instance);
		
		Collections.sort(values, new BrandNameSorter());
		
		return values;		
	}
	
	@Override
	public List<Brand> getBrandsByPubId(String pubId) {
		Cursor cursor = db.rawQuery(
				"SELECT id, title, icon, description " +
				"FROM brands b INNER JOIN pubs_brands pb ON b.id = pb.brand_id AND pb.pub_id = ?",
				new String[] { pubId });
		
		List<Brand> brands = DataTransfomer.toList(cursor, DoBrand.instance);
		
		Collections.sort(brands, new BrandNameSorter());
		
		return brands;
	}
	
	@Override
	public List<Pub> getPubsByBrandId(String brandId, Location location) {
		Bounds bounds = DistanceCalculator.getBounds(location, getMaxDistance());
		Cursor cursor = db.rawQuery(
			"SELECT id, title, address, notes, phone, url, latitude, longtitude, city " +
			"FROM pubs p INNER JOIN pubs_brands pb ON p.id = pb.pub_id AND pb.brand_id = ? " +
			"WHERE latitude < ? AND latitude > ? AND longtitude < ? AND longtitude > ?",
			new String[] { brandId,
				Double.toString(bounds.getMaxLatitude()), 
				Double.toString(bounds.getMinLatitude()), 
				Double.toString(bounds.getMaxLongitude()), 
				Double.toString(bounds.getMinLongitude())
			});
		
		List<Pub> pubs = DataTransfomer.toList(cursor, DoPub.instance);

		Collections.sort(pubs, new PubNameSorter());
		
		return pubs;
	}
	
	@Override
	public List<Pub> getPubsByTag(String tag, Location location) {
		Bounds bounds = DistanceCalculator.getBounds(location, getMaxDistance());
		Cursor cursor = db.rawQuery(
			"SELECT DISTINCT id, title, address, notes, phone, url, latitude, longtitude, city " +
			"FROM pubs p " +
				"INNER JOIN pubs_brands pb ON p.id = pb.pub_id " +
				"INNER JOIN brands_tags bt ON bt.brand_id = pb.brand_id AND bt.tag = ? " +
				"WHERE latitude < ? AND latitude > ? AND longtitude < ? AND longtitude > ?",
			new String[] { tag,
				Double.toString(bounds.getMaxLatitude()), 
				Double.toString(bounds.getMinLatitude()), 
				Double.toString(bounds.getMaxLongitude()), 
				Double.toString(bounds.getMinLongitude())
			});
		
		List<Pub> pubs = DataTransfomer.toList(cursor, DoPub.instance);

		Collections.sort(pubs, new PubNameSorter());
		
		return pubs;
	}
	
	@Override
	public List<Pub> getPubsByCountry(String country, Location location) {
		Bounds bounds = DistanceCalculator.getBounds(location, getMaxDistance());
		Cursor cursor = db.rawQuery(
			"SELECT DISTINCT p.id, p.title, p.address, p.notes, p.phone, p.url, p.latitude, p.longtitude, p.city " +
			"FROM pubs p " +
				"INNER JOIN pubs_brands pb ON p.id = pb.pub_id " +
				"INNER JOIN brands b ON b.id = pb.pub_id " +
				"INNER JOIN companies c ON c.id = b.companyId AND c.country = ? " +
				"WHERE latitude < ? AND latitude > ? AND longtitude < ? AND longtitude > ?",
			new String[] { country,
				Double.toString(bounds.getMaxLatitude()), 
				Double.toString(bounds.getMinLatitude()), 
				Double.toString(bounds.getMaxLongitude()), 
				Double.toString(bounds.getMinLongitude())					
			});
		
		List<Pub> pubs = DataTransfomer.toList(cursor, DoPub.instance);
		
		Collections.sort(pubs, new PubNameSorter());
		
		return pubs;
	}
	
	@Override
	public List<Pub> getNearbyPubs(Location location) {
		Bounds bounds = DistanceCalculator.getBounds(location, getMaxDistance());
		Cursor cursor = db.query(
			"pubs", 
			new String[] {"id", "title", "address", "notes", "phone", "url", "latitude", "longtitude", "city"},
			"latitude < ? AND latitude > ? AND longtitude < ? AND longtitude > ?", 
			new String[] {
				Double.toString(bounds.getMaxLatitude()), 
				Double.toString(bounds.getMinLatitude()), 
				Double.toString(bounds.getMaxLongitude()), 
				Double.toString(bounds.getMinLongitude()) 
			},
			null, 
			null, 
			null);
		
		List<Pub> pubs = DataTransfomer.toList(cursor, DoPub.instance);
		
		Collections.sort(pubs, new PubNameSorter());
		
		return pubs;
	}
	
	@Override
	public Pub getPub(String pubId) {
		Cursor cursor = db.query(
			"pubs", 
			new String[] {"id", "title", "address", "notes", "phone", "url", "latitude", "longtitude", "city"},
			"id = ?", 
			new String[] {pubId}, 
			null, 
			null, 
			"title asc");
		
		return DataTransfomer.to(cursor, DoPub.instance);
	}
	
	@Override
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
	
	@Override
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
		Cursor cursor = db.query(
				"pubs", 
				new String[] {"id", "title", "address", "notes", "phone", "url", "latitude", "longtitude", "city"},
				null,
				null, 
				null, 
				null, 
				"RANDOM()",
				"1");
			
		return DataTransfomer.to(cursor, DoPub.instance);
	}
	
	@Override
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
	
	@Override
	public Qoute getQoute(int amount)  {
		Cursor cursor = db.rawQuery(
				"SELECT text FROM qoutes q " +
				"WHERE q.amount = ? " +
				"ORDER BY RANDOM() LIMIT 1", 
				new String[] { Integer.valueOf(amount).toString() });
		
		return DataTransfomer.to(cursor, DoQoute.instance);
	}
	
	@Override
	public List<Country> getCountries(Location location) {
		Bounds bounds = DistanceCalculator.getBounds(location, getMaxDistance());
		Cursor cursor = db.rawQuery("SELECT DISTINCT c.country FROM companies as c " +
				"INNER JOIN brands as b ON b.companyId = c.id " +
				"INNER JOIN pubs_brands as pb ON pb.brand_id = b.id " +
				"INNER JOIN pubs as p on p.id = pb.pub_id " +
				"WHERE p.latitude < ? AND p.latitude > ? AND p.longtitude < ? AND p.longtitude > ?",
				new String[] {
					Double.toString(bounds.getMaxLatitude()), 
					Double.toString(bounds.getMinLatitude()), 
					Double.toString(bounds.getMaxLongitude()), 
					Double.toString(bounds.getMinLongitude())				
				});

		List<Country> values = DataTransfomer.toList(cursor, DoCountry.instance);
		
		Collections.sort(values, new CountryNameSorter(context));
		
		return values;
	}

	@Override
	public List<Tag> getTags(Location location) {
		Bounds bounds = DistanceCalculator.getBounds(location, getMaxDistance());
		Cursor cursor = db.rawQuery("SELECT DISTINCT t.code, t.title FROM tags as t "+
				"INNER JOIN brands_tags as bt on bt.tag = t.code " +
				"INNER JOIN pubs_brands as pb on pb.brand_id = bt.brand_id " +
				"INNER JOIN pubs as p on p.id = pb.pub_id " +
				"WHERE p.latitude < ? AND p.latitude > ? AND p.longtitude < ? AND p.longtitude > ?",
				new String[] {
					Double.toString(bounds.getMaxLatitude()), 
					Double.toString(bounds.getMinLatitude()), 
					Double.toString(bounds.getMaxLongitude()), 
					Double.toString(bounds.getMinLongitude())
				});
		
		List<Tag> values = DataTransfomer.toList(cursor, DoTag.instance);
		
		Collections.sort(values, new TagNameSorter());
		
		return values;
	}
	
	@Override
	public Tag getTag(String code) {
		Cursor cursor = db.query(
				"tags", 
				DoTag.columns,
				"code = ?", 
				new String[] { code }, 
				null, 
				null, 
				null);
		
		return DataTransfomer.to(cursor, DoTag.instance);
	}

	@Override
	public Country getCountry(String code) {
		return new Country(code);
	}

	private double getMaxDistance() {
		return settings.getMaxDistance();
	}

	@Override
	public List<Taxi> getTaxies(Location location) {
		Bounds bounds = DistanceCalculator.getBounds(location, 20000);
		Cursor cursor = db.query(
			"taxi", 
			new String[] {"title", "phone", "city", "latitude", "longitude"},
			"latitude < ? AND latitude > ? AND longitude < ? AND longitude > ?", 
			new String[] {
				Double.toString(bounds.getMaxLatitude()), 
				Double.toString(bounds.getMinLatitude()), 
				Double.toString(bounds.getMaxLongitude()), 
				Double.toString(bounds.getMinLongitude()) 
			},
			null, 
			null, 
			"title ASC");
		
		return DataTransfomer.toList(cursor, DoTaxi.instance);
	}
	
	@Override
	public Set<Tag> getBrandTags(String brandId) {		
		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
		
		builder.setTables("tags INNER JOIN brands_tags bt ON (bt.tag = tags.code)");
		Cursor cursor = builder.query(db, DoTag.columns, "bt.brand_id = ?", new String[] {brandId}, null, null, null);	
				
		return DataTransfomer.toSet(cursor, DoTag.instance);		
	}

	/* (non-Javadoc)
	 * @see alaus.radaras.service.BeerRadar#getBrandsByCompany(java.lang.String)
	 */
	@Override
	public List<Brand> getBrandsByCompany(String companyId) {
		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
		
		builder.setTables("brands");
		Cursor cursor = builder.query(db, DoBrand.columns, "companyId = ?", new String[] {companyId}, null, null, null);	
				
		return DataTransfomer.toList(cursor, DoBrand.instance);	
	}
	
	/*
	 * Update part
	 */
	
	private ContentValues getContentValues() {
		synchronized (WRITE_LOCK) {
			WRITE_LOCK.clear();
			return WRITE_LOCK;
		}
	}
	
	private static String[] STRING_ARRAY = new String[1];
	
	private static String[] getStringArray(String value) {
		STRING_ARRAY[0] = value;
		return STRING_ARRAY;
	}

	private static String[] STRING_ARRAYS = new String[2];
	
	private static String[] getStringArray(String value1, String value2) {
		STRING_ARRAYS[0] = value1;
		STRING_ARRAYS[1] = value2;
		return STRING_ARRAYS;
	}
	
    /* (non-Javadoc)
     * @see alaus.radaras.service.Beer#updateBrand(alaus.radaras.shared.model.Beer)
     */	
    @Override
    public void updateBrand(Beer beer) {
    	synchronized (WRITE_LOCK) {
	    	ContentValues beerValues = getContentValues();
	        beerValues.put("id", beer.getId());
	        beerValues.put("title", beer.getTitle());
	        beerValues.put("icon", beer.getIcon());
	        beerValues.put("description", beer.getDescription());
	        beerValues.put("companyId", beer.getBrandId());
	        
	        db.beginTransaction();
	        try {	            
	            brandsInsert.replace(beerValues);
	            
	            Set<Tag> brandTags = getBrandTags(beer.getId());
	            for (Tag tag : brandTags) {
	                if (!beer.getTags().remove(tag.getCode())) {
	                    db.delete("brands_tags", "brand_id = ? AND tag = ?", getStringArray(beer.getId(), tag.getCode()));
	                }
	            }
	            
	            
	            for (String tag : beer.getTags()) {
	                ContentValues tagValues = getContentValues();
	                tagValues.put("brand_id", beer.getId());
	                tagValues.put("tag", tag);
	                brandTagInsert.insert(tagValues);	                
	            }
	            
	            db.setTransactionSuccessful();
	        } finally {
	            db.endTransaction();
	        }
    	}
    }

    /* (non-Javadoc)
     * @see alaus.radaras.service.Beer#updatePub(alaus.radaras.shared.model.Pub)
     */
    @Override
    public void updatePub(alaus.radaras.shared.model.Pub pub) {
    	synchronized (WRITE_LOCK) {
	    	ContentValues pubValues = getContentValues();
	        pubValues.put("id", pub.getId());
	        pubValues.put("title", pub.getTitle());
	        pubValues.put("longtitude", pub.getLongitude());
	        pubValues.put("latitude", pub.getLatitude());
	        pubValues.put("address", pub.getAddress());
	        pubValues.put("city", pub.getCity());
	        pubValues.put("notes", pub.getDescription());
	        pubValues.put("phone", pub.getPhone());
	        pubValues.put("url", pub.getHomepage());
	        
	        db.beginTransaction();
	        try {	            
	            pubsInsert.replace(pubValues);
	            
	            /*
	             * Delete removed brands from pub association
	             */
	            List<Brand> pubBrands = getBrandsByPubId(pub.getId());
	            for (Brand brand : pubBrands) {
	                if (!pub.getBeerIds().remove(brand.getId())) {
	                    db.delete("pubs_brands", "pub_id = ? AND brand_id = ?", getStringArray(pub.getId(), brand.getId()));
	                }
	            }
	            
	            /*
	             * Add new brands to pub association
	             */
	            for (String brandId : pub.getBeerIds()) {
	                ContentValues brandValues = getContentValues();
	                brandValues.put("brand_id", brandId);
	                brandValues.put("pub_id", pub.getId());	                
	                pubBrandsInsert.insert(brandValues);
	            }
	            
	            db.setTransactionSuccessful();
	        } finally {
	            db.endTransaction();
	        }
    	}
    }
    
    /* (non-Javadoc)
     * @see alaus.radaras.service.Beer#updateCompany(alaus.radaras.shared.model.Brand)
     */
    @Override
    public void updateCompany(alaus.radaras.shared.model.Brand brand) {
    	synchronized (WRITE_LOCK) {
	    	ContentValues companyValues = getContentValues();
	        companyValues.put("country", brand.getCountry());
	        companyValues.put("description", brand.getDescription());
	        companyValues.put("homePage", brand.getHomePage());
	        companyValues.put("hometown", brand.getHometown());
	        companyValues.put("icon", brand.getIcon());
	        companyValues.put("id", brand.getId());
	        companyValues.put("title", brand.getTitle());
	        
	        companiesInsert.replace(companyValues);
    	}
    }
    
    /* (non-Javadoc)
     * @see alaus.radaras.service.Beer#deleteBrand(java.lang.String)
     */
    @Override
    public void deleteBrand(String brandId) {
    	synchronized (WRITE_LOCK) {
	    	String[] param = getStringArray(brandId);
	        db.beginTransaction();
	        try {
	            db.delete("brands", "id = ?", param);
	            db.delete("pubs_brands", "brand_id", param);
	            db.delete("brands_tags", "brand_id = ?", param);
	            db.setTransactionSuccessful();
	        } finally {
	            db.endTransaction();
	        }
    	}
    }
    
    /* (non-Javadoc)
     * @see alaus.radaras.service.Beer#deletePub(java.lang.String)
     */
    @Override
    public void deletePub(String pubId) {
    	synchronized (WRITE_LOCK) {
	        String[] param = getStringArray(pubId);
	        db.beginTransaction();
	        try {
	            db.delete("pubs", "id = ?", param);
	            db.delete("pubs_brands", "pub_id = ?", param);
	            db.setTransactionSuccessful();
	        } finally {
	            db.endTransaction();
	        }
    	}
    }
    
    /* (non-Javadoc)
     * @see alaus.radaras.service.Beer#deleteCompany(java.lang.String)
     */
    @Override
    public void deleteCompany(String companyId) {
    	synchronized (WRITE_LOCK) {
	        List<Brand> brands = getBrandsByCompany(companyId);
	        db.beginTransaction();
	        try {
	            for (Brand brand : brands) {
	                deleteBrand(brand.getId());
	            }
	            db.delete("companies", "id = ?", getStringArray(companyId));         
	            db.setTransactionSuccessful();
	        } finally {
	            db.endTransaction();
	        }
    	}
    }

    @Override
    public Date getLastUpdate() {
        Date result = null;
        
        Cursor cursor = db.query("updates", getStringArray("date"), null, null, null, null, "date DESC", "1");

        if (cursor.moveToFirst()) {
            result = new Date(cursor.getLong(0));
        }

        return result;
    }

    @Override
    public void setLastUpdate(Date date) {
    	synchronized (WRITE_LOCK) {
            ContentValues contentValues = getContentValues();
            contentValues.put("date", date.getTime());
            db.insert("updates", null, contentValues);
		}
    }	
}
