package alaus.radaras.adapters;

import java.text.DecimalFormat;
import java.util.List;

import alaus.radaras.viewholders.BeerBrandViewHolder;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class BeerBrandListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Bitmap sebIcon;
    private Bitmap swedIcon;
    private Bitmap snorasIcon;
    private Bitmap dnbIcon;
    private Bitmap ukioIcon;
    private Bitmap parexIcon;
    private Bitmap siauliuIcon;
    private Bitmap nordeaIcon;
    private Bitmap danskeIcon;
    private List<ATM> atms;
    private DecimalFormat meteresFormat = new DecimalFormat("0");
    private DecimalFormat kilometeresFormat = new DecimalFormat("0.00");
    public ATMListAdapter(Context context, List<ATM> atms) {
        // Cache the LayoutInflate to avoid asking for a new one each time.
        mInflater = LayoutInflater.from(context);
        this.atms = atms;
        // Icons bound to the rows.
        sebIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.seb);
        swedIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.hansa);
        parexIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.parex);
        dnbIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.dnbnord);
        danskeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.danske);
        nordeaIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.nordea);
        snorasIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.snoras);
        ukioIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ukio);
        siauliuIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.siauliu);
        Log.i("AMTProvider", "in ATMListAdapter");
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
        return 20;
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
        BeerBrandViewHolder holder;

        // When convertView is not null, we can reuse it directly, there is no need
        // to reinflate it. We only inflate a new View when the convertView supplied
        // by ListView is null.
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.atm_list_item, null);

            // Creates a BeerBrandViewHolder and store references to the two children views
            // we want to bind data to.
            holder = new BeerBrandViewHolder();
            holder.address = (TextView) convertView.findViewById(R.id.address);
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            holder.distance = (TextView) convertView.findViewById(R.id.distance);
            holder.location = (TextView) convertView.findViewById(R.id.location);
            convertView.setTag(holder);
        } else {
            // Get the BeerBrandViewHolder back to get fast access to the TextView
            // and the ImageView.
            holder = (BeerBrandViewHolder) convertView.getTag();
        }
        ATM atm = atms.get(position);
        // Bind the data efficiently with the holder.
        holder.atmAddress = atm.getAddress() + ", " + atm.getCity();
        holder.address.setText(holder.atmAddress);
        holder.icon.setImageBitmap(getImageByBank(atm.getBank()));
        holder.distance.setText(formatDistance(atm.getDistance()));
        holder.location.setText(atm.getLocation());
        
        return convertView;
    }

    private CharSequence formatDistance(double distance) {
		// TODO Auto-generated method stub
    	if(distance > 1)
    	{
			return kilometeresFormat.format(distance) + " km";
    	}
    	else
    	{
			return meteresFormat.format(distance * 1000) + " m";
    	}
	}

	private Bitmap getImageByBank(String bank) {
		if(bank.equalsIgnoreCase("seb"))
			return sebIcon;
		else if(bank.equalsIgnoreCase("swed"))
			return swedIcon;
		else if(bank.equalsIgnoreCase("nordea"))
			return nordeaIcon;
		else if(bank.equalsIgnoreCase("parex"))
			return parexIcon;
		else if(bank.equalsIgnoreCase("snoras"))
			return snorasIcon;
		else if(bank.equalsIgnoreCase("ukio"))
			return ukioIcon;
		else if(bank.equalsIgnoreCase("nord"))
			return dnbIcon;
		else if(bank.equalsIgnoreCase("danske"))
			return danskeIcon;
		else if(bank.equalsIgnoreCase("siauliu"))
			return siauliuIcon;
		return null;
	}
}