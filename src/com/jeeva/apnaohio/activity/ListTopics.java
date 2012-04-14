package com.jeeva.apnaohio.activity;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.htmlcleaner.HtmlNode;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.CommentNode;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.TagNodeVisitor;
import org.htmlcleaner.Utils;
import org.htmlcleaner.XPatherException;


import com.jeeva.apnaohio.R;
import com.jeeva.apnaohio.adapter.ItemAdapter;
import com.jeeva.apnaohio.entity.Item;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ListTopics extends Activity {
	ListView view;
	private Item selectedItem = null;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listtopics);
        LoadTopics();
    }
    
    private void LoadTopics(){
    	String urlPath = "http://www.apnaohio.com/classifieds.jsp?page=1&id=Moving%20Sale&city=columbus";
    	String topicsXPath = "//table[@id='ads']/tbody/tr/td";
    	HtmlCleaner cleaner = new HtmlCleaner();
    	CleanerProperties props = cleaner.getProperties();
    	 props.setAllowHtmlInsideAttributes(true);
         props.setAllowMultiWordAttributes(true);
         props.setRecognizeUnicodeChars(true);
         props.setOmitComments(true);
         
    	URL url;
    	URLConnection conn;
    	TagNode node;
    	Object[] nodes;
    	
    	List<Item> lstItems = new ArrayList<Item>();
    	
    	view = (ListView) findViewById(R.id.lstItems);			
		ItemAdapter adapter = new ItemAdapter(this,R.layout.item, lstItems);
		view.setAdapter(adapter);
		view.setClickable(true);
		view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	        public void onItemClick(AdapterView<?> a, View v, int i, long l) {	            
	                selectedItem = (Item) view.getItemAtPosition(i);
	                LaunchViewDetail();	            	            
	        }
	    });

    	
		try {
			url = new URL(urlPath);
			conn = url.openConnection();
			node = cleaner.clean(new InputStreamReader(conn.getInputStream()));
			nodes = node.evaluateXPath(topicsXPath);
			
			// if node found
			Item item = new Item();
			if(nodes != null && nodes.length > 0) {	    		
	    		int len = nodes.length;
	    		for(int i = 0; i < len; ++i) {
	    			node = (TagNode) nodes[i];
	    			if (node instanceof TagNode) {			            			            
	    				
			            if(node.getElementsByName("a", true).length > 0)
			            {
			            	item = new Item();
			            	TagNode linkNode = node.getElementsByName("a", true)[0];
			            	item.title = linkNode.getText().toString();
			            	item.url = linkNode.getAttributes().get("href");
			            			
			            }
			            String tagText = node.getText().toString();
			            if(tagText.startsWith("201")){			            
			            	item.datePosted = tagText;
			            	lstItems.add(item);
			            }
			        } 
	    		}
	    	}						
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	catch (XPatherException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
          
    private void LaunchViewDetail()
    {
    	Intent intent = new Intent(this,ViewDetail.class);		
    	intent.putExtra("Item", selectedItem);
		startActivity(intent);
    }
}