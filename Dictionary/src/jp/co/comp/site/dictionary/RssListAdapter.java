package jp.co.comp.site.dictionary;

import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.*;
import android.widget.TextView;
import java.util.*;

public class RssListAdapter extends ArrayAdapter<Item> {

	private LayoutInflater mInflater;
	private TextView mTitle;
	private TextView mDescr;
	
	public RssListAdapter(Context context, List<Item> objects){
		super(context, 0, objects);
		mInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		View view = convertView;
				
		if(convertView == null){
			view = mInflater.inflate(R.layout.item_row, null);
		}
		
		Item item = this.getItem(position);
		if(item != null){
			String title = item.getTitle().toString();
			mTitle = (TextView)view.findViewById(R.id.item_title);
			mTitle.setText(title);
			
			String descr = item.getDescription().toString();
			mDescr = (TextView)view.findViewById(R.id.item_descr);
			mDescr.setText(descr);
		}
		
		return view;
	}
}
