package alaus.radaras;

import alaus.radaras.dao.BeerRadarDao;
import alaus.radaras.dao.model.Brand;
import alaus.radaras.dao.model.FeelingLucky;
import alaus.radaras.dao.model.Pub;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class LuckyActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lucky);
        ImageView imgBrand = (ImageView) findViewById(R.id.LuckyBrandIcon);
        TextView brand = (TextView) findViewById(R.id.LuckyBrandTitle);
        TextView pubView = (TextView) findViewById(R.id.LuckyPubTitle);
        FeelingLucky lucky = new FeelingLucky();
        Brand brData = lucky.getBrand();
        Pub pb = lucky.getPub();
        imgBrand.setImageDrawable(BeerRadarDao.getInstance(this).getImage(brData.getIcon()));
        brand.setText(brData.getTitle());
        pubView.setText(pb.getTitle());
	}
}
