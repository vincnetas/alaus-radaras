/**
 * 
 */
package alaus.radaras;

import java.util.List;

import alaus.radaras.adapters.PubBrandListAdapter;
import alaus.radaras.dao.BeerRadarDao;
import alaus.radaras.dao.model.Brand;
import alaus.radaras.dao.model.Pub;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Vincentas
 * @author Gytis
 *
 */
public class PubActivity extends Activity {
	
	public static final String PUBID = "PUBID";

	private TextView addressView;
	private TextView phoneView;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.pub);

	    BeerRadarDao dao = BeerRadarDao.getInstance(this);
	    String pubId = getIntent().getExtras().get(PUBID).toString();
	    final Pub pub = dao.getPub(pubId);
	    final List<Brand> brands = dao.getBrandsByPubId(pub.getId());
	    
    	GridView gridview = (GridView) findViewById(R.id.pubBrandList);
    	TextView noBeers = (TextView)findViewById(R.id.pubNoBeers);
	    if(brands != null && brands.size() > 0) {
	    	gridview.setAdapter(new PubBrandListAdapter(this,brands));
	    	noBeers.setVisibility(View.GONE);
	    	gridview.setVisibility(View.VISIBLE);
	    } else{
	    	gridview.setVisibility(View.GONE);
	    	noBeers.setVisibility(View.VISIBLE);
	    }
	    addressView = (TextView)findViewById(R.id.pubAddress);
	    addressView.setText(pub.getAddress());
	    
	    phoneView = (TextView)findViewById(R.id.pubPhone);
	    phoneView.setText(pub.getPhone());
	    
	    ((TextView)findViewById(R.id.pubName)).setText(pub.getTitle());
	    ((TextView)findViewById(R.id.pubNotes)).setText(pub.getNotes());
	     
	    gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Brand beer = brands.get(position);
				Toast.makeText(PubActivity.this, beer.getDescription(), Toast.LENGTH_SHORT).show();
				
			}
	    });
	    
	    phoneView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_DIAL);
				intent.setData(Uri.parse("tel:"+pub.getPhone()));
				startActivity(intent);
				
			}
	    });
	    
	    addressView.setOnClickListener(new OnClickListener() {
	    	@Override
			public void onClick(View v) {
				
				String uri = "geo:0,0?q=" + pub.getAddress();
				startActivity(new Intent(android.content.Intent.ACTION_VIEW, Uri
						.parse(uri)));

			}
		});
	    
	    
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
	    setIntent(intent);
	    handleIntent(intent);
	}

	private void handleIntent(Intent intent) {
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	      String query = intent.getStringExtra(SearchManager.QUERY);
	      String inq = query;
	    }
	}
}
