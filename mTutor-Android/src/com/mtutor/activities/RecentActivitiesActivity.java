package com.mtutor.activities;

import java.util.ArrayList;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.mtutor.dashboard.DashboardActivity;
import com.mtutor.dashboard.R;
import com.mtutor.notification.Alerts;
import com.mtutor.servicecall.WebServiceCaller;

public class RecentActivitiesActivity extends DashboardActivity {

	ListView lvRecentActivity;
	ArrayList<ItemDetails> itemRecentActivity;

	TextView txtResultCount;
	WebServiceCaller webServiceCaller;
	Alerts alert;

	private static final String PREFS_NAME = "MyPrefsFile";
	private static final String PREF_USERID = "userid";
	public static final String PREFS_FULLNAME = "fullname";
	
	String userID,fullName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recent_activity);
		setTitleFromActivityLabel(R.id.title_text);
		setupStartUp();
	}

	void setupStartUp() {

		webServiceCaller = new WebServiceCaller();
		
		SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		userID=pref.getString(PREF_USERID, null);
		fullName=pref.getString(PREFS_FULLNAME, null);
		
		try {
			userID = getIntent().getExtras().getSerializable("USER_ID")
			.toString();
			fullName = getIntent().getExtras().getSerializable("FULL_NAME")
			.toString();
		} catch (Exception e) {
			// TODO: handle exception
		}

					
		txtResultCount=(TextView)findViewById(R.id.txtResultCount);
		
		itemRecentActivity = getRecentActivityList();
		lvRecentActivity = (ListView) findViewById(R.id.lvRecentActivity);
		lvRecentActivity.setAdapter(new RecentActivityItemListBaseAdapter(this,
				itemRecentActivity));
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

	private ArrayList<ItemDetails> getRecentActivityList() {
		ArrayList<ArrayList> resultList=null;
		ArrayList<String> rowList=null;
		
				
		resultList=webServiceCaller.getUserActivityList(userID);
				
				
		if(resultList!=null){
			
		for(int i=0;i<resultList.size();i++){
			
				rowList=resultList.get(i);
				
				ItemDetails item_details = new ItemDetails();
				item_details.setItemID(rowList.get(0));					
				item_details.setTextHeader(fullName+" "+rowList.get(6));
				item_details.setTextNormal(rowList.get(9));
				item_details.setTextFooter(rowList.get(8));	
				
				results.add(item_details);
			
		}
		txtResultCount.setText(+resultList.size()+" recent views");
		}
		/*String emptyText = "";
		ItemDetails item_details = new ItemDetails();
		item_details.setTextHeader("Maheshika changed profile picture");
		item_details.setTextNormal(emptyText);
		item_details.setTextFooter("August 23,2012 | 9:30 GMT");
		results.add(item_details);

		item_details = new ItemDetails();
		item_details
				.setTextHeader("Maheshika commented on 'How to program java' by Mr. Dinusha Kankanamge");
		item_details.setTextNormal(emptyText);
		item_details.setTextFooter("August 20,2012 | 9:35 GMT");
		results.add(item_details);*/

		return results;
	}
}
