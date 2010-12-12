package alaus.radaras;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

public class BrandTabWidget extends TabActivity {

	public void onCreate(Bundle savedInstanceState) {
	   super.onCreate(savedInstanceState);
	    setContentView(R.layout.brand_tabs);

	    Resources res = getResources(); 
	    final TabHost tabHost = getTabHost(); 
	    TabHost.TabSpec spec;
	    Intent intent;  

	    intent = new Intent().setClass(this, BrandListActivity.class);

	    spec = tabHost.newTabSpec("alūs").setIndicator("Alūs",
	                      res.getDrawable(R.drawable.ic_tab_brands))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    intent = new Intent().setClass(this, CountryListActivity.class);
	    spec = tabHost.newTabSpec("šalys").setIndicator("Šalys",
	                      res.getDrawable(R.drawable.ic_tab_countries))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    intent = new Intent().setClass(this, TagListActivity.class);
	    spec = tabHost.newTabSpec("tipai").setIndicator("Tipai",
	                      res.getDrawable(R.drawable.ic_tab_tags))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    tabHost.setCurrentTab(0);
	    //setTabColor(tabHost);
	    tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				//setTabColor(tabHost);
			}
		});
	    
	}
	
	protected static void setTabColor(TabHost tabhost) {
	    for(int i=0;i<tabhost.getTabWidget().getChildCount();i++)
	    {
	       tabhost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#461B0A")); //unselected
	    }
	    tabhost.getTabWidget().getChildAt(tabhost.getCurrentTab()).setBackgroundColor(Color.parseColor("#f9C22C")); // selected
	}
}
