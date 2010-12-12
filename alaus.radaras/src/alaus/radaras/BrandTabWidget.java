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
	    TabHost tabHost = getTabHost(); 
	    TabHost.TabSpec spec;
	    Intent intent;  

	    intent = new Intent().setClass(this, BrandListActivity.class);

	    spec = tabHost.newTabSpec("artists").setIndicator("Artists",
	                      res.getDrawable(R.drawable.bok_title))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    intent = new Intent().setClass(this, BrandListActivity.class);
	    spec = tabHost.newTabSpec("albums").setIndicator("Albums",
	                      res.getDrawable(R.drawable.bok_title))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    intent = new Intent().setClass(this, BrandListActivity.class);
	    spec = tabHost.newTabSpec("songs").setIndicator("Songs",
	                      res.getDrawable(R.drawable.bok_title))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    tabHost.setCurrentTab(0);
	}
}
