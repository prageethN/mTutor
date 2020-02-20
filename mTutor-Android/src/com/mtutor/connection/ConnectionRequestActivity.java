package com.mtutor.connection;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.mtutor.dashboard.DashboardActivity;
import com.mtutor.dashboard.R;
import com.mtutor.notification.Alerts;
import com.mtutor.servicecall.WebServiceCaller;
import com.mtutor.user.UserProfileActivity;

public class ConnectionRequestActivity extends DashboardActivity {
	ListView lvReqConnection;
	ArrayList<ItemDetails> itemReqConnection;

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
		setContentView(R.layout.activity_connection_request);
		setTitleFromActivityLabel(R.id.title_text);
		setupStartUp();
	}

	void setupStartUp() {
		
		webServiceCaller = new WebServiceCaller();
		
		SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		userID=pref.getString(PREF_USERID, null);
		
		txtResultCount=(TextView)findViewById(R.id.txtResultCount);
		
		itemReqConnection = getReqConnectionList();
		lvReqConnection = (ListView) findViewById(R.id.lvReqConnection);
		lvReqConnection.setAdapter(new ConnectionItemListBaseAdapter(this,
				itemReqConnection));

		lvReqConnection.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				Object o = lvReqConnection.getItemAtPosition(position);
				ItemDetails obj_itemDetails = (ItemDetails) o;
				Intent intent = new Intent(getApplicationContext(),
						UserProfileActivity.class);
				intent.putExtra("USER_ID", obj_itemDetails.getItemId());
				startActivity(intent);
			}
		});
		lvReqConnection
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
		Object o = lvReqConnection.getItemAtPosition(position);
		ItemDetails obj_itemDetails = (ItemDetails) o;
		String rowText = obj_itemDetails.getTextHeader();
		deleteRow(rowText, position);
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

	private ArrayList<ItemDetails> getReqConnectionList() {

		ArrayList<ArrayList> resultList=null;
		ArrayList<String> rowList=null;
		
				
		resultList=webServiceCaller.getUserContactRequestList(userID);
				
				
		if(resultList!=null){
			
		for(int i=0;i<resultList.size();i++){
			
				rowList=resultList.get(i);
				
				ItemDetails item_details = new ItemDetails();
				item_details.setItemID(rowList.get(0));				
				item_details.setTextHeader(rowList.get(1));
				item_details.setTextNormal(rowList.get(2));
				item_details.setTextFooter(rowList.get(3));	
				
				results.add(item_details);
			
		}
		txtResultCount.setText(+resultList.size()+" Connection requests");
		}
		/*ItemDetails item_details = new ItemDetails();
		item_details.setTextHeader("Dinusha Kankanamge");
		item_details.setTextNormal("Undergraduate");
		item_details.setTextFooter("University of Moratuwa");
		results.add(item_details);*/

		
		return results;
	}

	void deleteRow(String rowText, int position) {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Respond to connection request");
		alert.setMessage(rowText);
		// Set an EditText view to get user input

		final int positionIndex = position;
		alert.setPositiveButton("Accept",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
						Toast.makeText(ConnectionRequestActivity.this,
								"Successfully added to connections",
								Toast.LENGTH_LONG).show();
						itemReqConnection.remove(positionIndex);
						lvReqConnection
								.setAdapter(new ConnectionItemListBaseAdapter(
										ConnectionRequestActivity.this,
										itemReqConnection));
					}
				});

		alert.setNeutralButton("Reject", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				itemReqConnection.remove(positionIndex);
				lvReqConnection.setAdapter(new ConnectionItemListBaseAdapter(
						ConnectionRequestActivity.this, itemReqConnection));
			}
		});

		alert.setNegativeButton("Cacel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				// Cancel
			}
		});
		alert.show();

	}
}
