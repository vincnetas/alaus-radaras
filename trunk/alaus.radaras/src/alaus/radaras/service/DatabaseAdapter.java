package alaus.radaras.dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseAdapter extends SQLiteOpenHelper {

	private static final String LOG_TAG = "beer-radar-db";
	
	private Context context;
	
	private static class Definition {
		
		public static String DB_NAME = "beer-radar";
		
		public static int DB_VERSION = 1;
		
		public static String PUBS = 
	        "CREATE TABLE pubs(" +
	        	"id 		TEXT PRIMARY KEY, " +
	        	"title 		TEXT NOT NULL, " +
	        	"longtitude REAL NOT NULL, " +
	        	"latitude 	REAL NOT NULL, " +
	        	"address 	TEXT, " +
	        	"notes	 	TEXT, " +
	        	"phone	 	TEXT, " +
	        	"url	 	TEXT);";
		
		public static String BRANDS = 
	        "CREATE TABLE brands(" +
	        	"id 			TEXT PRIMARY KEY, " +
	        	"title 			TEXT NOT NULL, " +
	        	"icon			TEXT, " +
	        	"description 	TEXT);";
		
		public static String PUBS_BRANDS = 
	        "CREATE TABLE pubs_brands(" +
	        	"pub_id			TEXT NOT NULL, " +
	        	"brand_id 		TEXT NOT NULL);";
		
		public static String COUNTRIES = 
	        "CREATE TABLE countries(" +
	        	"code			TEXT NOT NULL," + 
	        	"name			TEXT NOT NULL);";
		
		public static String BRANDS_COUNTRIES = 
	        "CREATE TABLE brands_countries(" +
	        	"brand_id			TEXT NOT NULL," +
	        	"country			TEXT NOT NULL);";
		
		public static String TAGS = 
	        "CREATE TABLE tags(" +
	        	"code			TEXT NOT NULL," +
	        	"title			TEXT NOT NULL);";
		
		public static String BRANDS_TAGS = 
	        "CREATE TABLE brands_tags(" +
	        	"brand_id			TEXT NOT NULL," +
	        	"tag				TEXT NOT NULL);";
		
		public static String QOUTES = 
	        "CREATE TABLE qoutes(" +
	        	"amount			INTEGER NOT NULL," +
	        	"text			TEXT NOT NULL);";
	}
	
    public DatabaseAdapter(Context context) 
    {
        super(
    		context, 
    		Definition.DB_NAME, 
    		null, 
    		Definition.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) 
    {
    	Log.i(LOG_TAG, "Creating initial database");
    	try {
	        db.execSQL(Definition.PUBS);
	        db.execSQL(Definition.BRANDS);
	        db.execSQL(Definition.PUBS_BRANDS);
	        db.execSQL(Definition.COUNTRIES);
	        db.execSQL(Definition.BRANDS_COUNTRIES);
	        db.execSQL(Definition.TAGS);
	        db.execSQL(Definition.BRANDS_TAGS);
	        db.execSQL(Definition.QOUTES);
    	} catch (Exception e) {
    		Log.e(LOG_TAG, e.getMessage());
    	}
    	Log.i(LOG_TAG, "Finished creating database.");
    	
    	Log.i(LOG_TAG, "Inserting initial data");
    	insertBrands(db);
    	insertPubs(db);
    	insertTags(db);
    	insertCountries(db);
    	insertQoutes(db);
    	insertAssociations(db);
    	Log.i(LOG_TAG, "Finished inserting initial data");
    	
    }

	private void insertBrands(SQLiteDatabase db) {
    	try {
    		BufferedReader reader = new BufferedReader(
    				new InputStreamReader(context.getAssets().open("brands.txt")));
    		String line = null;
    		while ((line = reader.readLine()) != null) {
    			String[] columns = line.split("\t");
    			Log.i(LOG_TAG, "Inserting brand: " + columns[0]);
    			ContentValues values = new ContentValues();
    			values.put("id", columns[0]);
    			values.put("title", columns[1]);
    			values.put("icon", columns[0]);
    			db.insert("brands", null, values);
    		}
    	} catch (Exception e) {
    		Log.e(LOG_TAG, e.getMessage());
    	}
    }
    
    private void insertPubs(SQLiteDatabase db) {
    	try {
    		BufferedReader reader = new BufferedReader(
    				new InputStreamReader(context.getAssets().open("pubs.txt")));
    		String line = null;
    		while ((line = reader.readLine()) != null) {
    			String[] columns = line.split("\t");
    			Log.i(LOG_TAG, "Inserting pub: " + columns[0]);
    			ContentValues values = new ContentValues();
    			values.put("id", columns[0]);
    			values.put("title", columns[1]);
    			values.put("address", columns[2]);
    			values.put("phone", columns[3]);
    			values.put("url", columns[4]);
    			values.put("latitude", columns[5]);
    			values.put("longtitude", columns[6]);
    			db.insert("pubs", null, values);
    		}    		
    	} catch (Exception e) {
    		Log.e(LOG_TAG, e.getMessage());
    	}
    }
    
    private void insertAssociations(SQLiteDatabase db) {
    	try {
    		BufferedReader reader = new BufferedReader(
    				new InputStreamReader(context.getAssets().open("brands.txt")));
    		String line = null;
    		
    		while ((line = reader.readLine()) != null) {
    			
    			String[] columns = line.split("\t");
    			
    			Log.i(LOG_TAG, "Inserting brand<->pub association: " + columns[0]);
    			String[] pubs = columns[2].split(",");
    			for (int i = 0; i < pubs.length; i++) {
        			ContentValues values = new ContentValues();
        			values.put("brand_id", columns[0]);
        			values.put("pub_id", pubs[i].trim());
        			db.insert("pubs_brands", null, values);    				
    			}
    			
    			Log.i(LOG_TAG, "Inserting brand<->country association: " + columns[0]);
    			String[] countries = columns[3].split(",");
    			for (int i = 0; i < countries.length; i++) {
        			ContentValues values = new ContentValues();
        			values.put("brand_id", columns[0]);
        			values.put("country", countries[i].trim());
        			db.insert("brands_countries", null, values);    				
    			}
    			
    			Log.i(LOG_TAG, "Inserting brand<->tag association: " + columns[0]);
    			String[] tags = columns[4].split(",");
    			for (int i = 0; i < tags.length; i++) {
        			ContentValues values = new ContentValues();
        			values.put("brand_id", columns[0]);
        			values.put("tag", tags[i].trim());
        			db.insert("brands_tags", null, values);    				
    			}
    			
    		}
    	} catch (Exception e) {
    		Log.e(LOG_TAG, e.getMessage());
    	}
    }

	private void insertQoutes(SQLiteDatabase db) {
    	try {
    		BufferedReader reader = new BufferedReader(
    				new InputStreamReader(context.getAssets().open("qoutes.txt")));
    		String line = null;
    		while ((line = reader.readLine()) != null) {
    			String[] columns = line.split("\t");
    			Log.i(LOG_TAG, "Inserting qoute: " + columns[0]);
    			ContentValues values = new ContentValues();
    			values.put("amount", columns[0]);
    			values.put("text", columns[1]);
    			db.insert("qoutes", null, values);
    		}
    	} catch (Exception e) {
    		Log.e(LOG_TAG, e.getMessage());
    	}
	}

    private void insertCountries(SQLiteDatabase db) {
    	try {
    		BufferedReader reader = new BufferedReader(
    				new InputStreamReader(context.getAssets().open("countries.txt")));
    		String line = null;
    		while ((line = reader.readLine()) != null) {
    			Log.i(LOG_TAG, "Inserting country: " + line);
    			String[] columns = line.split("\t");
    			ContentValues values = new ContentValues();
    			values.put("code", columns[0]);
    			values.put("name", columns[1]);
    			db.insert("countries", null, values);
    		}
    	} catch (Exception e) {
    		Log.e(LOG_TAG, e.getMessage());
    	}
	}

	private void insertTags(SQLiteDatabase db) {
    	try {
    		BufferedReader reader = new BufferedReader(
    				new InputStreamReader(context.getAssets().open("tags.txt")));
    		String line = null;
    		while ((line = reader.readLine()) != null) {
    			Log.i(LOG_TAG, "Inserting tags: " + line);
    			String[] columns = line.split("\t");
    			ContentValues values = new ContentValues();
    			values.put("code", columns[0]);
    			values.put("title", columns[1]);
    			db.insert("tags", null, values);
    		}
    	} catch (Exception e) {
    		Log.e(LOG_TAG, e.getMessage());
    	}		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(LOG_TAG, "Upgrading database from version " 
        		+ oldVersion  + " to " + newVersion + 
        		", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS pubs_brands;");
        db.execSQL("DROP TABLE IF EXISTS pubs;");
        db.execSQL("DROP TABLE IF EXISTS brands;");
        db.execSQL("DROP TABLE IF EXISTS tags;");
        db.execSQL("DROP TABLE IF EXISTS countries;");
        onCreate(db);
	}
}
