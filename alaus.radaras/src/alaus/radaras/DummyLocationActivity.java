package alaus.radaras;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class DummyLocationActivity  extends Activity  implements Observer {
	/** Called when the activity is first created. */
	LocationProvider locationProvider;
	ATMProvider atmProvider;
	
	private static final int DIALOG_WAITING = 1;
	private static final int CONTEXT_GET_DIRECTIONS = 1;
	private static final int CONTEXT_SHOW_MY_POSITION = 2;
	private static final int CONTEXT_SHOW_ON_MAP = 3;
	private static Boolean DEBUG = false;
	private OnItemClickListener listClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView parent, View v, int position,
				long id) {
			ViewHolder holder = (ViewHolder) v.getTag();
			String uri = "geo:0,0?q=" + holder.atmAddress;
			startActivity(new Intent(android.content.Intent.ACTION_VIEW, Uri
					.parse(uri)));

		}
	};

	private ATMProvider getATMProvider() {
		if (atmProvider == null) {
			atmProvider = new ATMProvider(getBaseContext());
		}
		return atmProvider;
	}

	private LocationProvider getLocationProvider() {
		if (locationProvider == null) {
			locationProvider = initLocationProvider();
		}
		return locationProvider;
	}

	private LocationProvider initLocationProvider() {
		LocationProvider provider = new LocationProvider(getBaseContext());
		return provider;
	}

	private void killLocationProvider() {
		if (locationProvider != null) {
			locationProvider.deleteObserver(this);
		}
		getLocationProvider().killAll();
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
	//	PendingIntent intent = PendingIntent.getActivity(getBaseContext(), 0, 
	//			new Intent(getIntent()), getIntent().getFlags());
	//	Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(intent, getBaseContext()));
		if(DEBUG) Log.d("AtmFinderActivity", "onCreate");
		getLocationProvider().subscribe(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		tryUpdate();
	};

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_WAITING: {
			ProgressDialog dialog = new ProgressDialog(this);
			dialog.setTitle(R.string.wait_header);
			dialog.setMessage(getString(R.string.waiting_coordinates));
			dialog.setIndeterminate(true);
			dialog.setCancelable(false);
			return dialog;
		}
		}
		return null;
	};

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

		if (v.getId() == R.id.list) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			
			ViewHolder holder =  (ViewHolder)info.targetView.getTag();
			menu.setHeaderTitle(holder.atmAddress);
			menu.add(Menu.NONE, CONTEXT_GET_DIRECTIONS, CONTEXT_GET_DIRECTIONS, "Gauti nuorodas");
			menu.add(Menu.NONE, CONTEXT_SHOW_MY_POSITION, CONTEXT_SHOW_MY_POSITION, "Kur að esu?");
			menu.add(Menu.NONE, CONTEXT_SHOW_ON_MAP, CONTEXT_SHOW_ON_MAP, "Rodyti þemëlapyje");
		}

	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	  AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
	  int menuItemIndex = item.getItemId();
	  ViewHolder holder =  (ViewHolder)info.targetView.getTag();
	  Location location = getLocationProvider().getLastKnownLocation();
	  
	  if(menuItemIndex  == CONTEXT_SHOW_ON_MAP) {
		  String uri = "geo:0,0?q=" + holder.atmAddress;
			startActivity(new Intent(android.content.Intent.ACTION_VIEW, Uri
					.parse(uri)));
	  }
	  else if(menuItemIndex  == CONTEXT_SHOW_MY_POSITION) {
		  String uri = "geo:"+location.getLatitude()+","+location.getLongitude();
			startActivity(new Intent(android.content.Intent.ACTION_VIEW, Uri
					.parse(uri)));
	  }
	  else if(menuItemIndex  == CONTEXT_GET_DIRECTIONS) {
		  Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
				  Uri.parse("http://maps.google.com/maps?saddr="+location.getLatitude()+","+location.getLongitude()+"&daddr="+holder.atmAddress));
		  startActivity(intent);
	  }
	  return true;
	}

	@Override
	protected void onPause() {
		killLocationProvider();
		super.onPause();
	}

	@Override
	protected void onResume() {
		if(DEBUG)  Log.d("AtmFinderActivity", "onResume");
		getLocationProvider().subscribe(this);
		tryUpdate();
		super.onResume();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	private void tryUpdate() {
		if(DEBUG) Log.d("AtmFinderActivity", "tryUpdate");
		showDialog(DIALOG_WAITING);
		updateDisplay(getLocationProvider().getLastKnownLocation());
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if(DEBUG)  Log.d("AtmFinderActivity", "received update");

		Location location = (Location) arg1;

		updateDisplay(location);
	};

	private void updateDisplay(Location location) {
		// TODO Auto-generated method stub
		if (location != null) {

			Toast.makeText(getBaseContext(),
					getString(R.string.renewing_display, location.getProvider()),
					Toast.LENGTH_LONG).show();

			List<ATM> allAtms = getATMProvider().calculateATMSDistance(
					location.getLatitude(), location.getLongitude());
			ListView list = (ListView) findViewById(R.id.list);
			if(allAtms != null) {
				list.setAdapter(new ATMListAdapter(getBaseContext(), allAtms
						.subList(0, 21)));
				list.setSelection(0);
				list.setOnItemClickListener(listClickListener);
				registerForContextMenu(list);
				// update TextView display
				TextView status = (TextView) findViewById(R.id.updateStatus);
				Time time = new Time();
				time.set(location.getTime());
				status
						.setText(getString(R.string.last_update,
										time.format("%G-%m-%d %H:%M:%S"), location
												.getProvider(), location
												.getAccuracy()));
				removeDialog(DIALOG_WAITING);
			}
		}
	}
}
