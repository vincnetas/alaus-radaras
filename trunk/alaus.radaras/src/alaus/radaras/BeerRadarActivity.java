package alaus.radaras;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class BeerRadarActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
		Button button = (Button) findViewById(R.id.ButtonNear);
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(BeerRadarActivity.this, GimeLocation.class);
                startActivity(intent);
			}
		});
		Button buttonBrand = (Button) findViewById(R.id.ButtonBrand);
		buttonBrand.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
                Intent inte = new Intent(BeerRadarActivity.this, BrandListActivity.class);
        		try
        		{
        			startActivity(inte);
        		}catch(Exception ex){
        			Toast.makeText(BeerRadarActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        		}
			}
		});

		Button buttonLucky = (Button) findViewById(R.id.ButtonLucky);
		buttonLucky.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
                Intent inte = new Intent(BeerRadarActivity.this, LuckyActivity.class);
        		try
        		{
        			startActivity(inte);
        		}catch(Exception ex){
        			Toast.makeText(BeerRadarActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        		}
			}
		});

    }
}