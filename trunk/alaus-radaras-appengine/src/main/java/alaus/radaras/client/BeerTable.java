/**
 * 
 */
package alaus.radaras.client;

import alaus.radaras.shared.model.Beer;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.ProvidesKey;

/**
 * @author Vincentas
 *
 */
public abstract class BeerTable extends CellTable<Beer> {

	private static ProvidesKey<Beer> keyProvider = new ProvidesKey<Beer>() {
		
		@Override
		public Object getKey(Beer arg0) {
			return arg0.getId();
		}
	};
	
	public BeerTable() {
		super(keyProvider);
		
		addColumn(new TextColumn<Beer>() {
			
			@Override
			public String getValue(Beer arg0) {				
				return arg0.getTitle();
			}
			
		}, "Title");
		
		addColumn(new TextColumn<Beer>() {
			
			@Override
			public String getValue(Beer arg0) {				
				return arg0.getBrandId();
			}
			
		}, "Brans");
		
		addColumn(new TextColumn<Beer>() {
			
			@Override
			public String getValue(Beer arg0) {				
				return arg0.getIcon();
			}
			
		}, "Icon");	

		addColumn(new TextColumn<Beer>() {
			
			@Override
			public String getValue(Beer arg0) {				
				return arg0.getDescription();
			}
			
		}, "Description");
		
		addColumn(new TextColumn<Beer>() {
			
			@Override
			public String getValue(Beer arg0) {				
				return arg0.getTagsAsString();
			}
			
		}, "Tags");		
		
		Column<Beer, String> saveColumn = new Column<Beer, String>(new ButtonCell()) {

			@Override
			public String getValue(Beer arg0) {
				return "Edit";
			}
		};
		saveColumn.setFieldUpdater(new FieldUpdater<Beer, String>() {
			
			@Override
			public void update(int arg0, Beer arg1, String arg2) {
				editBeer(arg1);
			}
		});
		
		addColumn(saveColumn, "Edit");
	}
	
	public abstract void editBeer(Beer beer);
}
