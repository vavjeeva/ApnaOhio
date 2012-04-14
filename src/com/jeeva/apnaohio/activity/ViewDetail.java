package com.jeeva.apnaohio.activity;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;


import com.jeeva.apnaohio.entity.Item;
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
        
        LoadItemDetail();
    }
    
    private void LoadItemDetail()
    {
    	TextView txtDescription = (TextView) findViewById(R.id.txtDescription);
    	String urlPath = "http://www.apnaohio.com/";
    	String detailXPath = "//div[@id='license']";
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
    	try {
			url = new URL(urlPath + item.url);
			conn = url.openConnection();
			node = cleaner.clean(new InputStreamReader(conn.getInputStream()));
			nodes = node.evaluateXPath(detailXPath);
			
			// if node found			
			if(nodes != null && nodes.length > 0) {	    		
	    			node = (TagNode) nodes[0];
	    			if (node instanceof TagNode) {			            			            	    							          
			             txtDescription.setText(node.getText().toString());			            	
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
}