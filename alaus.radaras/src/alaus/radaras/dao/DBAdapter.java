package lt.lb.rates;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter 
{
    public static final String KEY_CURRENCY = "currency";
    public static final String KEY_NAME = "name";
    public static final String KEY_RATE = "rate";
    public static final String KEY_DATE = "date";    
    private static final String TAG = "DBAdapter";
    
    private static final String DATABASE_NAME = "lbrates";
    private static final String DATABASE_TABLE_CURRENCIES = "currencies";
    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_CREATE =
        "create table currencies (currency text primary key, "
        + "name text not null, rate real not null, " 
        + "date integer not null);";
        
    private final Context context; 
    
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx) 
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }
        
    private static class DatabaseHelper extends SQLiteOpenHelper 
    {
        DatabaseHelper(Context context) 
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) 
        {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, 
        int newVersion) 
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion 
                    + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS currencies");
            onCreate(db);
        }
    }    
    
    //---opens the database---
    public DBAdapter open() throws SQLException 
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---    
    public void close() 
    {
        DBHelper.close();
    }
    
    //---insert a title into the database---
    public long insertCurrency(String currency, String name, double rate, long date) 
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_CURRENCY, currency);
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_RATE, rate);
        initialValues.put(KEY_DATE, date);
        return db.insert(DATABASE_TABLE_CURRENCIES, null, initialValues);
    }

    //---deletes a particular title---
    public boolean deleteCurrency(String currency) 
    {
        return db.delete(DATABASE_TABLE_CURRENCIES, KEY_CURRENCY + 
        		"= '" + currency + "'", null) > 0;
    }

    //---retrieves all the titles---
    public Cursor getAllCurrencies() 
    {
        return db.query(DATABASE_TABLE_CURRENCIES, new String[] {
        		KEY_CURRENCY, 
        		KEY_NAME,
        		KEY_RATE,
                KEY_DATE}, 
                null, 
                null, 
                null, 
                null, 
                KEY_CURRENCY + " ASC");
    }

    //---retrieves a particular title---
    public Cursor getCurrency(String currency) throws SQLException 
    {
    	Cursor mCursor = null;
    	
        mCursor =
                db.query(true, DATABASE_TABLE_CURRENCIES, new String[] {
                		KEY_CURRENCY,
                		KEY_NAME, 
                		KEY_RATE,
                		KEY_DATE
                		}, 
                		KEY_CURRENCY + "= '" + currency + "'", 
                		null,
                		null, 
                		null, 
                		null, 
                		null);
    	
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public long existCurrency(String currency) throws SQLException 
    {
        Cursor mCursor =
                db.rawQuery("SELECT count(*) FROM " + DATABASE_TABLE_CURRENCIES + " WHERE " + KEY_CURRENCY + "= '" + currency + "'", null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor.getLong(0);
    }

    //---updates a title---
    public boolean updateCurrency(String currency, String name, 
    double rate, long date) 
    {
        ContentValues args = new ContentValues();
        args.put(KEY_NAME, name);
        args.put(KEY_RATE, rate);
        args.put(KEY_DATE, date);
        return db.update(DATABASE_TABLE_CURRENCIES, args, 
        		KEY_CURRENCY + "= '" + currency + "'", null) > 0;
    }
}