/**
 * 
 */
package alaus.radaras;

import net.fmu1.omxticker.R;
import alaus.radaras.adapters.PubBrandListAdapter;
import alaus.radaras.dao.BeerRadarDao;
import alaus.radaras.dao.model.Brand;
import alaus.radaras.dao.model.Pub;
import android.app.Activity;
import android.content.Intent;
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
	
	private static final String PUBID = "PUBID";

	private TextView addressView;
	private TextView phoneView;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);

	    final Pub pub = BeerRadarDao.getInstance().getPub(getIntent().getExtras().get(PUBID).toString());
	    GridView gridview; //(GridView) findViewById(R.id.);
	    gridview.setAdapter(new PubBrandListAdapter(this,pub.getBeers()));
	    
	    addressView = (TextView)findViewById(R.id.address);
	    addressView.setText(pub.getAddress());
	    
	    phoneView = (TextView)findViewById(R.id.phone);
	    phoneView.setText(pub.getPhone());
	    
	    ((TextView)findViewById(R.id.name)).setText(pub.getNotes());
	    ((TextView)findViewById(R.id.notes)).setText(pub.getNotes());
	     
	    gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Brand beer = pub.getBeers().get(position);
				Toast.makeText(PubActivity.this, beer.getDescription(), Toast.LENGTH_SHORT).show();
				
			}
	    });
	    
	    phoneView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_DIAL);
				intent.setData(data)
				
			}
			
	    });
	}
}
