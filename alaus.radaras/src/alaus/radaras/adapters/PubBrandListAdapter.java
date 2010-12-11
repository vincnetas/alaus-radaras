package alaus.radaras.adapters;

import java.util.List;

import alaus.radaras.dao.BeerRadarDao;
import alaus.radaras.dao.model.Brand;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class PubBrandListAdapter extends BaseAdapter {

	private List<Brand> beers;
	private Context context;
	
	public PubBrandListAdapter(Context context, List<Brand> beers) {
	        this.context = context;
	        this.beers = beers;
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
		// TODO unclear
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 ImageView imageView;
	        if (convertView == null) {  // if it's not recycled, initialize some attributes
	            imageView = new ImageView(context);
	            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
	            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	            imageView.setPadding(8, 8, 8, 8);
	        } else {
	            imageView = (ImageView) convertView;
	        }
	        Brand beer = beers.get(position);
	        imageView.setImageDrawable(BeerRadarDao.getInstance(context).getImage(beer.getIcon()));
	        return imageView;
	}

}
