package alaus.radaras.adapters;

import java.util.List;

import alaus.radaras.R;
import alaus.radaras.dao.BeerRadarDao;
import alaus.radaras.dao.model.Brand;
import alaus.radaras.dao.model.Country;
import alaus.radaras.viewholders.CountryViewHolder;
import alaus.radaras.viewholders.PubViewHolder;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class PubBrandListAdapter extends BaseAdapter {

	private List<Brand> beers;
	private Context context;
	private LayoutInflater inflater;
	
	public PubBrandListAdapter(Context context, List<Brand> beers) {
	        this.context = context;
	        this.beers = beers;
	        this.inflater = LayoutInflater.from(context);
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
		
//		PubViewHolder holder;
//
//
//        if (convertView == null) {
//            convertView = inflater.inflate(R.layout.pub_brand_list_item, null);
//
//            holder = new PubViewHolder();
//            holder.title = (TextView) convertView.findViewById(R.id.pubBrandGridText);
//            holder.img = (ImageView) convertView.findViewById(R.id.pubBrandGridIcon);
//            holder.img.setLayoutParams(new GridView.LayoutParams(70, 70));
//            holder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            holder.img.setPadding(8, 8, 8, 8);
//            convertView.setTag(holder);
//        } else {
//            holder = (PubViewHolder) convertView.getTag();
//        }
//        Brand brand = beers.get(position);
//        // Bind the data efficiently with the holder.
//        holder.title.setText(brand.getTitle());
//        holder.img.setImageDrawable(BeerRadarDao.getInstance(context).getImage(brand.getIcon()));
//        
//        return convertView;
		
		
		// TODO Auto-generated method stub
		View v;
		if(convertView==null){
			 Brand brand = beers.get(position);
			v = inflater.inflate(R.layout.pub_brand_list_item, null);
			TextView tv = (TextView)v.findViewById(R.id.pubBrandGridText);
			tv.setText(brand.getTitle());
			
			ImageView iv = (ImageView)v.findViewById(R.id.pubBrandGridIcon);
			//iv.setLayoutParams(new GridView.LayoutParams(70, 70));
			iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
			iv.setImageDrawable(BeerRadarDao.getInstance(context).getImage(brand.getIcon()));
			//iv.setPadding(8, 8, 8, 8);
		}
		else
		{
			v = convertView;
		}
		return v;
	}

}
