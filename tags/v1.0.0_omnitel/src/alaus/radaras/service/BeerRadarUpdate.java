/**
 * 
 */
package alaus.radaras.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


import alaus.radaras.service.model.Brand;
import alaus.radaras.service.model.Tag;
import alaus.radaras.shared.model.Beer;
import alaus.radaras.shared.model.Pub;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author Vincentas
 *
 */
public class BeerRadarUpdate {

	public static Update update() {
		Update result = null;
		
		HttpClient client = new DefaultHttpClient();
		HttpHost target = new HttpHost("www.alausradaras.lt");
		HttpRequest request = new HttpGet("/json");
		try {
			HttpResponse response = client.execute(target, request);
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				result = Update.parse(response.getEntity().getContent());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	/**
	 * This method applies update from input stream to data base.
	 * 
	 * @param data
	 *            Input stream containing update information in JSON format
	 * @param db
	 *            DB to store updated information
	 * @throws IOException 
	 */
	public static void applyUpdate(InputStream data, BeerRadar dao, SQLiteDatabase db) throws IOException {
		Update update = Update.parse(data);
		UpdateDB updateDB = new UpdateDB(dao, db);
		
		for (Beer beer : update.getUpdatedBeers()) {
			updateDB.updateBrand(beer);
		}
		
		for (alaus.radaras.shared.model.Brand brand : update.getUpdatedBrands()) {
			updateDB.updateCompany(brand);
		}
		
		for (alaus.radaras.shared.model.Pub pub : update.getUpdatedPubs()) {
			updateDB.updatePub(pub);
		}
		
		for (String beerId : update.getDeletedBeers()) {
			updateDB.deleteBrand(beerId);
		}
		
		for (String brandId : update.getDeletedBrands()) {
			updateDB.deleteCompany(brandId);
		}
		
		for (String pubId : update.getDeletedPubs()) {
			updateDB.deletePub(pubId);
		}
	}		
}

class UpdateDB {
	
	private final BeerRadar dao;
	
	private final SQLiteDatabase db;
	
	public UpdateDB(BeerRadar dao, SQLiteDatabase db) {
		this.db = db;
		this.dao = dao;
	}

	/**
	 * Updates brand in DB.
	 * 
	 * @param beer
	 *            Brand to update
	 */
	public void updateBrand(Beer beer) {
        ContentValues beerValues = new ContentValues();
        beerValues.put("id", beer.getId());
        beerValues.put("title", beer.getTitle());
        beerValues.put("icon", beer.getIcon());
        beerValues.put("description", beer.getDescription());
        beerValues.put("companyId", beer.getBrandId());
        
        db.beginTransaction();
        try {
			db.replace("brands", null, beerValues );
			
			Set<Tag> brandTags = dao.getBrandTags(beer.getId());
			for (Tag tag : brandTags) {
				if (!beer.getTags().remove(tag.getCode())) {
					db.delete("brands_tags", "brand_id = ? AND tag = ?", new String[] {beer.getId(), tag.getCode()});
				}
			}
			
			for (String tag : beer.getTags()) {
				ContentValues tagValues = new ContentValues();
				tagValues.put("brand_id", beer.getId());
				tagValues.put("tag", tag);
				db.insert("brand_tags", null, tagValues);
			}
			
			db.setTransactionSuccessful();
        } finally {
        	db.endTransaction();
        }
	}

	/**
	 * Updates pub in DB.
	 * 
	 * @param pub Pub to update
	 */
	public void updatePub(Pub pub) {
		ContentValues pubValues = new ContentValues();
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
			db.replace("pubs", null, pubValues);
			
			/*
			 * Delete removed brands from pub association
			 */
			List<Brand> pubBrands = dao.getBrandsByPubId(pub.getId());
			for (Brand brand : pubBrands) {
				if (!pub.getBeerIds().remove(brand.getId())) {
					db.delete("pubs_brands", "pub_id = ? AND brand_id = ?", new String[] {pub.getId(), brand.getId()});
				}
			}
			
			/*
			 * Add new brands to pub association
			 */
			for (String brandId : pub.getBeerIds()) {
				ContentValues brandValues = new ContentValues();
				brandValues.put("brand_id", brandId);
				brandValues.put("pub_id", pub.getId());
				db.insert("pubs_brands", null, brandValues);
			}
			
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}
	
	/**
	 * Updates company information in DB.
	 * 
	 * @param brand
	 *            Company data
	 */
	public void updateCompany(alaus.radaras.shared.model.Brand brand) {
		ContentValues companyValues = new ContentValues();
		companyValues.put("country", brand.getCountry());
		companyValues.put("description", brand.getDescription());
		companyValues.put("homePage", brand.getHomePage());
		companyValues.put("hometown", brand.getHometown());
		companyValues.put("icon", brand.getIcon());
		companyValues.put("id", brand.getId());
		companyValues.put("title", brand.getTitle());

		db.replace("companies", null, companyValues);
	}
	
	/**
	 * Deletes brand, association with pubs and tags.
	 * 
	 * @param brandId
	 *            Id of brand to delete
	 */
	public void deleteBrand(String brandId) {
		String[] param = new String[] {brandId};
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
	
	/**
	 * Deletes pub and associations with beers from DB.
	 * 
	 * @param pubId
	 *            Id of pub to delete
	 */
	public void deletePub(String pubId) {
		String[] param = new String[] {pubId};
		db.beginTransaction();
		try {
			db.delete("pubs", "id = ?", param);
			db.delete("pubs_brands", "pub_id = ?", param);
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}
	
	/**
	 * Deletes company, associated brands (see deleteBrand).
	 * 
	 * @param companyId
	 *            Id of company to delete
	 */
	public void deleteCompany(String companyId) {
		List<Brand> brands = dao.getBrandsByCompany(companyId);
		db.beginTransaction();
		try {
			for (Brand brand : brands) {
				deleteBrand(brand.getId());
			}
			db.delete("companies", "id = ?", new String[] {companyId});			
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}	
}


