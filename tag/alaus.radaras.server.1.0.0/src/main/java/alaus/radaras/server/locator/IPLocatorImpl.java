/**
 * 
 */
package alaus.radaras.server.locator;

import alaus.radaras.shared.model.IPLocation;

/**
 * @author Vincentas Vienozinskis
 *
 */
public class IPLocatorImpl implements IPLocator {

	public IPLocation locate(String remoteAddr) {
		IPLocation result = new IPLocation();		
		result.setCity("Lithuania");
		result.setCountry("Vilnius");
		
//		try {
//			InputStream inputStream = new URL("http://www.geobytes.com/IpLocator.htm?GetLocation&template=json.txt&ipaddress=" + remoteAddr).openStream();
//			Map<String, IPLocation> res = new Gson().fromJson(new InputStreamReader(inputStream), new TypeToken<Map<String, IPLocation>>() {}.getType());
//
//			result = res.get("geobytes");
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		return result;
	}
}
