package alaus.radaras;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BeerRadarActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
		Button button = (Button) findViewById(R.id.Button01);
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
    }
}