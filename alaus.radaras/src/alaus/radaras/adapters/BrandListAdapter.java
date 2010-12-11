package alaus.radaras.adapters;

import java.util.List;

import alaus.radaras.R;
import alaus.radaras.dao.BeerRadarDao;
import alaus.radaras.dao.model.Brand;
import alaus.radaras.viewholders.BrandViewHolder;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BrandListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Brand> brands;
    public BrandListAdapter(Context context, List<Brand> brands) {
        // Cache the LayoutInflate to avoid asking for a new one each time.
        mInflater = LayoutInflater.from(context);
        this.brands = brands;
        // Icons bound to the rows.
//        sebIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.seb);
//        swedIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.hansa);
//        parexIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.parex);
//        dnbIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.dnbnord);
//        danskeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.danske);
//        nordeaIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.nordea);
//        snorasIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.snoras);
//        ukioIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ukio);
//        siauliuIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.siauliu);
        Log.i("BrandProvider", "in BrandProvider");
       // mIcon1 = 
       // mIcon2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon48x48_2);
    }

    /**
     * The number of items in the list is determined by the number of speeches
     * in our array.
     *
     * @see android.widget.ListAdapter#getCount()
     */
    public int getCount() {
        return brands.size();
    }

    /**
     * Since the data comes from an array, just returning the index is
     * sufficent to get at the data. If we were using a more complex data
     * structure, we would return whatever object represents one row in the
     * list.
     *
     * @see android.widget.ListAdapter#getItem(int)
     */
    public Object getItem(int position) {
        return position;
    }

    /**
     * Use the array index as a unique id.
     *
     * @see android.widget.ListAdapter#getItemId(int)
     */
    public long getItemId(int position) {
        return position;
    }

    /**
     * Make a view to hold each row.
     *
     * @see android.widget.ListAdapter#getView(int, android.view.View,
     *      android.view.ViewGroup)
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        // A BeerBrandViewHolder keeps references to children views to avoid unneccessary calls
        // to findViewById() on each row.
        BrandViewHolder holder;

        // When convertView is not null, we can reuse it directly, there is no need
        // to reinflate it. We only inflate a new View when the convertView supplied
        // by ListView is null.
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.brand_list_item, null);

            // Creates a BeerBrandViewHolder and store references to the two children views
            // we want to bind data to.
            holder = new BrandViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.BrandTitle);
            holder.img = (ImageView) convertView.findViewById(R.id.BrandIcon);
            convertView.setTag(holder);
        } else {
            // Get the BeerBrandViewHolder back to get fast access to the TextView
            // and the ImageView.
            holder = (BrandViewHolder) convertView.getTag();
        }
        Brand brnd = brands.get(position);
        // Bind the data efficiently with the holder.
        holder.title.setText(brnd.getTitle());
        holder.img.setImageDrawable((BeerRadarDao.getInstance(mInflater.getContext()).getImage(brnd.getIcon())));
        
        return convertView;
    }

//    private CharSequence formatDistance(double distance) {
//		// TODO Auto-generated method stub
//    	if(distance > 1)
//    	{
//			return kilometeresFormat.format(distance) + " km";
//    	}
//    	else
//    	{
//			return meteresFormat.format(distance * 1000) + " m";
//    	}
//	}

}