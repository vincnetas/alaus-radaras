/**
 * 
 */
package alaus.radaras.client;

import java.util.ArrayList;
import java.util.List;

import alaus.radaras.client.ui.dialogs.EditDialog;
import alaus.radaras.client.ui.edit.EditBeerWidget;
import alaus.radaras.client.ui.edit.EditBrandWidget;
import alaus.radaras.shared.model.Beer;
import alaus.radaras.shared.model.Brand;
import alaus.radaras.shared.model.Location;
import alaus.radaras.shared.model.LocationBounds;
import alaus.radaras.shared.model.Pub;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Vincentas
 * 
 */
public class AdminPanel extends Composite {

	private static AdminPanelUiBinder uiBinder = GWT.create(AdminPanelUiBinder.class);

	interface AdminPanelUiBinder extends UiBinder<Widget, AdminPanel> {
	}
		
	private List<Pub> data = new ArrayList<Pub>();

	private CellTable<Pub> table = PubEdit.getTable();
	
	private BrandTable brandTable;
	
	private BeerTable beerTable;
	
	@UiField
	Panel pubsPanel;

	@UiField
	Panel brandPanel;
	
	@UiField
	Panel beerPanel;
	
	@UiField
	Panel importPanel;
	
	@UiField
	Button addPubButton;
	
	@UiField
	Button addBeerButton;
	
	@UiField
	Button addBrandButton;
	
	public AdminPanel() {
		initWidget(uiBinder.createAndBindUi(this));

		brandTable = new BrandTable() {
			
			@Override
			public void editBrand(Brand brand) {
				final EditBrandWidget editBrandWidget = new EditBrandWidget(brand);
				EditDialog editDialog = new EditDialog(editBrandWidget, "Edit brand") {

					/* (non-Javadoc)
					 * @see alaus.radaras.client.ui.dialogs.EditDialog#onOkButtonClick(com.google.gwt.event.dom.client.ClickEvent)
					 */
					@Override
					public void onOkButtonClick(ClickEvent event) {
						Brand brand = editBrandWidget.getBrand();
						Brand update = new Brand();
						update.setTitle(brand.getTitle());
						update.setIcon(brand.getIcon());
						update.setHomePage(brand.getHomePage());
						update.setCountry(brand.getCountry());
						update.setHomeTown(brand.getHomeTown());
						update.setDescription(brand.getDescription());
						update.setTags(brand.getTags());
						update.setParentId(brand.getId());
						
						Stat.getBeerService().saveBrand(update, new BaseAsyncCallback<Brand>() {
							
							@Override
							public void onSuccess(Brand arg0) {								
								hide();
								redraw();
							}
						});
					}
					
				};
				
				editDialog.center();				
			}
		};
		
		beerTable = new BeerTable() {
			
			@Override
			public void editBeer(Beer beer) {
				final EditBeerWidget editBeerWidget = new EditBeerWidget(beer);
				EditDialog editDialog = new EditDialog(editBeerWidget, "Edit beer") {

					/* (non-Javadoc)
					 * @see alaus.radaras.client.ui.dialogs.EditDialog#onOkButtonClick(com.google.gwt.event.dom.client.ClickEvent)
					 */
					@Override
					public void onOkButtonClick(ClickEvent event) {
						Beer beer = editBeerWidget.getBeer();
						Beer update = new Beer();
						update.setTitle(beer.getTitle());
						update.setBrandId(beer.getBrandId());
						update.setDescription(beer.getDescription());
						update.setTags(beer.getTags());
						update.setParentId(beer.getId());
						
						Stat.getBeerService().saveBeer(update, new BaseAsyncCallback<Beer>() {
							
							@Override
							public void onSuccess(Beer arg0) {								
								hide();
								redraw();
							}
						});
					}
					
				};
				
				editDialog.center();				
			}
		};
		
		pubsPanel.add(table);
		brandPanel.add(brandTable);
		beerPanel.add(beerTable);
		
		Stat.getBeerService().findPubs(new LocationBounds(new Location(10, 20), new Location(10, 20)), new BaseAsyncCallback<List<Pub>>() {
			@Override
			public void onSuccess(List<Pub> result) {
				setData(result);
			}
		});
		
		updateBrands();
		updateBeers();
	}
	
	private void updateBrands() {
		Stat.getBeerService().getBrands(new BaseAsyncCallback<List<Brand>>() {
			
			@Override
			public void onSuccess(List<Brand> arg0) {
				brandTable.setRowData(arg0);
			}
		});
	}
	
	private void updateBeers() {
		Stat.getBeerService().getBeers(new BaseAsyncCallback<List<Beer>>() {
			
			@Override
			public void onSuccess(List<Beer> arg0) {
				beerTable.setRowData(arg0);
			}
		});
	}

	@UiHandler("addPubButton")
	public void addPubButtonClicked(ClickEvent event) {
		Pub pub = new Pub();
		getData().add(0, pub);
		setData(getData());	
	}
	
	@UiHandler("addBrandButton")
	public void addBrandButtonClicked(ClickEvent event) {
		final EditBrandWidget editBrandWidget = new EditBrandWidget(new Brand());
		EditDialog editDialog = new EditDialog(editBrandWidget, "Add new brand") {

			/* (non-Javadoc)
			 * @see alaus.radaras.client.ui.dialogs.EditDialog#onOkButtonClick(com.google.gwt.event.dom.client.ClickEvent)
			 */
			@Override
			public void onOkButtonClick(ClickEvent event) {
				Stat.getBeerService().addBrand(editBrandWidget.getBrand(), new BaseAsyncCallback<Brand>() {
					
					@Override
					public void onSuccess(Brand arg0) {
						updateBrands();
						hide();
					}
				});
			}
			
		};
		
		editDialog.center();
	}
	
	@UiHandler("addBeerButton")
	public void addBeerdButtonClicked(ClickEvent event) {
		final EditBeerWidget editBeerWidget = new EditBeerWidget(new Beer());
		EditDialog editDialog = new EditDialog(editBeerWidget, "Add new beer") {

			/* (non-Javadoc)
			 * @see alaus.radaras.client.ui.dialogs.EditDialog#onOkButtonClick(com.google.gwt.event.dom.client.ClickEvent)
			 */
			@Override
			public void onOkButtonClick(ClickEvent event) {
				Stat.getBeerService().addBeer(editBeerWidget.getBeer(), new BaseAsyncCallback<Beer>() {
					
					@Override
					public void onSuccess(Beer arg0) {
						updateBeers();
						hide();
					}
				});
			}
			
		};
		
		editDialog.center();
	}
	 
	
	/**
	 * @return the data
	 */
	public List<Pub> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(List<Pub> data) {
		this.data = data;
		
		table.setRowCount(data.size(), true);
		table.setRowData(data);
	}
}