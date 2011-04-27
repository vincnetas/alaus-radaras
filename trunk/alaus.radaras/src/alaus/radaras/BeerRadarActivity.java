package alaus.radaras;

//import alaus.radaras.service.BeerRadarUpdate;
import java.io.IOException;
import java.util.Date;

import org.svenson.tokenize.InputStreamSource;
import org.svenson.tokenize.JSONCharacterSource;
import org.svenson.tokenize.JSONTokenizer;

import alaus.radaras.parser.state.StartState;
import alaus.radaras.parser.state.State;
import alaus.radaras.service.BeerRadarSqlite;
import alaus.radaras.service.BeerUpdate;
import alaus.radaras.service.UpdateService;
import alaus.radaras.service.UpdateTask;
import alaus.radaras.settings.SettingsManager;
import alaus.radaras.utils.Utils;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class BeerRadarActivity extends AbstractLocationActivity {

    protected static final int UPDATE_DIALOG = 123;

    protected static final int LOCATION_DIALOG = 124;

    private SettingsManager settings;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                
        settings = new SettingsManager(getApplicationContext());
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.main);

        getCounterView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BeerRadarActivity.this, BeerCounterActivity.class));
            }
        });

        getAroundView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BeerRadarActivity.this, PubLocationActivity.class));
            }
        });

        getSearchView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BeerRadarActivity.this, BrandTabWidget.class));
            }
        });

        getLuckyView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BeerRadarActivity.this, LuckyActivity.class));
            }
        });

        checkIfLocationIsEnabled();
        checkIfBackgroundDataIsEnabled();

//        BeerUpdate beerUpdate = new BeerRadarSqlite(this);
//        Date lastUpdate = beerUpdate.getLastUpdate();
//        if (lastUpdate == null) {
            new UpdateTask(this, new BeerRadarSqlite(this)).execute("data.json");
//        }

    }

    private void checkIfBackgroundDataIsEnabled() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (settings.askEnableSynchronization() && !connectivityManager.getBackgroundDataSetting()) {
            showDialog(UPDATE_DIALOG);
        }
    }

    private void checkIfLocationIsEnabled() {
        if (settings.askEnableLocationProvider() && ((BeerRadarApp) getApplication()).getLocationProvider().getAvailableProvidersCount() == 0) {
            showDialog(LOCATION_DIALOG);
        }
    }

    private View getAroundView() {
        return findViewById(R.id.around);
    }

    private View getSearchView() {
        return findViewById(R.id.search);
    }

    private View getLuckyView() {
        return findViewById(R.id.lucky);
    }

    private View getCounterView() {
        return findViewById(R.id.counter);
    }

    @Override
    public void onResume() {
        final SettingsManager settings = new SettingsManager(this);
        TextView counter = (TextView) findViewById(R.id.mainCounterCurrent);
        counter.setText(settings.getTotalCount().toString());

        super.onResume();
    }

    @Override
    protected void locationUpdated(Location location) {
        // do nothing. we just need this to get location fix asap.
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreateDialog(int)
     */
    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog result = null;

        if (id == UPDATE_DIALOG) {
        	result = genericEnableDialog(
        			SettingsManager.Settings.SETTINGS_ASK_ENABLE_SYNCHRONIZATION, 
        			R.string.sync_disabled_message, 
        			R.string.sync_disabled_header,
        			new Intent(Settings.ACTION_SYNC_SETTINGS), new Intent(Settings.ACTION_SETTINGS));
        } else if (id == LOCATION_DIALOG) {
        	result = genericEnableDialog(
        			SettingsManager.Settings.SETTINGS_ASK_ENABLE_LOCATION_PROVIDER, 
        			R.string.location_disabled_message, 
        			R.string.location_disabled_header,
        			new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }

        return result;
    }
    
    private Dialog genericEnableDialog(final String keepAsking, int messageId, int titleId, final Intent... intents) {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.generic_enable_request, null);

        final TextView textView = (TextView) layout.findViewById(R.id.tvGenericEnableMesage);       
        textView.setText(messageId);
        
        final CheckBox checkBox = (CheckBox) layout.findViewById(R.id.cbGenericStopAsking);
        checkBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Editor editor = settings.getPreferences().edit();
				editor.putBoolean(keepAsking, !isChecked);
				editor.commit();
			}
		});
        
        return new AlertDialog.Builder(this).
            setPositiveButton(R.string.generic_enable_yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Utils.startActivity(BeerRadarActivity.this, intents); 
                }
            }).
            setNegativeButton(R.string.generic_enable_no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).
            setView(layout).
            setTitle(titleId).
            create();            
    }

    /*
     * (non-Javadoc)
     * 
     * @see alaus.radaras.AbstractLocationActivity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see alaus.radaras.AbstractLocationActivity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.update:
        	startService(new Intent(this, UpdateService.class));
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}