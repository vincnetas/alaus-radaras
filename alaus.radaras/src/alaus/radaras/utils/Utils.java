/**
 * 
 */
package alaus.radaras.utils;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.SortedMap;
import java.util.TreeMap;

import alaus.radaras.R;
import alaus.radaras.dialogs.NewPubReportDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;

/**
 * @author Vincentas
 *
 */
public class Utils {
	
	public static final long SECOND = 1000;
	
	public static final long MINUTE = SECOND * 60;
	
	public static final long FIVE_MINUTES = MINUTE * 5;
	
	public static final long HOUR = MINUTE * 60;
	
	public static final long DAY = HOUR * 24;
	
	public static final Collator COLLARATOR = Collator.getInstance(new Locale("lt", "LT"));

	public static GeoPoint geoPoint(Location location) {
		return new GeoPoint((int) (location.getLatitude() * 1e6), (int) (location.getLongitude() * 1e6));
	}

	public static void showNoBeerAlert(Context context) {
		Toast.makeText(context, context.getString(R.string.no_beers), Toast.LENGTH_SHORT).show();
		
	}

	public static void showPubSubmitDialog(Context context,
			Location location) {
		NewPubReportDialog dialog = new NewPubReportDialog(context);
		dialog.display(location);		
	}
	
	public static void startActivity(Context context, Intent... intents) {
	    for (Intent intent : intents) {
            try {
                context.startActivity(intent);
                break;
            } catch (ActivityNotFoundException activityNotFoundException) {
                /*
                 * Ignore and try next activity
                 */
            }
        }
	}
	
	public static String translate(Context context, String value, String prefix) {
		String result = value;
		
		int identifier = context.getResources().getIdentifier("@string/" + prefix + "_" + value.toLowerCase(), "string", context.getPackageName());
		if (identifier != 0) {
			result = context.getResources().getString(identifier);
		}

		return result;
	}

	public static List<String> sortTranslation(Context context, List<String> values, String prefix) {
		SortedMap<String, String> map = new TreeMap<String, String>(new Comparator<String>() {

			@Override
			public int compare(String object1, String object2) {
				return COLLARATOR.compare(object1, object2);
			}
		});
				
		for (String value : values) {
			map.put(translate(context, value, prefix), value);
		}
		
		return new ArrayList<String>(map.values());
	}
	
	public static void setLanguage(Context context, Locale locale) {
		context.getResources().getDisplayMetrics();
		Configuration configuration = context.getResources().getConfiguration();
		configuration.locale = locale;
		context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());	
	}

}
