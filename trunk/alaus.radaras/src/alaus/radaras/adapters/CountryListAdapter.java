package alaus.radaras.adapters;

import java.util.List;

import alaus.radaras.R;
import alaus.radaras.viewholders.CountryViewHolder;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CountryListAdapter extends BaseAdapter {


	private List<String> countries;
	private LayoutInflater inflater;
	
	public CountryListAdapter(Context context, List<String> countries) {
		this.countries = countries;
		this.inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return countries.size();
	}

	@Override
	public Object getItem(int position) {
		return countries.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CountryViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.country_list_item, null);

            holder = new CountryViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.countryTitle);
            holder.img = (ImageView) convertView.findViewById(R.id.countryIcon);
            convertView.setTag(holder);
        } else {
            holder = (CountryViewHolder) convertView.getTag();
        }
        String country = countries.get(position);
        // Bind the data efficiently with the holder.
        holder.title.setText(country);
        holder.img.setImageResource(R.drawable.map_02);
        
        return convertView;
	}
}
