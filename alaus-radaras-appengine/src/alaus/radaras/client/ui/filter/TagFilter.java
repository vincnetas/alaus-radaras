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
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Vincentas
 *
 */
public class TagFilter extends BaseFilter {

    private final PubTagFilter filter;
    
    @Override
    protected Set<Widget> getFilterWidgets(Pub pub) {
        Set<Widget> result = new HashSet<Widget>();
        
        for (final String tag : getPubBeerTags(pub)) {
            TagFilterWidget widget = new TagFilterWidget(tag);
            widget.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
                
                @Override
                public void onValueChange(ValueChangeEvent<Boolean> arg0) {
                    if (arg0.getValue()) {
                        filter.addTag(tag);
                    } else {
                        filter.removeTag(tag);
                    }
                    
                    Stat.getHandlerManager().fireEvent(new PubFilterEvent(filter));
                }
            });
        }
        
        return result;
    }
    
    private Set<String> getPubBeerTags(Pub pub) {
        
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
    
    class PubTagFilter implements PubFilter {

        private final Set<String> tags = new HashSet<String>();
        
        @Override
        public boolean match(Pub pub) {
            if (tags.isEmpty()) {
                return true;
            }
            
            return getPubBeerTags(pub).containsAll(tags);
        }

        public void addTag(String country) {
            tags.add(country.toLowerCase());
        }
        
        public void removeTag(String country) {
            tags.remove(country.toLowerCase());
        }       
    }
}
