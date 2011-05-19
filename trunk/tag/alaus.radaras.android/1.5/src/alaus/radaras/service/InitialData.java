/**
 * 
 */
package alaus.radaras.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.io.IOUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.DatabaseUtils.InsertHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * @author vienozin
 *
 */
public class InitialData {

    private static final String LOG_TAG = "beer-radar-db";
    
    public static void importInitialData(SQLiteDatabase db, Context context) {
        try {
            insertBrands(db, new BufferedReader(new InputStreamReader(context.getAssets().open("brands.txt"))));
            insertPubs(db, new BufferedReader(new InputStreamReader(context.getAssets().open("pubs.txt"))));
            insertBeers(db, new BufferedReader(new InputStreamReader(context.getAssets().open("beers.txt"))));
            insertQoutes(db, new BufferedReader(new InputStreamReader(context.getAssets().open("qoutes.txt"))));
            insertTaxies(db, new BufferedReader(new InputStreamReader(context.getAssets().open("taxi.txt"))));
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }
    }
    
    private static void insertBrands(SQLiteDatabase db, BufferedReader reader) {
        DatabaseUtils.InsertHelper companies = new InsertHelper(db, BeerRadarSQLiteOpenHelper.COMPANIES);
        
        try {
            String line = null;
            ContentValues values = new ContentValues();
                        
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split("\t");
            
                values.clear();
                values.put("id", columns[0]);
                values.put("title", columns[1]);
                values.put("country", columns[2]);
                
                companies.insert(values);
            }           
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(reader);
            
            companies.close();
        }        
    }
    
    private static void insertBeers(SQLiteDatabase db, BufferedReader reader) {
        DatabaseUtils.InsertHelper brands = new InsertHelper(db, BeerRadarSQLiteOpenHelper.BRANDS);
        DatabaseUtils.InsertHelper pubs_brands = new InsertHelper(db, BeerRadarSQLiteOpenHelper.PUBS_BRANDS);
        DatabaseUtils.InsertHelper brands_tags = new InsertHelper(db, BeerRadarSQLiteOpenHelper.BRANDS_TAGS);
        
        try {
            String line = null;
            ContentValues values = new ContentValues();
            
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split("\t");

                values.clear();
                values.put("id", columns[0]);
                values.put("title", columns[2]);
                values.put("icon", columns[1]);                
                values.put("companyId", columns[5]);
                brands.insert(values);
                
                String[] pubs = columns[3].split(",");
                for (int i = 0; i < pubs.length; i++) {
                    values.clear();
                    values.put("brand_id", columns[0]);
                    values.put("pub_id", pubs[i].trim());
                    pubs_brands.insert(values);                 
                }
                
                String[] tags = columns[4].split(",");
                for (int i = 0; i < tags.length; i++) {
                    values.clear();
                    values.put("brand_id", columns[0]);
                    values.put("tag", tags[i].trim());
                    brands_tags.insert(values);                 
                }                
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(reader);
            
            brands.close();
            pubs_brands.close();
            brands_tags.close();
        }      
    }
    
    private static void insertPubs(SQLiteDatabase db, BufferedReader reader) {
        DatabaseUtils.InsertHelper pubs = new InsertHelper(db, BeerRadarSQLiteOpenHelper.PUBS);
        
        try {
            String line = null;
            ContentValues values = new ContentValues();
            
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split("\t");
            
                values.clear();
                values.put("id", columns[0]);
                values.put("title", columns[1]);
                values.put("address", columns[2]);
                values.put("city", columns[3]);
                values.put("phone", columns[4]);
                values.put("url", columns[5]);
                values.put("latitude", columns[6]);
                values.put("longtitude", columns[7]);
                pubs.insert(values);
            }           
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(reader);
            
            pubs.close();
        }
    }

    private static void insertQoutes(SQLiteDatabase db, BufferedReader reader) {
        DatabaseUtils.InsertHelper qoutes = new InsertHelper(db, BeerRadarSQLiteOpenHelper.QOUTES);
        
        try {   
            ContentValues values = new ContentValues();
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split("\t");

                values.clear();
                values.put("amount", columns[0]);
                values.put("text", columns[1]);                
                qoutes.insert(values);
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(reader);
            
            qoutes.close();
        }
    }
    
    private static void insertTaxies(SQLiteDatabase db, BufferedReader reader) {
        DatabaseUtils.InsertHelper taxi = new InsertHelper(db, BeerRadarSQLiteOpenHelper.TAXI);
        
        try {            
            String line = null;
            ContentValues values = new ContentValues();
            
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split("\t");
            
                values.clear();
                values.put("title", columns[0]);
                values.put("phone", columns[1]);
                values.put("city", columns[2]);
                values.put("latitude", columns[3]);
                values.put("longitude", columns[4]);                
                taxi.insert(values);
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(reader);
            
            taxi.close();
        }
    }
}
