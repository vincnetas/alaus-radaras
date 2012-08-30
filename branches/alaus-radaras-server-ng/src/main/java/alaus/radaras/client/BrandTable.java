/**
 * 
 */
package alaus.radaras.client;

import alaus.radaras.shared.model.Brand;

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
public abstract class BrandTable extends CellTable<Brand> {

	private static ProvidesKey<Brand> keyProvider = new ProvidesKey<Brand>() {
		
		
		public Object getKey(Brand arg0) {
			return arg0.getId();
		}
	};
	
	public BrandTable() {
		super(keyProvider);
		
		addColumn(new TextColumn<Brand>() {
			
			
			public String getValue(Brand arg0) {				
				return arg0.getTitle();
			}
			
		}, "Title");
		
		addColumn(new TextColumn<Brand>() {
			
			
			public String getValue(Brand arg0) {				
				return arg0.getIcon();
			}
			
		}, "Icon");
		
		addColumn(new TextColumn<Brand>() {
			
			
			public String getValue(Brand arg0) {				
				return arg0.getHomePage();
			}
			
		}, "Homepage");
		
		addColumn(new TextColumn<Brand>() {
			
			
			public String getValue(Brand arg0) {				
				return arg0.getCountry();
			}
			
		}, "Country");
		
		addColumn(new TextColumn<Brand>() {
			
			
			public String getValue(Brand arg0) {				
				return arg0.getHometown();
			}
			
		}, "Hometown");
		
		addColumn(new TextColumn<Brand>() {
			
			
			public String getValue(Brand arg0) {				
				return arg0.getDescription();
			}
			
		}, "Description");
		
		addColumn(new TextColumn<Brand>() {
			
			
			public String getValue(Brand arg0) {				
				return arg0.getTagsAsString();
			}
			
		}, "Tags");		
		
		Column<Brand, String> saveColumn = new Column<Brand, String>(new ButtonCell()) {

			
			public String getValue(Brand arg0) {
				return "Edit";
			}
		};
		saveColumn.setFieldUpdater(new FieldUpdater<Brand, String>() {
			
			
			public void update(int arg0, Brand arg1, String arg2) {
				editBrand(arg1);
			}
		});
		
		addColumn(saveColumn, "Edit");
	}
	
	public abstract void editBrand(Brand brand);
}
