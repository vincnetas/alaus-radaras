package alaus.radaras.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class BeerRadarSQLiteOpenHelper extends SQLiteOpenHelper {

	private static final String LOG_TAG = "beer-radar-db";
	
	private Context context;
	
	private static class Definition {
		
		public static String DB_NAME = "beer-radar";
		
		public static int DB_VERSION = 14;
		
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
	
    public BeerRadarSQLiteOpenHelper(Context context) {
        super(context, Definition.DB_NAME, null, Definition.DB_VERSION);
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

        Log.i(LOG_TAG, "Inserting initial data");
        InitialData.importInitialData(db, context);     
        Log.i(LOG_TAG, "Finished inserting initial data");
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
