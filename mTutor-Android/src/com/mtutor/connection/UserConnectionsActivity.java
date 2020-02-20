package com.mtutor.connection;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

public class UserConnectionsActivity extends DashboardActivity {
	ListView lvUserConnection;
	ArrayList<ItemDetails> itemUserConnection;

	WebServiceCaller webServiceCaller;
	Alerts alert;
	
	TextView txtResultHeading;
	
	String userID;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userconnections);
		setTitleFromActivityLabel(R.id.title_text);
		setStartUp();
	}

	void setStartUp() {
		
		webServiceCaller = new WebServiceCaller();
		alert = new Alerts(this);
		try {
			userID = getIntent().getExtras().getSerializable("USER_ID")
			.toString();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		txtResultHeading=(TextView)findViewById(R.id.txtResultHeading);
		
		itemUserConnection = getUserConnectionList();
		lvUserConnection = (ListView) findViewById(R.id.lvUserConnection);
		lvUserConnection.setAdapter(new ConnectionItemListBaseAdapter(this,
				itemUserConnection));

		lvUserConnection.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				Object o = lvUserConnection.getItemAtPosition(position);
				ItemDetails obj_itemDetails = (ItemDetails) o;
				Intent intent = new Intent(getApplicationContext(),
						UserProfileActivity.class);
				intent.putExtra("USER_ID", obj_itemDetails.getItemId());
				startActivity(intent);
			}
		});
		lvUserConnection
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
		Object o = lvUserConnection.getItemAtPosition(position);
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

	private ArrayList<ItemDetails> getUserConnectionList() {
		ArrayList<ArrayList> resultList=null;
		ArrayList<String> rowList=null;
		
				
		resultList=webServiceCaller.getUserContactList(userID);
				
				
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
		txtResultHeading.setText(+resultList.size()+" Connections");
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

		alert.setTitle("Add Connection?");
		alert.setMessage(rowText);
		// Set an EditText view to get user input

		final int positionIndex = position;
		alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				Toast.makeText(UserConnectionsActivity.this,
						"Successfully added to connections", Toast.LENGTH_LONG)
						.show();

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
}
