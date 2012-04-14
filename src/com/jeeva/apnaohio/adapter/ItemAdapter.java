package com.jeeva.apnaohio.adapter;

import java.util.List;


import com.jeeva.apnaohio.R;
import com.jeeva.apnaohio.entity.Item;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemAdapter extends ArrayAdapter<Item> {

	int resource;
	String response;
	Context context;

	// Initialize adapter
	public ItemAdapter(Context context, int resource,
			List<Item> items) {
		super(context, resource, items);
		this.resource = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout gridView;
		// Get the current alert object
		Item item = getItem(position);

		// Inflate the view
		if (convertView == null) {
			gridView = new LinearLayout(getContext());
			String inflater = Context.LAYOUT_INFLATER_SERVICE;
			LayoutInflater vi;
			vi = (LayoutInflater) getContext().getSystemService(inflater);
			vi.inflate(resource, gridView, true);
		} else {
			gridView = (LinearLayout) convertView;
		}
		// Get the text boxes from the listitem.xml file
		TextView txtTitle = (TextView) gridView.findViewById(R.id.txtTitle);
		//TextView txtDatePosted = (TextView) gridView.findViewById(R.id.txtDatePosted);				
		
		txtTitle.setText(item.title);
		//txtDatePosted.setText(item.datePosted);
		return gridView;
	}
}
