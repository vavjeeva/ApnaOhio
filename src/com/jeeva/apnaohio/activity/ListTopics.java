package com.jeeva.apnaohio.activity;

import java.util.List;

import com.jeeva.apnaohio.R;
import com.jeeva.apnaohio.adapter.ItemAdapter;
import com.jeeva.apnaohio.entity.Item;
import com.jeeva.apnaohio.util.PageParser;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ListTopics extends ListActivity {	
	private Item selectedItem = null;
	private String category = null;
    	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        category = (String) getIntent().getSerializableExtra("Category");        
        List<Item> items = PageParser.LoadTopics(category, "1");
        setListAdapter(new ItemAdapter(this, R.layout.item, items));
        
        ListView lv = getListView();
        lv.setTextFilterEnabled(true);
		lv.setOnItemClickListener(myClickListener);
    }
    
    protected OnItemClickListener myClickListener = new AdapterView.OnItemClickListener() {
    	public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
			selectedItem = (Item) parent.getItemAtPosition(position);
            LaunchViewDetail();					
	    }
    };
          
    private void LaunchViewDetail()
    {
    	Intent intent = new Intent(this,ViewDetail.class);		
    	intent.putExtra("Item", selectedItem);
		startActivity(intent);
    }
}