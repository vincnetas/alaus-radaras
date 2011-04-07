/**
 * 
 */
package alaus.radaras.service;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import alaus.radaras.shared.model.Beer;
import alaus.radaras.shared.model.Brand;
import alaus.radaras.shared.model.Pub;
import alaus.radaras.shared.model.Quote;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

/**
 * @author Vincentas
 *
 */
public class Update {
	
	private static final String UPDATE = "update";
	
	private static final String DELETE = "delete";
	
	private static final String BEERS = "beers";
	
	private static final String BRANDS = "brands";
	
	private static final String PUBS = "pubs";

	private Set<Pub> updatedPubs;
	
	private Set<String> deletedPubs;
	
	private Set<Beer> updatedBeers;
	
	private Set<String> deletedBeers;
	
	private Set<Brand> updatedBrands;
	
	private Set<String> deletedBrands;
	
	private Set<Quote> updatedQuotes;
	
	private Set<String> deteledQuotes;

	public static Update parse(InputStream inputStream) throws IOException {
		return parse(IOUtils.toString(inputStream));
	}
	
	public static Update parse(String json) {
		System.out.println(json);
		Type type = new TypeToken<Map<String, Map<String, MyData>>>() {}.getType();
		Map<String, Map<String, MyData>> o = getGson().fromJson(json, type);
		
		Update result = new Update();

		String[] deletedBeers = getGson().fromJson(o.get(DELETE).get(BEERS).getElement(), new TypeToken<String>() {}.getType());
		String[] deletedBrands = getGson().fromJson(o.get(DELETE).get(BRANDS).getElement(), new TypeToken<String[]>() {}.getType());
		String[] deletedPubs = getGson().fromJson(o.get(DELETE).get(PUBS).getElement(), new TypeToken<String[]>() {}.getType());

		Beer[] updatedBeers = getGson().fromJson(o.get(UPDATE).get(BEERS).getElement(), new TypeToken<Beer[]>() {}.getType());
		Brand[] updatedBrands = getGson().fromJson(o.get(UPDATE).get(BRANDS).getElement(), new TypeToken<Brand[]>() {}.getType());
		Pub[] updatedPubs = getGson().fromJson(o.get(UPDATE).get(PUBS).getElement(), new TypeToken<Pub[]>() {}.getType());
				
		result.setDeletedBeers(asSet(deletedBeers));
		result.setDeletedBrands(asSet(deletedBrands));
		result.setDeletedPubs(asSet(deletedPubs));
		
		result.setUpdatedBeers(asSet(updatedBeers));
		result.setUpdatedBrands(asSet(updatedBrands));
		result.setUpdatedPubs(asSet(updatedPubs));
		
		return result;		
	}
	
	private static <T> Set<T> asSet(T... values) {
		return new HashSet<T>(Arrays.asList(values));
	}

	
	public static String toJson(Update update) {
		return getGson().toJson(update.param());
	}
	
	private static Gson gson = null;
	
	private static synchronized Gson getGson() {
		if (gson == null) {
			gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
	
				private Set<String> fieldsToSkip = asSet("lastUpdate", "modified", "approved", "updatedBy", "jdoDetachedState");
				
				@Override
				public boolean shouldSkipField(FieldAttributes f) {
					return fieldsToSkip.contains(f.getName());
				}
				
				@Override
				public boolean shouldSkipClass(Class<?> clazz) {
					return false;
				}
			}).registerTypeAdapter(MyData.class, new MyData()).
			create();
			
			
		}
		
		return gson;
	}
	
	private Map<String, Map<String, Object[]>> param() {
		Map<String, Map<String, Object[]>> result = new HashMap<String, Map<String,Object[]>>();
		
		result.put(DELETE, getObjectsToDelete());
		result.put(UPDATE, getObjectsToUpdate());
		
		return result;
	}
	
	private Map<String, Object[]> getObjectsToDelete() {
		Map<String, Object[]> result = new HashMap<String, Object[]>();
		
		result.put(BEERS, getDeletedBeers().toArray());
		result.put(BRANDS, getDeletedBrands().toArray());
		result.put(PUBS, getDeletedPubs().toArray());
		
		return result;
	}
	
	private Map<String, Object[]> getObjectsToUpdate() {
		Map<String, Object[]> result = new HashMap<String, Object[]>();
		
		result.put(BEERS, getUpdatedBeers().toArray());
		result.put(BRANDS, getUpdatedBrands().toArray());
		result.put(PUBS, getUpdatedPubs().toArray());
		
		return result;
	}

	/**
	 * @return the updatedPubs
	 */
	public Set<Pub> getUpdatedPubs() {
		return updatedPubs;
	}

	/**
	 * @param updatedPubs the updatedPubs to set
	 */
	public void setUpdatedPubs(Set<Pub> updatedPubs) {
		this.updatedPubs = updatedPubs;
	}

	/**
	 * @return the deletedPubs
	 */
	public Set<String> getDeletedPubs() {
		return deletedPubs;
	}

	/**
	 * @param deletedPubs the deletedPubs to set
	 */
	public void setDeletedPubs(Set<String> deletedPubs) {
		this.deletedPubs = deletedPubs;
	}

	/**
	 * @return the updatedBeers
	 */
	public Set<Beer> getUpdatedBeers() {
		return updatedBeers;
	}

	/**
	 * @param updatedBeers the updatedBeers to set
	 */
	public void setUpdatedBeers(Set<Beer> updatedBeers) {
		this.updatedBeers = updatedBeers;
	}

	/**
	 * @return the deletedBeers
	 */
	public Set<String> getDeletedBeers() {
		return deletedBeers;
	}

	/**
	 * @param deletedBeers the deletedBeers to set
	 */
	public void setDeletedBeers(Set<String> deletedBeers) {
		this.deletedBeers = deletedBeers;
	}

	/**
	 * @return the updatedBrands
	 */
	public Set<Brand> getUpdatedBrands() {
		return updatedBrands;
	}

	/**
	 * @param updatedBrands the updatedBrands to set
	 */
	public void setUpdatedBrands(Set<Brand> updatedBrands) {
		this.updatedBrands = updatedBrands;
	}

	/**
	 * @return the deletedBrands
	 */
	public Set<String> getDeletedBrands() {
		return deletedBrands;
	}

	/**
	 * @param deletedBrands the deletedBrands to set
	 */
	public void setDeletedBrands(Set<String> deletedBrands) {
		this.deletedBrands = deletedBrands;
	}

	/**
	 * @return the updatedQuotes
	 */
	public Set<Quote> getUpdatedQuotes() {
		return updatedQuotes;
	}

	/**
	 * @param updatedQuotes the updatedQuotes to set
	 */
	public void setUpdatedQuotes(Set<Quote> updatedQuotes) {
		this.updatedQuotes = updatedQuotes;
	}

	/**
	 * @return the deteledQuotes
	 */
	public Set<String> getDeteledQuotes() {
		return deteledQuotes;
	}

	/**
	 * @param deteledQuotes the deteledQuotes to set
	 */
	public void setDeteledQuotes(Set<String> deteledQuotes) {
		this.deteledQuotes = deteledQuotes;
	}
}

class MyData implements JsonDeserializer<MyData> {

	private JsonElement element;
	
	@Override
	public MyData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		MyData result = new MyData();

		result.setElement(json);
		
		return result;
	}

	/**
	 * @return the element
	 */
	public JsonElement getElement() {
		return element;
	}

	/**
	 * @param element the element to set
	 */
	public void setElement(JsonElement element) {
		this.element = element;
	}
}
