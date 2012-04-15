package com.jeeva.apnaohio.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import android.text.TextUtils;
import android.widget.TextView;

import com.jeeva.apnaohio.R;
import com.jeeva.apnaohio.entity.Item;

public final class PageParser {
	
	public static final String baseURL = "http://www.apnaohio.com/";
	public static final String city = "columbus"; //TODO : read from local storage
	
	public static List<Item> LoadTopics(String category,String page){
		String encodedCategory = null;
		try {
			encodedCategory = URLEncoder.encode(category, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			
		}
    	String urlPath = baseURL + "classifieds.jsp?page="+page+"&id="+encodedCategory+"&city="+city;
    	String topicsXPath = "//table[@id='ads']/tbody/tr/td";    	
    	List<Item> lstItems = new ArrayList<Item>();
    	    	
		try {
			TagNode node = LoadHTML(urlPath);
			Object[] nodes = node.evaluateXPath(topicsXPath);
			
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
		}
		catch (XPatherException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lstItems;
    }
	
	private static TagNode LoadHTML(String urlPath){		
		TagNode node = null;
		HtmlCleaner cleaner = new HtmlCleaner();
    	CleanerProperties props = cleaner.getProperties();
		props.setAllowHtmlInsideAttributes(true);
	    props.setAllowMultiWordAttributes(true);
	    props.setRecognizeUnicodeChars(true);
	    props.setOmitComments(true);
		
	    URL url;
    	URLConnection conn;    	    	    
		try {
			url = new URL(urlPath);
			conn = url.openConnection();
			node = cleaner.clean(new InputStreamReader(conn.getInputStream()));			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    		    
		return node;
	}
	
	public static String LoadItem(String itemURL)
    {
    	String result = null;    	
    	String detailXPath = "//div[@id='license']";    	    
    	try {
    		TagNode node = LoadHTML(baseURL + itemURL);
    		Object[] nodes = node.evaluateXPath(detailXPath);
			
			// if node found			
			if(nodes != null && nodes.length > 0) {	    		
	    			node = (TagNode) nodes[0];
	    			if (node instanceof TagNode) {			            			            	    							          
			             result = node.getText().toString();			            	
			        } 
	    		}	    	
		}
    	catch (XPatherException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return result;
    }

}
