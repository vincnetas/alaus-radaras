package alaus.radaras.service.model;

import android.content.Context;

public class Country extends MultipleLocation {

	private String code;
	
	public Country() {
		
	}
	
	public Country(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return code;
	}
	
	public String getName(Context context) {
		String result = code;
		
		int identifier = context.getResources().getIdentifier("@string/country_" + code.toLowerCase(), "string", context.getPackageName());
		if (identifier != 0) {
			result = context.getResources().getString(identifier);
		}
		
		return result;
	}
}
