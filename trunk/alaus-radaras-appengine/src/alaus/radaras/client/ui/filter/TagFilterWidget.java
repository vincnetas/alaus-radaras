package alaus.radaras.client.ui.filter;

import com.google.gwt.user.client.ui.CheckBox;

public class TagFilterWidget extends FilterWidget<String> {
    
    private CheckBox checkBox;
    
    public TagFilterWidget(String country, PubFilter filter) {          
        super(country, filter);
        
        checkBox = new CheckBox(country);
        checkBox.addValueChangeHandler(this);
        
        initWidget(checkBox);  
    }       
}