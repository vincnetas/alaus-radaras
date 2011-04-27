/**
 * 
 */
package alaus.radaras.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import alaus.radaras.R;
import alaus.radaras.shared.model.Beer;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * @author vienozin
 *
 */
public class UpdateTask extends AsyncTask<String, Integer, Integer> {

    private final Context context;
    
    private final BeerUpdate beerUpdate;
    
    private InputStream inputStream;
    
    private final ProgressHandler progressHandler;
    
    private Date lastUpdate;
    
    public UpdateTask(Context context, BeerUpdate beerUpdate) {
        this(context, beerUpdate, null);
    }
    
    public UpdateTask(Context context, BeerUpdate beerUpdate, ProgressHandler progressHandler) {
        this.context = context;
        this.beerUpdate = beerUpdate;
        this.progressHandler = progressHandler;
    }
    
    @Override
    protected Integer doInBackground(String... arg0) {
    	Integer result = null;
        
        if (arg0.length == 1) {
            String source = arg0[0];
            InputStream inputStream = asAsset(source);
            if (inputStream == null) {
                inputStream = asURL(source);
            }
            
            if (inputStream != null) {                
                try {
                    result = applyUpdate(inputStream);
                } catch (IOException e) {
                    /*
                     * Ignore
                     */
                }
            }
        }
                
        return result;
    }
    
    private InputStream asAsset(String asset) {
        InputStream result = null;
                
        try {
            result = context.getAssets().open(asset);
            lastUpdate = new SimpleDateFormat("yyyy-MM-dd").parse("2011-03-01");
        } catch (IOException e) {
            /*
             * Ignore
             */
        } catch (ParseException e) {
            /*
             * Ignore
             */
		}
        
        return result;
    }
    
    private InputStream asURL(String url) {
        String updateDate = "";        
        Date lastUpdate = beerUpdate.getLastUpdate();
        if (lastUpdate != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+00"));
            updateDate = "?lastUpdate=" + dateFormat.format(lastUpdate);
        }
        
        InputStream result = null; 
 
        HttpClient client = new DefaultHttpClient();
        HttpHost target = new HttpHost(url);
        HttpRequest request = new HttpGet("/json" + updateDate);
        
        try {
            HttpResponse response = client.execute(target, request);
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                result = response.getEntity().getContent();
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
	 * @return Returns number of updated/deleted elements
	 * @throws IOException
	 * 
	 */
    private int applyUpdate(InputStream data) throws IOException {
        Update update = Update.parse(data);
        int updateSize = update.getUpdateSize();        
        int processedItems = 0;
        
        for (Beer beer : update.getUpdatedBeers()) {
            beerUpdate.updateBrand(beer);
            processedItems++;
            publishProgress(processedItems, updateSize);
            if (isCancelled()) {
            	throw new IOException("Task canceled");
            }
        }
        
        for (alaus.radaras.shared.model.Brand brand : update.getUpdatedBrands()) {
            beerUpdate.updateCompany(brand);
            processedItems++;
            publishProgress(processedItems, updateSize);
            if (isCancelled()) {
            	throw new IOException("Task canceled");
            }
        }
        
        for (alaus.radaras.shared.model.Pub pub : update.getUpdatedPubs()) {
            beerUpdate.updatePub(pub);
            processedItems++;
            publishProgress(processedItems, updateSize);
            if (isCancelled()) {
            	throw new IOException("Task canceled");
            }
        }
        
        for (String beerId : update.getDeletedBeers()) {
            beerUpdate.deleteBrand(beerId);
            processedItems++;
            publishProgress(processedItems, updateSize);
            if (isCancelled()) {
            	throw new IOException("Task canceled");
            }
        }
        
        for (String brandId : update.getDeletedBrands()) {
            beerUpdate.deleteCompany(brandId);
            processedItems++;
            publishProgress(processedItems, updateSize);
            if (isCancelled()) {
            	throw new IOException("Task canceled");
            }
        }
        
        for (String pubId : update.getDeletedPubs()) {
            beerUpdate.deletePub(pubId);
            processedItems++;
            publishProgress(processedItems, updateSize);
            if (isCancelled()) {
            	throw new IOException("Task canceled");
            }
        }
        
        return updateSize;
    }

    /* (non-Javadoc)
     * @see android.os.AsyncTask#onCancelled()
     */
    @Override
    protected void onCancelled() {
        super.onCancelled();
        
        IOUtils.closeQuietly(inputStream);
    }
    
    /* (non-Javadoc)
     * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
     */
    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        
        IOUtils.closeQuietly(inputStream);
        
        if (result != null) {
            beerUpdate.setLastUpdate(lastUpdate != null ? lastUpdate : new Date());
            Toast.makeText(context, context.getResources().getString(R.string.update_complete, result), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, context.getResources().getString(R.string.update_failed), Toast.LENGTH_LONG).show();
        }
    }

    /* (non-Javadoc)
     * @see android.os.AsyncTask#onProgressUpdate(Progress[])
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        
        if (progressHandler != null) {
            progressHandler.progress(values[0], values[1], context.getResources().getString(R.string.update_importing_data, values[0], values[1]));
        }
    }
}

