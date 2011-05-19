package alaus.radaras;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class BrandTabWidget extends TabActivity {

	public void onCreate(Bundle savedInstanceState) {
	   super.onCreate(savedInstanceState);
	    setContentView(R.layout.brand_tabs);

	    Resources res = getResources(); 
	    final TabHost tabHost = getTabHost(); 
	    TabHost.TabSpec spec;
	    Intent intent;  

	    intent = new Intent().setClass(this, BrandListActivity.class);

	    spec = tabHost.newTabSpec("beer").setIndicator(getString(R.string.tabs_title_brands),
	                      res.getDrawable(R.drawable.ic_tab_brands))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    intent = new Intent().setClass(this, CountryListActivity.class);
	    spec = tabHost.newTabSpec("country").setIndicator(getString(R.string.tabs_title_countries),
	                      res.getDrawable(R.drawable.ic_tab_countries))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    intent = new Intent().setClass(this, TagListActivity.class);
	    spec = tabHost.newTabSpec("types").setIndicator(getString(R.string.tabs_title_tags),
	                      res.getDrawable(R.drawable.ic_tab_tags))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    tabHost.setCurrentTab(0);
	}
}
