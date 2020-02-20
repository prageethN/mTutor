package com.mtutor.attachment;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.apache.http.util.ByteArrayBuffer;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class AttachmentViewActivity extends Activity{

	private static final String PATH = "/sdcard/temp_mtutor/";
	
	String attURL,attType;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setStartUp();
	}

	void setStartUp(){
		
		if (getIntent().getExtras().getSerializable("ATT_TYPE") != null) {

			attURL = getIntent().getExtras().getSerializable("ATT_URL")
					.toString();
			attType = getIntent().getExtras().getSerializable("ATT_TYPE")
					.toString();
			openResourceFile(Integer.parseInt(attType),attURL);
		}
		
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	
	void openResourceFile(int rType,String rUrl){
		String rName;
		switch(rType){
		
		case 1:
			openVideo(rUrl);break;
		case 2:
			rName=getResourceName(rUrl);
			DownloadFromUrl(PATH+rName,rUrl);
			openImage(rName);break;			
		case 3:
			rName=getResourceName(rUrl);
			DownloadFromUrl(PATH+rName,rUrl);			
			openPDF(rName);break;
		case 4:
			rName=getResourceName(rUrl);
			DownloadFromUrl(PATH+rName,rUrl);
			openDoc(rName);	break;
		case 5:
			rName=getResourceName(rUrl);
			DownloadFromUrl(PATH+rName,rUrl);
			openPTT(rName);break;
		case 6:
			rName=getResourceName(rUrl);
			DownloadFromUrl(PATH+rName,rUrl);
			openExcel(rName);break;
		case 7:
			openAudio(rUrl);
		}
		
		
	}
	 void openVideo(String rUrl){
	    	
	    	Uri uri = Uri.parse(rUrl);
	        Intent intent = new Intent(Intent.ACTION_VIEW); 
	        intent.setDataAndType(uri, "video/mp4");
	        startActivity(intent);
	        
	    }
	    void openAudio(String rUrl){
	    	
	    	Uri uri = Uri.parse(rUrl);
	        Intent intent = new Intent(Intent.ACTION_VIEW); 
	        intent.setDataAndType(uri, "audio/mp3");
	        startActivity(intent);
	        
	    }
	    void openExcel(String rName){
	    	
	    	Intent intent = new Intent();
	    	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    	intent.setAction(Intent.ACTION_VIEW);
	    	String type = "application/vnd.ms-excel";
	    	File file = new File(PATH+rName);
	    	intent.setDataAndType(Uri.fromFile(file), type);
	    	startActivity(intent);
	    }
	    void openPTT(String rName){
	    	Intent intent = new Intent();
	    	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    	intent.setAction(Intent.ACTION_VIEW);
	    	String type = "application/vnd.ms-powerpoint";
	    	File file = new File(PATH+rName);
	    	intent.setDataAndType(Uri.fromFile(file), type);
	    	startActivity(intent);
	    	
	    }
	    void openDoc(String rName){
	    	Intent intent = new Intent();
	    	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    	intent.setAction(Intent.ACTION_VIEW);
	    	String type = "application/msword";
	    	File file = new File(PATH+rName);
	    	intent.setDataAndType(Uri.fromFile(file), type);
	    	startActivity(intent);
	    }
	    void openImage(String rName){
	    	
	    	Intent intent = new Intent();  
	        intent.setAction(android.content.Intent.ACTION_VIEW);
	        File file1 = new File(PATH+rName);
	        intent.setDataAndType(Uri.fromFile(file1), "image/jpg");
	        startActivity(intent);

	    }
	    void openPDF(String rName){
	    	 File file = new File(PATH+rName);

	         if (file.exists()) {
	             Uri path = Uri.fromFile(file);
	             Intent intent = new Intent(Intent.ACTION_VIEW);
	             intent.setDataAndType(path, "application/pdf");
	             intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

	             try {
	                 startActivity(intent);
	             } 
	             catch (ActivityNotFoundException e) {
	                 Toast.makeText(getApplicationContext(), 
	                     "No Application Available to View PDF", 
	                     Toast.LENGTH_SHORT).show();
	             }
	         }
	    	
	    }
		 public Boolean DownloadFromUrl(String fileName, String resourceURL) {
		        try {
		                URL url = new URL(resourceURL); //you can write here any link
		                File file = new File(fileName);

		                long startTime = System.currentTimeMillis();
		                System.out.print("Starting download......from " + url);
		                URLConnection ucon = url.openConnection();
		                InputStream is = ucon.getInputStream();
		                BufferedInputStream bis = new BufferedInputStream(is);
		                /*
		                 * Read bytes to the Buffer until there is nothing more to read(-1).
		                 */
		                ByteArrayBuffer baf = new ByteArrayBuffer(50);
		                int current = 0;
		                while ((current = bis.read()) != -1) {
		                        baf.append((byte) current);
		                }

		                FileOutputStream fos = new FileOutputStream(file);
		                fos.write(baf.toByteArray());
		                fos.close();
		                System.out.print("Download Completed in" + ((System.currentTimeMillis() - startTime) / 1000) + " sec");
		                
		        } catch (IOException e) {
		        	 System.out.print("Error: " + e);
		        	 return false;
		        }
		        return true;
		}
		 String getResourceName(String rUrl){
			 
			 String rName=null;
			 String arrSplit[]=rUrl.split("/");
			 rName=arrSplit[arrSplit.length-1];
			 
			 return rName;
		 }

}
