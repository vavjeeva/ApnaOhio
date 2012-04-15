package com.jeeva.apnaohio.activity;

import com.jeeva.apnaohio.entity.Item;
import com.jeeva.apnaohio.util.PageParser;
import com.jeeva.apnaohio.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewDetail extends Activity {
	private Item item;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewdetail);     
        
        item = (Item) getIntent().getSerializableExtra("Item");
        
        TextView txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText(item.title);
        
        TextView txtDescription = (TextView) findViewById(R.id.txtDescription);
        txtDescription.setText(PageParser.LoadItem(item.url));
    }       
}