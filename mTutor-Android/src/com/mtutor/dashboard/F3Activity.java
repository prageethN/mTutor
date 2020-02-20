/*
 * Copyright (C) 2011 Wglxy.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mtutor.dashboard;

import java.util.ArrayList;

import com.mtutor.dashboard.R;
import com.mtutor.data.DatabaseHandler;
import com.mtutor.servicecall.WebServiceCaller;
import com.mtutor.watchlist.WatchListItemListBaseAdapter;
import com.mtutor.watchlist.ItemDetails;
import com.mtutor.watchlist.WatchListSettingsActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class F3Activity extends DashboardActivity {

	TextView txtListHeader;
	ListView lvWatchList;
	ArrayList<ItemDetails> itemWatchList;
	
	WebServiceCaller webServiceCaller;
	SharedPreferences pref;
	DatabaseHandler db;
	
	private static final String PREFS_NAME = "MyPrefsFile";
	private static final String PREF_USERID = "userid";
	
	String userID;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_f3);
		setTitleFromActivityLabel(R.id.title_text);

		setupStartUp();
	}

	void setupStartUp() {

		webServiceCaller = new WebServiceCaller();

		pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		userID = pref.getString(PREF_USERID, null);

		db = new DatabaseHandler(this);
		
		txtListHeader=(TextView)findViewById(R.id.txtListHeader);
		
		itemWatchList = getEventsList();

		lvWatchList = (ListView) findViewById(R.id.lvWatchList);
		lvWatchList.setAdapter(new WatchListItemListBaseAdapter(this,
				itemWatchList));

		
		lvWatchList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				Object o = lvWatchList.getItemAtPosition(position);
				ItemDetails obj_itemDetails = (ItemDetails) o;
				Intent intent = new Intent(getApplicationContext(),
						WatchListSettingsActivity.class);			
					
					intent.putExtra("DOC_ID", obj_itemDetails.getTextExtra());
					intent.putExtra("AUTHOR_ID", obj_itemDetails.getTextAdditional());
					intent.putExtra("ATT_ID", obj_itemDetails.getItemId());
					intent.putExtra("ITEM_TYPE", obj_itemDetails.getTextOptional());
					intent.putExtra("ITEM_TITLE", obj_itemDetails.getTextHeader());
					startActivity(intent);
			}
		});
		lvWatchList
				.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
					@Override
					public boolean onItemLongClick(AdapterView<?> av, View v,
							int pos, long id) {
						onLongListItemClick(v, pos, id);
						return false;
					}
				});
	}

	// You then create your handler method:
	protected void onLongListItemClick(View view, int position, long id) {
		Object o = lvWatchList.getItemAtPosition(position);
		ItemDetails obj_itemDetails = (ItemDetails) o;
		String rowText = obj_itemDetails.getTextHeader();
		deleteRow(rowText, position);
	}

	

	private ArrayList<ItemDetails> getEventsList() {
		ArrayList<ItemDetails> results = new ArrayList<ItemDetails>();
		ArrayList<ArrayList> resultList=null;
		ArrayList<String> rowList=null;
		
				
		resultList=webServiceCaller.getItemList(getWishListDocuments(),getWishListAttachments());
				
				
		if(resultList!=null){
			
		for(int i=0;i<resultList.size();i++){
			
				rowList=resultList.get(i);
				
				ItemDetails item_details = new ItemDetails();
				item_details.setItemID(rowList.get(0));
				item_details.setTextExtra(rowList.get(6));
				item_details.setTextHeader(rowList.get(1));
				item_details.setTextNormal(rowList.get(4));
				item_details.setTextFooter(rowList.get(2)+" |"+getItemTypeString(Integer.parseInt(rowList.get(5))));	
				item_details.setTextOptional(rowList.get(5));
				item_details.setTextAdditional(rowList.get(3));
				item_details.setNotifyEnbale(true);
				results.add(item_details);
			
		}
		txtListHeader.setText("There are "+resultList.size()+" items");
		}
		/*ItemDetails item_details = new ItemDetails();
		item_details.setTextHeader("How to program java");
		item_details.setTextNormal("Mr. Dinusha Kankanamge");
		item_details.setTextFooter("August 23,2012 | 9:30 GMT");
		item_details.setNotifyEnbale(true);
		results.add(item_details);*/
	
		return results;
	}
	
	String getItemTypeString(int type){
		
		String strType="";
		switch (type) {
		case 0:
			strType="Document";
			break;

		case 1:
			strType="Attachment";
			break;
		}
		return strType;
	}

	void deleteRow(String rowText, int position) {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Delete Item?");
		alert.setMessage(rowText);
		// Set an EditText view to get user input

		final int positionIndex = position;
		alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {

				
				ItemDetails obj_item=itemWatchList.get(positionIndex);
				if(obj_item.getTextOptional().equalsIgnoreCase("1")){
					db.deleteFromWatchList(userID,"0000",obj_item.getTextExtra());
				}else{
					db.deleteFromWatchList(userID,"0000",obj_item.getTextExtra());
				}
				itemWatchList.remove(positionIndex);
				lvWatchList.setAdapter(new WatchListItemListBaseAdapter(
						F3Activity.this, itemWatchList));
			}
		});

		alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				// Canceled.
			}
		});

		alert.show();

	}
	String getWishListAttachments(){
		
		String qString="";
		
		ArrayList<String> arrList=new ArrayList<String>();
		
		ArrayList<ArrayList<String>> resultList;
		ArrayList<String> rowList;
		
		resultList=db.getWatchList(userID,"2");
		
		if(resultList!=null){
			
		for(int i=0;i<resultList.size();i++){
			
			rowList=resultList.get(i);
			
			if(i==0){
				if(rowList.get(4).equalsIgnoreCase("2")){
					qString=qString+rowList.get(2);
				}
				
								
			}else{
				if(rowList.get(4).equalsIgnoreCase("2")){
				qString=qString+","+rowList.get(2);
				}
			}
		}
		
		}
		return qString;
		
	}
String getWishListDocuments(){
		
		String qString="";
		
		ArrayList<String> arrList=new ArrayList<String>();
		
		ArrayList<ArrayList<String>> resultList;
		ArrayList<String> rowList;
		
		resultList=db.getWatchList(userID,"1");
		
		if(resultList!=null){
			
		for(int i=0;i<resultList.size();i++){
			
			rowList=resultList.get(i);
			
			if(i==0){
				if(rowList.get(4).equalsIgnoreCase("1")){
					qString=qString+rowList.get(3);
				}
				
								
			}else{
				if(rowList.get(4).equalsIgnoreCase("1")){
				qString=qString+","+rowList.get(3);
				}
			}
		}
		
		}
		return qString;
		
	}
} // end class
