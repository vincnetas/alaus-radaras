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

/**
 * @author Vincentas
 *
 */
public class BeerRadarUpdate {

	public static Update update() {
		Update result = null;
		
		HttpClient client = new DefaultHttpClient();
		HttpHost target = new HttpHost("www.alausradaras.lt");
		HttpRequest request = new HttpGet("/json");
		try {
			HttpResponse response = client.execute(target, request);
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				InputStream content = response.getEntity().getContent();
				String string = IOUtils.toString(content);
				result = Update.parse(string);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
