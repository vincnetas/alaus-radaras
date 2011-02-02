/**
 * 
 */
package alaus.radaras.client;

import alaus.radaras.shared.model.Location;
import alaus.radaras.shared.model.Pub;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextInputCell;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Window;
import com.google.gwt.view.client.ProvidesKey;

/**
 * @author Vincentas
 * 
 */
public class PubEdit {

	private static final ProvidesKey<Pub> KEY_PROVIDER = new ProvidesKey<Pub>() {
		public Object getKey(Pub item) {
			return valueOrDefault(item.getId());
		}
	};
	
	private static String valueOrDefault(String value) {
		if (value == null) {
			value = "undefined";
		}
		
		return value;
	}
	
	public static CellTable<Pub> getTable() {
		final CellTable<Pub> table = new CellTable<Pub>(KEY_PROVIDER);
		table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		
		{
			final EditTextCell titleCell = new EditTextCell();
			Column<Pub, String> titleColumn = new Column<Pub, String>(titleCell) {
				@Override
				public String getValue(Pub object) {					
					return valueOrDefault(object.getTitle());
				}
			};
			titleColumn.setFieldUpdater(new FieldUpdater<Pub, String>() {				
				@Override
				public void update(int arg0, Pub arg1, String arg2) {
					arg1.setTitle(arg2);
				}
			});
			table.addColumn(titleColumn, "Title");
		}
		
		{
			final TextInputCell countryCell = new TextInputCell();
			Column<Pub, String> countryColumn = new Column<Pub, String>(countryCell) {
				@Override
				public String getValue(Pub object) {
					return valueOrDefault(object.getCountry());
				}
			};
			countryColumn.setFieldUpdater(new FieldUpdater<Pub, String>() {				
				@Override
				public void update(int arg0, Pub arg1, String arg2) {					
					arg1.setCountry(arg2);
				}
			});
			table.addColumn(countryColumn, "Country");
		}
		
		{
			final EditTextCell cityCell = new EditTextCell();
			Column<Pub, String> cityColumn = new Column<Pub, String>(cityCell) {
				@Override
				public String getValue(Pub object) {
					return valueOrDefault(object.getCity());
				}
			};
			cityColumn.setFieldUpdater(new FieldUpdater<Pub, String>() {				
				@Override
				public void update(int arg0, Pub arg1, String arg2) {
					arg1.setCity(arg2);
				}
			});
			table.addColumn(cityColumn, "City");
		}
		
		{
			final EditTextCell addressCell = new EditTextCell();
			Column<Pub, String> addressColumn = new Column<Pub, String>(addressCell) {
				@Override
				public String getValue(Pub object) {
					return valueOrDefault(object.getAddress());
				}
			};
			addressColumn.setFieldUpdater(new FieldUpdater<Pub, String>() {				
				@Override
				public void update(int arg0, Pub arg1, String arg2) {
					arg1.setAddress(arg2);
				}
			});
			table.addColumn(addressColumn, "Address");
		}		
		
		{
			Column<Pub, Location> locationColumn = new Column<Pub, Location>(new LocationCell()) {
				@Override
				public Location getValue(Pub object) {
					return object.getLocation();
				}
			};
			locationColumn.setFieldUpdater(new FieldUpdater<Pub, Location>() {				
				@Override
				public void update(int arg0, Pub arg1, Location arg2) {
					arg1.setLocation(arg2);
				}
			});
			table.addColumn(locationColumn, "Location");
		}
		
		{
			final EditTextCell phoneCell = new EditTextCell();
			Column<Pub, String> phoneColumn = new Column<Pub, String>(phoneCell) {
				@Override
				public String getValue(Pub object) {
					return valueOrDefault(object.getPhone());
				}
			};
			phoneColumn.setFieldUpdater(new FieldUpdater<Pub, String>() {				
				@Override
				public void update(int arg0, Pub arg1, String arg2) {
					arg1.setPhone(arg2);
				}
			});
			table.addColumn(phoneColumn, "Phone");
		}
		
		{
//			final EditTextCell descriptionCell = new EditTextCell();
//			Column<Pub, String> descriptionColumn = new Column<Pub, String>(descriptionCell) {
//				@Override
//				public String getValue(Pub object) {
//					return valueOrDefault(object.getDescription());
//				}
//			};
//			descriptionColumn.setFieldUpdater(new FieldUpdater<Pub, String>() {				
//				@Override
//				public void update(int arg0, Pub arg1, String arg2) {
//					arg1.setDescription(arg2);
//				}
//			});
//			table.addColumn(descriptionColumn, "Description");
		}
		
		{
			final EditTextCell homepageCell = new EditTextCell();
			Column<Pub, String> homepageColumn = new Column<Pub, String>(homepageCell) {
				@Override
				public String getValue(Pub object) {
					return valueOrDefault(object.getHomepage());
				}
			};
			homepageColumn.setFieldUpdater(new FieldUpdater<Pub, String>() {				
				@Override
				public void update(int arg0, Pub arg1, String arg2) {
					arg1.setHomepage(arg2);
				}
			});
			table.addColumn(homepageColumn, "Homepage");
		}
		
		{
			final ButtonCell saveCell = new ButtonCell();
			Column<Pub, String> saveColumn = new Column<Pub, String>(saveCell) {
				@Override
				public String getValue(Pub object) {
					return "Save";
				}
			};
			saveColumn.setFieldUpdater(new FieldUpdater<Pub, String>() {
				
				@Override
				public void update(int arg0, Pub arg1, String arg2) {
					Window.alert("saved");
				}
			});
			table.addColumn(saveColumn, "Save");
		}
		
		return table;
	}
	

}

