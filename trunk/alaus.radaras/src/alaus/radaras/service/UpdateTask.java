/**
 * 
 */
package alaus.radaras.service;

import java.io.IOException;
import java.io.InputStream;

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

import alaus.radaras.shared.model.Beer;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * @author vienozin
 *
 */
public class UpdateTask extends AsyncTask<String, Integer, Boolean> {

    private final Context context;
    
    private final BeerUpdate beerUpdate;
    
    private InputStream inputStream;
    
    private ProgressBar progressBar;
    
    public UpdateTask(Context context, BeerUpdate beerUpdate, ProgressBar progressBar) {
        this.context = context;
        this.beerUpdate = beerUpdate;
        this.progressBar = progressBar;
    }
    
    @Override
    protected Boolean doInBackground(String... arg0) {
        boolean result = false;
        
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
        } catch (IOException e) {
            /*
             * Ignore
             */
        }
        
        return result;
    }
    
    private static InputStream asURL(String url) {
        InputStream result = null; 
 
        HttpClient client = new DefaultHttpClient();
        HttpHost target = new HttpHost(url);
        HttpRequest request = new HttpGet("/json");
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
     * @throws IOException 
     */
    private boolean applyUpdate(InputStream data) throws IOException {
        Update update = Update.parse(data);
        int updateSize = update.getUpdateSize();        
        int processedItems = 0;
        
        for (Beer beer : update.getUpdatedBeers()) {
            beerUpdate.updateBrand(beer);
            processedItems++;
            publishProgress(processedItems, updateSize);
            if (isCancelled()) {
                return false;
            }
        }
        
        for (alaus.radaras.shared.model.Brand brand : update.getUpdatedBrands()) {
            beerUpdate.updateCompany(brand);
            processedItems++;
            publishProgress(processedItems, updateSize);
            if (isCancelled()) {
                return false;
            }
        }
        
        for (alaus.radaras.shared.model.Pub pub : update.getUpdatedPubs()) {
            beerUpdate.updatePub(pub);
            processedItems++;
            publishProgress(processedItems, updateSize);
            if (isCancelled()) {
                return false;
            }
        }
        
        for (String beerId : update.getDeletedBeers()) {
            beerUpdate.deleteBrand(beerId);
            processedItems++;
            publishProgress(processedItems, updateSize);
            if (isCancelled()) {
                return false;
            }
        }
        
        for (String brandId : update.getDeletedBrands()) {
            beerUpdate.deleteCompany(brandId);
            processedItems++;
            publishProgress(processedItems, updateSize);
            if (isCancelled()) {
                return false;
            }
        }
        
        for (String pubId : update.getDeletedPubs()) {
            beerUpdate.deletePub(pubId);
            processedItems++;
            publishProgress(processedItems, updateSize);
            if (isCancelled()) {
                return false;
            }
        }
        
        return true;
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
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        
        IOUtils.closeQuietly(inputStream);
        progressBar.setVisibility(View.INVISIBLE);
        
        if (result) {
            Toast.makeText(context, "Update complete", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Update failure", Toast.LENGTH_SHORT).show();
        }
    }

    /* (non-Javadoc)
     * @see android.os.AsyncTask#onProgressUpdate(Progress[])
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        
        progressBar.setProgress(values[0]);
        progressBar.setMax(values[1]);        
    }

    /* (non-Javadoc)
     * @see android.os.AsyncTask#onPreExecute()
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        
        Toast.makeText(context, "Loading data", Toast.LENGTH_SHORT).show();
        
        progressBar.setProgress(0);
        progressBar.setVisibility(View.VISIBLE);
    }
    
    

}

