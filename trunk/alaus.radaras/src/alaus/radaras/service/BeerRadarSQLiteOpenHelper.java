package alaus.radaras.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class BeerRadarSQLiteOpenHelper extends SQLiteOpenHelper {

	private static final String LOG_TAG = "beer-radar-db";
	
	private Context context;
	
	private boolean update = false;
	
	private static class Definition {
		
		public static String DB_NAME = "beer-radar";
		
		public static int DB_VERSION = 13;
		
		public static String PUBS = 
	        "CREATE TABLE pubs(" +
	        	"id 		TEXT PRIMARY KEY, " +
	        	"title 		TEXT NOT NULL, " +
	        	"longtitude REAL NOT NULL, " +
	        	"latitude 	REAL NOT NULL, " +
	        	"address 	TEXT, " +
	        	"city 		TEXT, " +
	        	"notes	 	TEXT, " +
	        	"phone	 	TEXT, " +
	        	"url	 	TEXT);";
		
		public static String COMPANY =
			"CREATE TABLE companies(" +
				"id				TEXT PRIMARY key, " +
				"title			TEXT NOT NULL, " + 
				"icon			TEXT, " +
				"homePage		TEXT, " +
				"country		TEXT, " +
				"hometown		TEXT, " +
				"description	TEXT);";
		
		public static String BRANDS = 
	        "CREATE TABLE brands(" +
	        	"id 			TEXT PRIMARY KEY, " +
	        	"title 			TEXT NOT NULL, " +
	        	"icon			TEXT, " +
	        	"description 	TEXT, " +
	        	"companyId		TEXT NOT NULL);";
				
		public static String PUBS_BRANDS = 
	        "CREATE TABLE pubs_brands(" +
	        	"pub_id			TEXT NOT NULL, " +
	        	"brand_id 		TEXT NOT NULL);";
				
		public static String TAGS = 
	        "CREATE TABLE tags(" +
	        	"code			TEXT PRIMARY KEY," +
	        	"title			TEXT NOT NULL);";
		
		public static String BRANDS_TAGS = 
	        "CREATE TABLE brands_tags(" +
	        	"brand_id			TEXT NOT NULL," +
	        	"tag				TEXT NOT NULL);";
		
		public static String QOUTES = 
	        "CREATE TABLE qoutes(" +
	        	"amount			INTEGER NOT NULL," +
	        	"text			TEXT NOT NULL);";
		
		public static String TAXI = 
	        "CREATE TABLE taxi(" +
	        	"title 		TEXT NOT NULL, " +
	        	"phone	 	TEXT, " +
	        	"city 		TEXT, " +
	        	"longitude REAL NOT NULL, " +
	        	"latitude 	REAL NOT NULL);";
		
		public static String UPDATES = 
		    "CREATE TABLE updates(" +
		        "date       INTEGER NOT NULL);";    
	}
	
    public BeerRadarSQLiteOpenHelper(Context context) 
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
    		db.execSQL(Definition.COMPANY);
	        db.execSQL(Definition.BRANDS);
	        db.execSQL(Definition.PUBS_BRANDS);
	        db.execSQL(Definition.BRANDS_TAGS);
	        db.execSQL(Definition.UPDATES);
	        
	        db.execSQL(Definition.TAGS);
	        db.execSQL(Definition.QOUTES);
	        db.execSQL(Definition.TAXI);
    	} catch (Exception e) {
    		Log.e(LOG_TAG, e.getMessage());
    	}
    	Log.i(LOG_TAG, "Finished creating database.");

    	updateData(db);
    }
    
    private void updateData(SQLiteDatabase db) {
    	Log.i(LOG_TAG, "Inserting initial data");
    	
    	update = true;
    	
    	insertTags(db);
    	insertQoutes(db);
    	insertTaxies(db);
    	
    	Log.i(LOG_TAG, "Finished inserting initial data");
    } 
    
	/**
	 * @return the update
	 */
	public boolean isUpdate() {
		return update;
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
	
	private void insertTaxies(SQLiteDatabase db) {
    	try {
    		BufferedReader reader = new BufferedReader(
    				new InputStreamReader(context.getAssets().open("taxi.txt")));
    		String line = null;
    		while ((line = reader.readLine()) != null) {
    			Log.i(LOG_TAG, "Inserting taxies: " + line);
    			String[] columns = line.split("\t");
    			ContentValues values = new ContentValues();
    			values.put("title", columns[0]);
    			values.put("phone", columns[1]);
    			values.put("city", columns[2]);
    			values.put("latitude", columns[3]);
    			values.put("longitude", columns[4]);
    			db.insert("taxi", null, values);
    		}
    	} catch (Exception e) {
    		Log.e(LOG_TAG, e.getMessage());
    	}		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(LOG_TAG, "Upgrading database from version " + oldVersion  + " to " + newVersion + ", which will destroy all old data");

        db.execSQL("DROP TABLE IF EXISTS pubs_brands;");
        db.execSQL("DROP TABLE IF EXISTS brands_tags;");
        db.execSQL("DROP TABLE IF EXISTS pubs;");
        db.execSQL("DROP TABLE IF EXISTS brands;");
        db.execSQL("DROP TABLE IF EXISTS companies;");
        db.execSQL("DROP TABLE IF EXISTS tags;");
        db.execSQL("DROP TABLE IF EXISTS qoutes;");
        db.execSQL("DROP TABLE IF EXISTS taxi;");
        db.execSQL("DROP TABLE IF EXISTS updates;");
        
        onCreate(db);
	}
}
