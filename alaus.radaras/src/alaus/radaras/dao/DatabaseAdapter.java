package alaus.radaras.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseAdapter extends SQLiteOpenHelper {

	private static final String LOG_TAG = "beer-radar-db";
	
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
