/**
 * 
 */
package alaus.radaras.client;

import alaus.radaras.client.ui.dialogs.EditDialog;
import alaus.radaras.client.ui.edit.EditPubWidget;
import alaus.radaras.shared.model.Location;
import alaus.radaras.shared.model.Pub;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.view.client.ProvidesKey;

/**
 * @author Vincentas
 * 
 */
public abstract class PubTable extends CellTable<Pub> {

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
	
	public PubTable() {
		super(KEY_PROVIDER);

		setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		
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
			addColumn(titleColumn, "Title");
		}
		
		{
			final EditTextCell countryCell = new EditTextCell();
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
			addColumn(countryColumn, "Country");
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
			addColumn(cityColumn, "City");
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
			addColumn(addressColumn, "Address");
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
			addColumn(locationColumn, "Location");
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
			addColumn(phoneColumn, "Phone");
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
			addColumn(homepageColumn, "Homepage");
		}
		
		{
			final ButtonCell saveCell = new ButtonCell();
			Column<Pub, String> saveColumn = new Column<Pub, String>(saveCell) {
				@Override
				public String getValue(Pub object) {
					return "Edit";
				}
			};
			saveColumn.setFieldUpdater(new FieldUpdater<Pub, String>() {
				
				@Override
				public void update(int arg0, Pub arg1, String arg2) {
					editPub(arg1);
				}

			});
			addColumn(saveColumn, "Edit");
		}
	}
	
	public abstract void editPub(Pub arg1);

}

