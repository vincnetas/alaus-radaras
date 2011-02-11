package alaus.radaras.submition;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import android.util.Log;

public class DataPublisher {
	
	
	public boolean submitPubCorrection(PubCorrection error) {
		 HttpParams p=new BasicHttpParams();
		 p.setParameter("type", "pubCorrection");
		 p.setParameter("reason", error.getReason().toString());
		 p.setParameter("pubId", error.getPubId());
		 p.setParameter("message", error.getMessage());
		 return postData(p,"http://alausradaras.lt/android/submit.php");	
	}

	private boolean postData(HttpParams p, String string) {
		  HttpClient httpclient = new DefaultHttpClient(p);
		    HttpPost httppost = new HttpPost("http://alausradaras.lt/android/submit.php");

		    try {
		        HttpResponse response = httpclient.execute(httppost);
		        return response.getStatusLine().getStatusCode() == 200;
		    } catch (Exception e) {

		        Log.e("BeerRadar", e.toString());
		        return false;
		    }
	}

	public boolean submitNewPub(NewPub newpub) {
		 HttpParams p=new BasicHttpParams();
		 p.setParameter("type", "newPub");
		 p.setParameter("text",newpub.getText());
		 p.setParameter("isNear", newpub.isNear());
		 if(newpub.getLocation() != null) {
			 p.setParameter("location.latitude", newpub.getLocation().getLatitude());
			 p.setParameter("location.longitude", newpub.getLocation().getLongitude());
			 p.setParameter("location.accuracy", newpub.getLocation().getAccuracy());
			 p.setParameter("location.provider", newpub.getLocation().getProvider());
		 }
		 return postData(p,"http://alausradaras.lt/android/submit.php");	
	}

}
