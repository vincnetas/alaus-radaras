package alaus.radaras.adapters;

import java.util.List;

import alaus.radaras.R;
import alaus.radaras.viewholders.TagViewHolder;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TagListAdapter extends BaseAdapter {

	private List<String> tags;
	private LayoutInflater inflater;
	
	public TagListAdapter(Context context, List<String> tags) {
		this.tags = tags;
		this.inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return tags.size();
	}

	@Override
	public Object getItem(int position) {
		return tags.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

        TagViewHolder holder;


        if (convertView == null) {
            convertView = inflater.inflate(R.layout.tag_list_item, null);

            holder = new TagViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.tagTitle);
            holder.img = (ImageView) convertView.findViewById(R.id.tagIcon);
            convertView.setTag(holder);
        } else {
            holder = (TagViewHolder) convertView.getTag();
        }
        String tag = tags.get(position);

        // Bind the data efficiently with the holder.
        holder.title.setText(tag);
        holder.img.setImageResource(R.drawable.alus);
        
        return convertView;
	}

}
