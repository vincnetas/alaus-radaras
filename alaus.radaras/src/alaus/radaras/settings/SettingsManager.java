package alaus.radaras.settings;

import java.util.Date;
import java.util.Locale;

import alaus.radaras.utils.Utils;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

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
        public static final String SETTINGS_LAST_UPDATE = "lastUpdate";
        public static final String SETTINGS_LAST_UPDATE_ATTEMPT = "lastUpdateAttempt";
		public static final String SETTINGS_LANGUAGE = "language";
	}
	
    private void storeBool(String name, Boolean value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putBoolean(name, value);
        editor.commit();
    }

    private void storeLong(String name, Long value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putLong(name, value);
        editor.commit();
    }
	
	private void storeInt(String name, int value) {
		SharedPreferences.Editor editor = getPreferences().edit();
		editor.putInt(name, value);
		editor.commit();
	}
	
	private void storeString(String name, String value) {
		SharedPreferences.Editor editor = getPreferences().edit();
		editor.putString(name, value);
		editor.commit();
	}

	public SharedPreferences getPreferences() {
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

    
    /**
     * Returns date when last update was performed (successful finished).
     * 
     * @return Returns last update date or null if update was never performed.
     */
    public Date getLastUpdate() {
        Date result = null;
        
        long lastUpdate = getPreferences().getLong(Settings.SETTINGS_LAST_UPDATE, 0);
        if (lastUpdate != 0) {
            result = new Date(lastUpdate);
        }
        
        return result;        
    }
    
    /**
     * Sets date of last successful update.
     * 
     * @param date Date when update finished
     */
    public void setLastUpdate(Date date) {
        storeLong(Settings.SETTINGS_LAST_UPDATE, date.getTime());
    }

	public Date getLastUpdateAttempt() {
        Date result = null;
        
        long lastUpdate = getPreferences().getLong(Settings.SETTINGS_LAST_UPDATE_ATTEMPT, 0);
        if (lastUpdate != 0) {
            result = new Date(lastUpdate);
        }
        
        return result;   
	}

	public void setLastUpdateAttempt(Date date) {
		storeLong(Settings.SETTINGS_LAST_UPDATE_ATTEMPT, date.getTime());		
	}

	public void setLanguage(String language) {				
		storeString(Settings.SETTINGS_LANGUAGE, language);		
	}

	public String getLanguage() {
		return getPreferences().getString(Settings.SETTINGS_LANGUAGE, null);
	}

	public void updateLanguage(Context context) {
		String language = getLanguage();
		
		if (language != null) {
			Toast.makeText(context, language, Toast.LENGTH_SHORT).show();
			Utils.setLanguage(context, new Locale(language));
		} else {
			Toast.makeText(context, "no lang", Toast.LENGTH_SHORT).show();
		}
	}
}
