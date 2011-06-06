/**
 * 
 */
package alaus.radaras;

import alaus.radaras.settings.SettingsManager;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * @author vienozin
 *
 */
public class SelectLanguageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				
		setContentView(R.layout.select_language);
		final SettingsManager settingsManager = new SettingsManager(this);
		
		/*
		 * Default language in case user exits activity without selecting
		 * anything
		 */
		settingsManager.setLanguage(SettingsActivity.LANGUAGE_LT);
		
		findViewById(R.id.language_lt_button).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				settingsManager.setLanguage(SettingsActivity.LANGUAGE_LT);
				finish();
			}
		});
		
		findViewById(R.id.language_gb_button).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				settingsManager.setLanguage(SettingsActivity.LANGUAGE_EN);
				finish();
			}
		});
	}
	
}
