package alaus.radaras.settings;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsManager {
	
	
    public static final int DISTANCE_STEP = 500;
	public static final int UNLIMITED_DISTANCE = 1000001;
	
	private SharedPreferences preferences;
	private Context context;
	public static final String PREFS_NAME = "BeerRadarPreference";

	public SettingsManager(Context context) {
		this.context = context;
	}
	
	public class Settings {
		
		public static final String SETTING_TOTAL_COUNT = "totalCount";
		public static final String SETTING_CURRENT_COUNT = "currentCount";
		public static final String SETTING_MAX_DISTANCE = "maxDistance";
		public static final String SETTINGS_ASK_ENABLE_LOCATION_PROVIDER = "askEnableLocationProvider";
        public static final String SETTINGS_ASK_ENABLE_SYNCHRONIZATION = "askEnableSynchronization";
	}
	
	private void storeBool(String name, Boolean value) {
		SharedPreferences.Editor editor = getPreferences().edit();
		editor.putBoolean(name, value);
		editor.commit();
	}
	
	private void storeInt(String name, int value) {
		SharedPreferences.Editor editor = getPreferences().edit();
		editor.putInt(name, value);
		editor.commit();
	}
	
	@Deprecated
	private void storeString(String name, String value) {
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

		return getInt(settingName, 0);
	}

	@Deprecated()
	private Integer getInt(String settingName, int defaultValue) {
		
		String value = getPreferences().getString(settingName, "");
		if(value == null || value.length() == 0) {
			return defaultValue;
		} else {
			return Integer.valueOf(value);
		}
	}


	public Integer getTotalCount() {
		return getCount(Settings.SETTING_TOTAL_COUNT);
	}

	public void increaseCurrent() {
		Integer current = getCurrentCount();
		current++;
		storeString(Settings.SETTING_CURRENT_COUNT, current.toString());
		Integer total = getTotalCount();
		total++;
		storeString(Settings.SETTING_TOTAL_COUNT, total.toString());
		
	}

	public void resetCurrent() {
		storeString(Settings.SETTING_CURRENT_COUNT,"0");
		
	}

	public int getMaxDistance() {

		return getPreferences().getInt(Settings.SETTING_MAX_DISTANCE, 20000);
	}
	
	public void setMaxDistance(Integer maxDistanceInMeters) {
		storeInt(Settings.SETTING_MAX_DISTANCE, maxDistanceInMeters);
	}
	
	
	public boolean askEnableLocationProvider() {
		return getPreferences().getBoolean(Settings.SETTINGS_ASK_ENABLE_LOCATION_PROVIDER, true);
	}
	
	public boolean askEnableSynchronization() {
	    return getPreferences().getBoolean(Settings.SETTINGS_ASK_ENABLE_SYNCHRONIZATION, true);
	}
	
	public void setAskOnNoLocation(boolean doAsk) {
		storeBool(Settings.SETTINGS_ASK_ENABLE_LOCATION_PROVIDER, doAsk);
	}



}
