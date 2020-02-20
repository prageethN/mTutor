package com.mtutor.connection;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mtutor.dashboard.DashboardActivity;
import com.mtutor.dashboard.R;
import com.mtutor.notification.Alerts;
import com.mtutor.servicecall.WebServiceCaller;

public class ConnectionsActivity extends DashboardActivity implements
		OnClickListener {

	RelativeLayout relLayoutMyConnecions, relLayoutAddConnections,
			relLayoutConnectionRequest;
			TextView txtConnectionCount,txtMyConnectionCount,txtConnectionReqCount;
			
			WebServiceCaller webServiceCaller;
			Alerts alert;

			private static final String PREFS_NAME = "MyPrefsFile";
			private static final String PREF_USERID = "userid";
			
			String userID;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connections);
		setTitleFromActivityLabel(R.id.title_text);
		setStartUp();

	}

	public void setStartUp() {
		
		webServiceCaller = new WebServiceCaller();
		
		SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		userID=pref.getString(PREF_USERID, null);
		
		relLayoutMyConnecions = (RelativeLayout) findViewById(R.id.relLayoutMyConnecions);
		relLayoutMyConnecions.setOnClickListener(this);
		relLayoutAddConnections = (RelativeLayout) findViewById(R.id.relLayoutAddConnections);
		relLayoutAddConnections.setOnClickListener(this);
		relLayoutConnectionRequest = (RelativeLayout) findViewById(R.id.relLayoutConnectionRequest);
		relLayoutConnectionRequest.setOnClickListener(this);
		
		txtConnectionCount=(TextView)findViewById(R.id.txtConnectionCount);
		txtMyConnectionCount=(TextView)findViewById(R.id.txtMyConnectionCount);
		txtConnectionReqCount=(TextView)findViewById(R.id.txtConnectionReqCount);
		
		setProfileCount();
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if (view == relLayoutMyConnecions) {
			Intent i = new Intent(getApplicationContext(),
					MyConnectionsActivity.class);
			startActivity(i);
		}
		if (view == relLayoutAddConnections) {
			Intent i = new Intent(getApplicationContext(),
					AddConnectionActivity.class);
			startActivity(i);
		}
		if (view == relLayoutConnectionRequest) {
			Intent i = new Intent(getApplicationContext(),
					ConnectionRequestActivity.class);
			startActivity(i);
		}
	}
void setProfileCount(){
		
		String[] userProfile_Count = null;
		userProfile_Count = webServiceCaller.getUserProfileCount(userID);
		
		txtMyConnectionCount.setText(userProfile_Count[2]+" connections");
		txtConnectionCount.setText(userProfile_Count[3]+" registered users");
		txtConnectionReqCount.setText(userProfile_Count[4]+" connection requests");
		
		
	}
}
