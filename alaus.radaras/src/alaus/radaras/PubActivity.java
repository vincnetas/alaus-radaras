/**
 * 
 */
package alaus.radaras;

import java.util.List;
import alaus.radaras.adapters.PubBrandListAdapter;
import alaus.radaras.dialogs.PubBrandInfoDialog;
import alaus.radaras.dialogs.PubBrandInfoDialog.PubBrandInfoSubmited;
import alaus.radaras.dialogs.PubCorrectionDialog;
import alaus.radaras.dialogs.PubCorrectionDialog.PubErrorSubmited;
import alaus.radaras.service.BeerRadar;
import alaus.radaras.service.BeerRadarSqlite;
import alaus.radaras.service.model.Brand;
import alaus.radaras.service.model.Pub;
import alaus.radaras.submition.DataPublisher;
import alaus.radaras.submition.PubBrandInfo;
import alaus.radaras.submition.PubCorrection;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Vincentas
 * @author Gytis
 *
 */
public class PubActivity extends Activity implements PubErrorSubmited {
	
	public static final String PUB_ID_PARAM = "PUBID";

	private TextView addressView;
	private TextView phoneView;
	private String pubId;
	private PubCorrectionDialog pubError;
	private BeerRadar beerRadar;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    beerRadar = new BeerRadarSqlite(this);
	    
	    setContentView(R.layout.pub);
	    pubError =  new PubCorrectionDialog(this, pubId);
	    pubError.setOnSubmitedListener(this);
	    
	    pubId = getIntent().getExtras().get(PUB_ID_PARAM).toString();
	    final Pub pub = beerRadar.getPub(pubId);
	    final List<Brand> brands = beerRadar.getBrandsByPubId(pub.getId());
	    
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
	    
	    ImageView locationImage = (ImageView) findViewById(R.id.pubLocationImage);
	    addressView = (TextView)findViewById(R.id.pubAddress);
	    addressView.setText(pub.getAddress() + ", " + pub.getCity());
	    
	    ImageView phoneImage = (ImageView) findViewById(R.id.pubPhoneImage);	    
	    phoneView = (TextView)findViewById(R.id.pubPhone);
	    phoneView.setText(pub.getPhone());
	    
	    ((TextView)findViewById(R.id.pubName)).setText(pub.getTitle());
	    
	    final TextView notesView = (TextView)findViewById(R.id.pubNotes);
	    notesView.setText(pub.getUrl());
	    ImageView internetImage = (ImageView) findViewById(R.id.pubInternet);	
	    
	    if(pub.getUrl() == null || pub.getUrl().length() == 0) {
	    	internetImage.setVisibility(View.GONE);
	    	notesView.setVisibility(View.GONE);
	    }
	 
	    
	    gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Brand beer = brands.get(position);
				
				final PubBrandInfoDialog dialog = new PubBrandInfoDialog(parent.getContext(), pubId, beer.getId());
				dialog.display();
				
				dialog.setOnSubmitedListener(new PubBrandInfoSubmited() {
					
					@Override
					public void pubInfoSubmited(PubBrandInfo brandInfo) {
						BeerRadarApp app = ((BeerRadarApp)getApplication());
						brandInfo.setLocation(app.getLastKnownLocation());
						
						DataPublisher publisher = new DataPublisher();
						brandInfo.setPubId(pubId);
						if(publisher.submitPubBrandInfo(brandInfo)){
							dialog.dismiss();
							Toast.makeText(getApplicationContext(), getString(R.string.pub_brand_submit_thanks), 
									Toast.LENGTH_SHORT).show();
						} else {
							pubError.setErrorState(true);
						}
						
					}
				});
				
				Toast.makeText(PubActivity.this, beer.getTitle(), Toast.LENGTH_SHORT).show();
				
			}
	    });
	    
	    OnClickListener phoneOnClickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_DIAL);
				intent.setData(Uri.parse("tel:"+pub.getPhone()));
				startActivity(intent);				
			}
	    };
	    phoneView.setOnClickListener(phoneOnClickListener);
	    phoneImage.setOnClickListener(phoneOnClickListener);
	    
	    OnClickListener locationOnClickListener = new OnClickListener() {
	    	@Override
			public void onClick(View v) {
				
				String uri = "geo:0,0?q=" + pub.getAddress();
				startActivity(new Intent(android.content.Intent.ACTION_VIEW, Uri
						.parse(uri)));

			}
		};
	    addressView.setOnClickListener(locationOnClickListener);
	    locationImage.setOnClickListener(locationOnClickListener);	
	    
	    internetImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(notesView.getText().toString()));
				startActivity(i);
				
			}
		});
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.pub_menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.pubError:

	    	pubError.setErrorState(false);
	    	pubError.display();
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}


	@Override
	public void pubErrorSubmited(PubCorrection pubErrorData) {
		DataPublisher publisher = new DataPublisher();
		pubErrorData.setPubId(pubId);
		if(publisher.submitPubCorrection(pubErrorData)){
			pubError.dismiss();
			Toast.makeText(getApplicationContext(), getString(R.string.submiting_thanks), Toast.LENGTH_SHORT).show();
		} else {
			pubError.setErrorState(true);
		}
		
	}
}
