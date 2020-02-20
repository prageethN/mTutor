package com.mtutor.recentviews;

import java.util.ArrayList;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.mtutor.dashboard.DashboardActivity;
import com.mtutor.dashboard.R;
import com.mtutor.notification.Alerts;
import com.mtutor.servicecall.WebServiceCaller;

public class RecentViewActivity extends DashboardActivity {
	ListView lvRecentView;
	ArrayList<ItemDetails> itemRecentView;

	TextView txtResultCount;
	WebServiceCaller webServiceCaller;
	Alerts alert;

	private static final String PREFS_NAME = "MyPrefsFile";
	private static final String PREF_USERID = "userid";
	
	String userID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recent_view);
		setTitleFromActivityLabel(R.id.title_text);
		setupStartUp();
	}

	void setupStartUp() {

webServiceCaller = new WebServiceCaller();
		
		SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		userID=pref.getString(PREF_USERID, null);
		
		try {
			userID = getIntent().getExtras().getSerializable("USER_ID")
			.toString();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		txtResultCount=(TextView)findViewById(R.id.txtResultCount);
		
		itemRecentView = getRecentViewList();
		lvRecentView = (ListView) findViewById(R.id.lvRecentView);
		lvRecentView.setAdapter(new RecentViewItemListBaseAdapter(this,
				itemRecentView));
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	ArrayList<ItemDetails> results = new ArrayList<ItemDetails>();

	private ArrayList<ItemDetails> getRecentViewList() {
		ArrayList<ArrayList> resultList=null;
		ArrayList<String> rowList=null;
		
				
		resultList=webServiceCaller.getUserViewList(userID);
				
				
		if(resultList!=null){
			
		for(int i=0;i<resultList.size();i++){
			
				rowList=resultList.get(i);
				
				ItemDetails item_details = new ItemDetails();
				item_details.setItemID(rowList.get(0));	
				item_details.setTextExtra(rowList.get(1).replace("anyType{}", ""));
				item_details.setTextHeader(rowList.get(2).replace("anyType{}", ""));
				item_details.setTextNormal(rowList.get(3).replace("anyType{}", ""));
				item_details.setTextFooter(rowList.get(4).replace("anyType{}", ""));	
				
				results.add(item_details);
			
		}
		txtResultCount.setText(+resultList.size()+" recent views");
		}
		/*ItemDetails item_details = new ItemDetails();
		item_details.setTextHeader("How to program Java");
		item_details.setTextNormal("Dr. Dinusha Kankanamge");
		item_details.setTextFooter("August 23,2012 | 9:30 GMT");
		results.add(item_details);*/

		

		return results;
	}
}
