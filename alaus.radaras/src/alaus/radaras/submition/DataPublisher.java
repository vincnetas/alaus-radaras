package alaus.radaras.submition;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.location.Location;
import android.util.Log;

public class DataPublisher {
	
	
	public boolean submitPubCorrection(PubCorrection error) {
		 List<NameValuePair> params = new ArrayList<NameValuePair>();

		 params.add(new BasicNameValuePair("type", "pubCorrection"));
		 params.add(new BasicNameValuePair("reason", error.getReason().toString()));
		 params.add(new BasicNameValuePair("pubId", error.getPubId()));
		 params.add(new BasicNameValuePair("message", error.getMessage()));
		 return postData(params,"http://alausradaras.lt/android/submit.php");	
	}

	private boolean postData(List<NameValuePair> params, String string) {
		  HttpClient httpclient = new DefaultHttpClient();
		    HttpPost httppost = new HttpPost("http://alausradaras.lt/android/submit.php");

		    try {
		    	httppost.setEntity(new UrlEncodedFormEntity(params));

		        HttpResponse response = httpclient.execute(httppost);
		        return response.getStatusLine().getStatusCode() == 200;
		    } catch (Exception e) {

		        Log.e("BeerRadar", e.toString());
		        return false;
		    }
	}

	public boolean submitNewPub(NewPub newpub) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		 params.add(new BasicNameValuePair("type", "newPub"));
		 params.add(new BasicNameValuePair("message",newpub.getMessage()));
		 params.add(new BasicNameValuePair("isNear", String.valueOf(newpub.isNear())));
		 addLocationToParams(newpub.getLocation(), params);
		
		 return postData(params,"http://alausradaras.lt/android/submit.php");	
	}
	
	private void addLocationToParams(Location location,
			List<NameValuePair> params) {
		 if(location != null) {
			 params.add(new BasicNameValuePair("location.latitude", String.valueOf(location.getLatitude())));
			 params.add(new BasicNameValuePair("location.longitude", String.valueOf(location.getLongitude())));
			 params.add(new BasicNameValuePair("location.accuracy", String.valueOf(location.getAccuracy())));
			 params.add(new BasicNameValuePair("location.provider", String.valueOf(location.getProvider())));
		 }
		
	}

	public boolean submitPubBrandInfo(PubBrandInfo pubBrandInfo) {
		 List<NameValuePair> params = new ArrayList<NameValuePair>();

		 params.add(new BasicNameValuePair("type", "pubBrandInfo"));
		 params.add(new BasicNameValuePair("status", pubBrandInfo.getStatus().toString()));
		 params.add(new BasicNameValuePair("brandId", pubBrandInfo.getBrandId()));
		 params.add(new BasicNameValuePair("pubId", pubBrandInfo.getPubId()));
		 params.add(new BasicNameValuePair("message", pubBrandInfo.getMessage()));
		 addLocationToParams(pubBrandInfo.getLocation(), params);
		 return postData(params,"http://alausradaras.lt/android/submit.php");	
	}

}
