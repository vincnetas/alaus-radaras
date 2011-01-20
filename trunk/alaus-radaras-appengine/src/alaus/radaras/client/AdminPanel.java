/**
 * 
 */
package alaus.radaras.client;

import java.util.List;

import alaus.radaras.shared.model.Pub;
import alaus.radaras.shared.model.UpdateRecord;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.ProvidesKey;

/**
 * @author Vincentas
 * 
 */
public class AdminPanel implements EntryPoint {

	/**
	 * The key provider that allows us to identify Contacts even if a field
	 * changes. We identify contacts by their unique ID.
	 */
	private static final ProvidesKey<UpdateRecord<Pub>> KEY_PROVIDER = new ProvidesKey<UpdateRecord<Pub>>() {
		public Object getKey(UpdateRecord<Pub> item) {
			return item.getCurrent().getId();
		}
	};

	public void onModuleLoad() {
		final CellTable<UpdateRecord<Pub>> table = new CellTable<UpdateRecord<Pub>>(KEY_PROVIDER);
		table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		
		final TextCell nameCell = new TextCell();
		Column<UpdateRecord<Pub>, String> nameColumn = new Column<UpdateRecord<Pub>, String>(nameCell) {
			@Override
			public String getValue(UpdateRecord<Pub> object) {
				return object.getCurrent().getTitle();
			}
		};
		table.addColumn(nameColumn, "Title");
		
		Stat.getAdminBeerService().getPubUpdates(new BaseAsyncCallback<List<UpdateRecord<Pub>>>() {
			
			@Override
			public void onSuccess(List<UpdateRecord<Pub>> result) {
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