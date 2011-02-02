/**
 * 
 */
package alaus.radaras.client;

import java.util.List;

import alaus.radaras.shared.model.Location;
import alaus.radaras.shared.model.LocationBounds;
import alaus.radaras.shared.model.Pub;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author Vincentas
 * 
 */
public class AdminPanel implements EntryPoint {


	public void onModuleLoad() {
		final CellTable<Pub> table = PubEdit.getTable();

		Stat.getBeerService().findPubs(new LocationBounds(new Location(10, 20), new Location(10, 20)), new BaseAsyncCallback<List<Pub>>() {
			@Override
			public void onSuccess(List<Pub> result) {
				table.setRowCount(result.size(), true);
				table.setRowData(result);
				
				if (result.size() == 0) {
					Window.alert("Empty result");
				}
			}
		});

		RootPanel.get().add(table);
	}
}