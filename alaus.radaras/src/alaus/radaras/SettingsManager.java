package alaus.radaras;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsManager {
	
	private SharedPreferences preferences;
	private Context context;
	public static final String PREFS_NAME = "BeerRadarPreference";

	public SettingsManager(Context context) {
		this.context = context;
	}
	
	public class Settings {
		
		public static final String SETTING_TOTAL_COUNT = "totalCount";
		public static final String SETTING_CURRENT_COUNT = "currentCount";
	}
	
	private String getValue(String name) {
		return getPreferences().getString(name,"");
	}
	
	private void storeValue(String name, String value) {
		SharedPreferences.Editor editor = getPreferences().edit();
		editor.putString(name, value);
		editor.commit();
	}
	

	private SharedPreferences getPreferences() {
		if(preferences == null) {
			preferences = context.getSharedPreferences(PREFS_NAME, 0);
		}
		return preferences;
	}

	public Integer getCurrentCount() {
		return getCount(Settings.SETTING_CURRENT_COUNT);
		
	}
	
	private Integer getCount(String settingName) {

		String value = this.getValue(settingName);
		if(value == null || value.length() == 0) {
			return 0;
		}
		return Integer.valueOf(value);
	}

	private Integer getTotalCount() {
		return getCount(Settings.SETTING_TOTAL_COUNT);
	}

	public void increaseCurrent() {
		Integer current = getCurrentCount();
		storeValue(Settings.SETTING_CURRENT_COUNT, (current++).toString());
		Integer total = getTotalCount();
		storeValue(Settings.SETTING_TOTAL_COUNT, (total++).toString());
		
	}



}
