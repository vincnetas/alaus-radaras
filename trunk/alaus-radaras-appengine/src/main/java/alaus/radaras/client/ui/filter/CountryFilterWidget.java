package alaus.radaras.client.ui.filter;

import com.google.gwt.user.client.ui.CheckBox;

public class CountryFilterWidget extends FilterWidget<String> {
    
	private CheckBox checkBox;
	
    public CountryFilterWidget(String country, PubFilter filter) {	    	
    	super(country, filter);
    	
        checkBox = new CheckBox(country);
        checkBox.addValueChangeHandler(this);
        
    	initWidget(checkBox);  
    }
}	