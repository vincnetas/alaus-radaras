package alaus.radaras.client;

import java.util.List;

import alaus.radaras.shared.model.Pub;
import alaus.radaras.shared.model.UpdateRecord;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.ProvidesKey;

/**
 * @author Vincentas
 * 
 */
public class ApprovalPanel extends Composite {

	/**
	 * The key provider that allows us to identify Contacts even if a field
	 * changes. We identify contacts by their unique ID.
	 */
	private static final ProvidesKey<UpdateRecord<Pub>> KEY_PROVIDER = new ProvidesKey<UpdateRecord<Pub>>() {
		public Object getKey(UpdateRecord<Pub> item) {
			return item.getCurrent().getId();
		}
	};

	public ApprovalPanel() {
		final CellTable<UpdateRecord<Pub>> table = new CellTable<UpdateRecord<Pub>>(KEY_PROVIDER);
		table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		
		{
			final TextCell nameCell = new TextCell();
			Column<UpdateRecord<Pub>, String> nameColumn = new Column<UpdateRecord<Pub>, String>(nameCell) {
				@Override
				public String getValue(UpdateRecord<Pub> object) {
					return object.getCurrent().getTitle();
				}
			};
			table.addColumn(nameColumn, "Title");
		}
		
		{
			final TextCell statusCell = new TextCell();
			Column<UpdateRecord<Pub>, String> statusColumn = new Column<UpdateRecord<Pub>, String>(statusCell) {
				@Override
				public String getValue(UpdateRecord<Pub> object) {
					return "Approved : " + object.getCurrent().isApproved() + " Modified : " + object.getCurrent().isModified() + " Has updates : " + object.getUpdates().size();
				}
			};
			table.addColumn(statusColumn, "Status");
		}
		
		{
			final ButtonCell approveCell = new ButtonCell();
			Column<UpdateRecord<Pub>, String> approveColumn = new Column<UpdateRecord<Pub>, String>(approveCell) {
				@Override
				public String getValue(UpdateRecord<Pub> object) {
					return "approve";
				}
			};
			approveColumn.setFieldUpdater(new FieldUpdater<UpdateRecord<Pub>, String>() {
				
				@Override
				public void update(int index, UpdateRecord<Pub> object, String value) {
					String updateId;
					if (object.getUpdates().isEmpty()) {
						updateId = object.getCurrent().getId();
					} else {
						updateId = object.getUpdates().get(0).getId();
					}
					
					Stat.getAdminBeerService().applyUpdate(updateId, new BaseAsyncCallback<Pub>() {
						
						@Override
						public void onSuccess(Pub result) {
							Window.alert("Sukses");
						}
					});
				}
			});
			table.addColumn(approveColumn, "Approve");
		}
		
		{
			final ButtonCell rejectCell = new ButtonCell();
			Column<UpdateRecord<Pub>, String> rejectColumn = new Column<UpdateRecord<Pub>, String>(rejectCell) {
				@Override
				public String getValue(UpdateRecord<Pub> object) {
					return "reject";
				}
			};
			rejectColumn.setFieldUpdater(new FieldUpdater<UpdateRecord<Pub>, String>() {
				
				@Override
				public void update(int index, UpdateRecord<Pub> object, String value) {
					String updateId;
					if (object.getUpdates().isEmpty()) {
						updateId = object.getCurrent().getId();
					} else {
						updateId = object.getUpdates().get(0).getId();
					}
					
					Stat.getAdminBeerService().rejectUpdate(updateId, new BaseAsyncCallback<Pub>() {
						
						@Override
						public void onSuccess(Pub result) {
							Window.alert("Sukses");
						}
					});					
				}
			});
			table.addColumn(rejectColumn, "Reject");
		}
		
		Stat.getAdminBeerService().getPubUpdates(new BaseAsyncCallback<List<UpdateRecord<Pub>>>() {
			
			@Override
			public void onSuccess(List<UpdateRecord<Pub>> result) {
				table.setRowCount(result.size(), true);
				table.setRowData(result);
			}
		});
		
		initWidget(table);
	}
}