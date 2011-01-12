/**
 * 
 */
package alaus.radaras.server.locator;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import alaus.radaras.shared.model.IPLocation;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author Vincentas Vienozinskis
 *
 */
public class IPLocatorImpl implements IPLocator {

	@Override
	public IPLocation locate(String remoteAddr) {
		IPLocation result = new IPLocation();
		result.setCity("Helsinki");
		result.setCountry("Finland");
		
		try {
			InputStream inputStream = new URL("http://www.geobytes.com/IpLocator.htm?GetLocation&template=json.txt&ipaddress=" + remoteAddr).openStream();
			Map<String, IPLocation> res = new Gson().fromJson(new InputStreamReader(inputStream), new TypeToken<Map<String, IPLocation>>() {}.getType());

			result = res.get("geobytes");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
