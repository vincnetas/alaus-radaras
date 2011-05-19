package alaus.radaras.adapters;

import java.util.List;

import alaus.radaras.R;
import alaus.radaras.service.BeerRadar;
import alaus.radaras.service.BeerRadarSqlite;
import alaus.radaras.service.model.Brand;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PubBrandListAdapter extends BaseAdapter {

	private List<Brand> beers;
	private LayoutInflater inflater;
	private BeerRadar beerRadar;
	
	public PubBrandListAdapter(Context context, List<Brand> beers) {
        this.beers = beers;
        this.inflater = LayoutInflater.from(context);
        beerRadar = new BeerRadarSqlite(context);
	}

	
	@Override
	public int getCount() {
		return beers.size();
	}

	@Override
	public Object getItem(int position) {
		return beers.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v;
		if(convertView==null){
			Brand brand = beers.get(position);
			v = inflater.inflate(R.layout.pub_brand_list_item, null);
			TextView tv = (TextView)v.findViewById(R.id.pubBrandGridText);
			tv.setText(brand.getTitle());
			
			ImageView iv = (ImageView)v.findViewById(R.id.pubBrandGridIcon);
			iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
			iv.setImageDrawable(beerRadar.getImage(brand.getIcon()));
		} else {
			v = convertView;
		}
		
		return v;
	}

}
