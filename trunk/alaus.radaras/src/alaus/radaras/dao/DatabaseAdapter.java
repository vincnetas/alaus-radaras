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
	        	"beer_id 		TEXT NOT NULL);";

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
    	} catch (Exception e) {
    		Log.e(LOG_TAG, e.getMessage());
    	}
    	Log.i(LOG_TAG, "Finished creating database.");
    	
    	Log.i(LOG_TAG, "Inserting initial data");
    	insertBrands(db);
    	insertPubs(db);
    	Log.i(LOG_TAG, "Finished inserting initial data");
    	
    }
    
    private void insertBrands(SQLiteDatabase db) {
    	try {
    		BufferedReader reader = new BufferedReader(
    				new InputStreamReader(context.getAssets().open("brands.txt")));
    		String line = null;
    		while ((line = reader.readLine()) != null) {
    			String[] columns = line.split(";");
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
    			String[] columns = line.split(";");
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

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(LOG_TAG, "Upgrading database from version " 
        		+ oldVersion  + " to " + newVersion + 
        		", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS pubs_brands;");
        db.execSQL("DROP TABLE IF EXISTS pubs;");
        db.execSQL("DROP TABLE IF EXISTS brands;");
        onCreate(db);
	}
}
