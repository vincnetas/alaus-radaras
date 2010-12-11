/**
 * 
 */
package alaus.radaras;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;

/**
 * @author Vincentas
 * 
 */
public class PubOverlay extends ItemizedOverlay<PubOverlayItem> {

	private ArrayList<PubOverlayItem> overlays = new ArrayList<PubOverlayItem>();

	private final Context context;

	public PubOverlay(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		this.context = context;
	}

	public void addOverlay(PubOverlayItem overlay) {
		overlays.add(overlay);
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
	protected boolean onTap(int index) {
		PubOverlayItem pubOverlayItem = overlays.get(index);
		
		Intent intent = new Intent(context, PubActivity.class);
		intent.putExtra(PubActivity., value)
		context.startActivity(intent);
		
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle(pubOverlayItem.getTitle());
		dialog.setMessage(pubOverlayItem.getSnippet());
		dialog.show();
		return true;
	}
}
