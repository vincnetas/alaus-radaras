package alaus.radaras;

import alaus.radaras.settings.SettingsManager;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SettingsActivity extends Activity {

	private SettingsManager settings;

	private static final int[] distances = new int[] { 500, 2000, 10000, SettingsManager.UNLIMITED_DISTANCE };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		settings = new SettingsManager(this);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.settings);

		Spinner distanceSpinner = (Spinner) findViewById(R.id.settingsDistance);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.settings_search_distance_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		distanceSpinner.setAdapter(adapter);
		distanceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
				settings.setMaxDistance(distances[position]);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				settings.setMaxDistance(2000);
			}
		});

		distanceSpinner.setSelection(getSelectedIndex());
	}

	private int getSelectedIndex() {
		int distance = settings.getMaxDistance();
		for (int i = 0; i < distances.length; i++) {
			if (distances[i] == distance) {
				return i;
			}
		}

		return 1;
	}
}
