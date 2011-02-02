package alaus.radaras.client.ui.filter;

import alaus.radaras.shared.model.Beer;

import com.google.gwt.user.client.ui.CheckBox;

public class BeerFilterWidget extends FilterWidget<Beer> {
    
    private CheckBox checkBox;
    
    public BeerFilterWidget(Beer beer, PubFilter filter) {
        super(beer, filter);
        
        checkBox = new CheckBox(beer.getTitle());
        checkBox.addValueChangeHandler(this);
        
        initWidget(checkBox);         
    }
}
