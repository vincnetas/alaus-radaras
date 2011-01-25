/**
 * 
 */
package alaus.radaras.client.ui.filter;

import java.util.HashSet;
import java.util.Set;

import alaus.radaras.client.Stat;
import alaus.radaras.client.events.PubFilterEvent;
import alaus.radaras.client.ui.filter.CountryFilter.CountryFilterWidget;
import alaus.radaras.shared.model.Pub;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Vincentas
 *
 */
public class TagFilter extends BaseFilter {

    @Override
    protected void getFilterWidgets(final Pub pub, AsyncCallback<Set<Widget>> callback) {
        Set<Widget> result = new HashSet<Widget>();
        
        for (final String tag : getPubBeerTags(pub)) {
            TagFilterWidget widget = new TagFilterWidget(tag);
            widget.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
                
                @Override
                public void onValueChange(ValueChangeEvent<Boolean> arg0) {
                    if (arg0.getValue()) {
                        filter.addPub(pub);
                    } else {
                        filter.removePub(pub);
                    }
                    
                    Stat.getHandlerManager().fireEvent(new PubFilterEvent(filter));
                }
            });
        }
        
        callback.onSuccess(result);
    }

    class TagFilterWidget extends CheckBox {
        
        private final String tag;
        
        public TagFilterWidget(String tag) {
            super(tag);         
            this.tag = tag;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {         
            return tag.hashCode();
        }

        /* (non-Javadoc)
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) {
            boolean result = false;
            
            if (obj instanceof CountryFilterWidget) {
                TagFilterWidget widget = (TagFilterWidget) obj;
                result = tag.equalsIgnoreCase(widget.tag);
            }
            
            return result;
        }       
    }
}
