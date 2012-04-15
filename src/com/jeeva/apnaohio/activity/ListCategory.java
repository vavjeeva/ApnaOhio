package com.jeeva.apnaohio.activity;

import com.jeeva.apnaohio.R;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;

public class ListCategory extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);

		  String[] categories = getResources().getStringArray(R.array.category_array);
		  setListAdapter(new ArrayAdapter<String>(this, R.layout.item, categories));

		  ListView lv = getListView();
		  lv.setTextFilterEnabled(true);

		  lv.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View view, 
		    		int position, long id) {
		      LaunchTopics(((TextView) view).getText().toString());
		    }
		  });
		}
	
	 private void LaunchTopics(String category)
	    {
	    	Intent intent = new Intent(this,ListTopics.class);		
	    	intent.putExtra("Category", category);
			startActivity(intent);
	    }
}
