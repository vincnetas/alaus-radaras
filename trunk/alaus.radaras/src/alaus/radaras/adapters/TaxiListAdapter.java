package alaus.radaras.adapters;

import java.util.List;

import alaus.radaras.R;
import alaus.radaras.service.model.Taxi;
import alaus.radaras.viewholders.TaxiViewHolder;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TaxiListAdapter extends BaseAdapter {

	private List<Taxi> taxies;
	private LayoutInflater inflater;
	
	public TaxiListAdapter(Context context, List<Taxi> taxies) {
		this.taxies = taxies;
		this.inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return taxies.size();
	}

	@Override
	public Object getItem(int position) {
		return taxies.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

        TaxiViewHolder holder;


        if (convertView == null) {
            convertView = inflater.inflate(R.layout.taxi_list_item, null);

            holder = new TaxiViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.taxiTitle);
            holder.phone = (TextView) convertView.findViewById(R.id.taxiPhone);
            holder.city = (TextView) convertView.findViewById(R.id.taxiCity);
            holder.img = (ImageView) convertView.findViewById(R.id.taxiIcon);
            convertView.setTag(holder);
        } else {
            holder = (TaxiViewHolder) convertView.getTag();
        }
        Taxi taxi = taxies.get(position);
        // Bind the data efficiently with the holder.
        holder.title.setText(taxi.getTitle());
        holder.city.setText(taxi.getCity());
        holder.phone.setText(taxi.getPhone());
        holder.img.setImageResource(android.R.drawable.sym_action_call);
        
        return convertView;
	}

}

