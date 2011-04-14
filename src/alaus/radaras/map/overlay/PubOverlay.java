/**
 * 
 */
package alaus.radaras.map.overlay;

import java.util.ArrayList;

import alaus.radaras.PubActivity;
import alaus.radaras.service.model.Pub;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import com.google.android.maps.MapView;
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
	
	@Override
	protected boolean onBalloonTap(int index) {
		PubOverlayItem pubOverlayItem = overlays.get(index);
		Pub pub = pubOverlayItem.getPub();
		
		Intent intent = new Intent(context, PubActivity.class);
		intent.putExtra(PubActivity.PUB_ID_PARAM, pub.getId());
		context.startActivity(intent);
		
		return true;
	}
}
