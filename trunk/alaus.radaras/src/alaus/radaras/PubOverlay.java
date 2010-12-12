/**
 * 
 */
package alaus.radaras;

import java.util.ArrayList;

import alaus.radaras.dao.model.Pub;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.Projection;
import com.readystatesoftware.mapviewballoons.BalloonItemizedOverlay;

/**
 * @author Vincentas
 * 
 */
public class PubOverlay extends BalloonItemizedOverlay<PubOverlayItem> {

	private ArrayList<PubOverlayItem> overlays = new ArrayList<PubOverlayItem>();

	private final Context context;

	public PubOverlay(Drawable defaultMarker, Context context, MapView mapView) {
		super(boundCenterBottom(defaultMarker), mapView);
		this.context = context;
	}

	public void addOverlay(PubOverlayItem overlay) {
		overlays.add(overlay);
		populate();
	}
	
	public void clean() {
		overlays.clear();
		populate();
	}

	@Override
	protected PubOverlayItem createItem(int i) {
		return overlays.get(i);
	}

	@Override
	public int size() {
		return overlays.size();
	}

//	@Override
//	protected boolean onTap(int index) {
//		PubOverlayItem pubOverlayItem = overlays.get(index);
//		Pub pub = pubOverlayItem.getPub();
//		
//		Intent intent = new Intent(context, PubActivity.class);
//		intent.putExtra(PubActivity.PUBID, pub.getId());
//		context.startActivity(intent);
//
//		return true;
//	}
	
	@Override
	protected boolean onBalloonTap(int index) {
		Toast.makeText(context, "onBalloonTap for overlay index " + index, Toast.LENGTH_LONG).show();
		return true;
	}
}
