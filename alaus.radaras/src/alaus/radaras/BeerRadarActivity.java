package alaus.radaras;

//import alaus.radaras.service.BeerRadarUpdate;
import java.util.Date;

import alaus.radaras.service.BeerRadarSqlite;
import alaus.radaras.service.BeerUpdate;
import alaus.radaras.service.UpdateService;
import alaus.radaras.settings.SettingsManager;
import alaus.radaras.utils.Utils;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.ProgressBar;
import android.widget.TextView;

public class BeerRadarActivity extends AbstractLocationActivity {

    protected static final int UPDATE_DIALOG = 123;

    protected static final int LOCATION_DIALOG = 124;

	protected static final int UPDATE_IN_PROGRESS = 0;

    private SettingsManager settings;
    
    private BeerUpdate beerUpdate;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			ProgressBar updateProgress = (ProgressBar) findViewById(R.id.updateProgress);
			if (intent.getExtras().getBoolean(UpdateService.UPDATE_STATUS)) {
				updateProgress.setVisibility(View.VISIBLE);
			} else {
				updateProgress.setVisibility(View.INVISIBLE);
			}
		}
		
	};
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                
        beerUpdate = new BeerRadarSqlite(this);
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
        
        getProgressView().setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDialog(UPDATE_IN_PROGRESS);
				
			}
		});

        checkIfLocationIsEnabled();
        checkIfBackgroundDataIsEnabled();
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
    
    private View getProgressView() {
        return findViewById(R.id.updateProgress);
    }

    @Override
    public void onResume() {
    	super.onResume();
    	
    	registerReceiver(broadcastReceiver, new IntentFilter(UpdateService.UPDATE_STATUS));
    	
        Date lastUpdate = beerUpdate.getLastUpdate();
        if (lastUpdate == null) {
            Intent intent = new Intent(this, UpdateService.class);
            intent.putExtra(UpdateService.UPDATE_SOURCE, "data.json");

        	startService(intent);
        }

        final SettingsManager settings = new SettingsManager(this);
        TextView counter = (TextView) findViewById(R.id.mainCounterCurrent);
        counter.setText(settings.getTotalCount().toString());
    }
    
    /* (non-Javadoc)
	 * @see alaus.radaras.AbstractLocationActivity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();		
    	unregisterReceiver(broadcastReceiver);
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

        switch (id) {
		case UPDATE_DIALOG: {
        	result = genericEnableDialog(
        			SettingsManager.Settings.SETTINGS_ASK_ENABLE_SYNCHRONIZATION, 
        			R.string.sync_disabled_message, 
        			R.string.sync_disabled_header,
        			new Intent(Settings.ACTION_SYNC_SETTINGS), new Intent(Settings.ACTION_SETTINGS));
			break;
		}
		case LOCATION_DIALOG: {
        	result = genericEnableDialog(
        			SettingsManager.Settings.SETTINGS_ASK_ENABLE_LOCATION_PROVIDER, 
        			R.string.location_disabled_message, 
        			R.string.location_disabled_header,
        			new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        	break;
		}
		case UPDATE_IN_PROGRESS : {
			result = new AlertDialog.Builder(this).
				setPositiveButton("Gerai", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();						
					}
				}).
				setMessage("Kantrybės, vyksta duomenų atnaujinimas...").
				create();
			break;
		}
		default:
			break;
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
    	boolean result = true;

		switch (item.getItemId()) {
		case R.id.update: {
			Intent intent = new Intent(this, UpdateService.class);
			intent.putExtra(UpdateService.UPDATE_SOURCE, "www.alausradaras.lt");

			startService(intent);
			break;
		}
		default: {
			result = super.onOptionsItemSelected(item);
		}
		}
        
        return result;
    }
}