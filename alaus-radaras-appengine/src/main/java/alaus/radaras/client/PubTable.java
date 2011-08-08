/**
 * 
 */
package alaus.radaras.client;

import alaus.radaras.shared.model.Pub;

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

			addColumn(new TextColumn<Pub>() {
				
				public String getValue(Pub object) {
					return valueOrDefault(object.getTitle());
				}
			}, "Title");
		}

		{

			addColumn(new TextColumn<Pub>() {
				
				public String getValue(Pub object) {
					return valueOrDefault(object.getCountry());
				}
			}, "Country");
		}

		{
			addColumn(new TextColumn<Pub>() {
				
				public String getValue(Pub object) {
					return valueOrDefault(object.getCity());
				}
			}, "City");
		}

		{
			addColumn(new TextColumn<Pub>() {
				
				public String getValue(Pub object) {
					return valueOrDefault(object.getAddress());
				}
			}, "Address");
		}

		{
			addColumn(new TextColumn<Pub>() {
				
				public String getValue(Pub object) {
					return object.getLocation().toString();
				}
			}, "Location");
		}

		{
			addColumn(new TextColumn<Pub>() {
				
				public String getValue(Pub object) {
					return valueOrDefault(object.getPhone());
				}
			}, "Phone");
		}

		{
			addColumn(new TextColumn<Pub>() {
				
				public String getValue(Pub object) {
					return valueOrDefault(object.getHomepage());
				}
			}, "Homepage");
		}

		{
			final ButtonCell saveCell = new ButtonCell();
			Column<Pub, String> saveColumn = new Column<Pub, String>(saveCell) {
				
				public String getValue(Pub object) {
					return "Edit";
				}
			};
			saveColumn.setFieldUpdater(new FieldUpdater<Pub, String>() {

				
				public void update(int arg0, Pub arg1, String arg2) {
					editPub(arg1);
				}

			});
			addColumn(saveColumn, "Edit");
		}
	}

	public abstract void editPub(Pub arg1);

}
