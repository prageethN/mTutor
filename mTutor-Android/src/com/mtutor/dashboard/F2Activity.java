package com.mtutor.dashboard;

import java.util.ArrayList;
import com.mtutor.dashboard.R;
import com.mtutor.data.DatabaseHandler;
import com.mtutor.publication.ImageItemDetails;
import com.mtutor.savedsearch.ItemListBaseAdapter;
import com.mtutor.savedsearch.ItemDetails;
import com.mtutor.savedsearch.SavedSearchResultActivity;
import com.mtutor.servicecall.WebServiceCaller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class F2Activity extends DashboardActivity implements
		android.view.View.OnClickListener {

	ListView lvSavedSeach;
	ArrayList<ItemDetails> itemSavedSearch;

	WebServiceCaller webServiceCaller;
	SharedPreferences pref;
	DatabaseHandler db;
	
	private static final String PREFS_NAME = "MyPrefsFile";
	private static final String PREF_USERID = "userid";
	
	String userID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_f2);
		setTitleFromActivityLabel(R.id.title_text);
		setupStartUp();
	}

	void setupStartUp() {

		webServiceCaller = new WebServiceCaller();

		pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		userID = pref.getString(PREF_USERID, null);

		db = new DatabaseHandler(this);
		
		itemSavedSearch = getSavedSearch();
		lvSavedSeach = (ListView) findViewById(R.id.lvSavedSearch);
		lvSavedSeach.setAdapter(new ItemListBaseAdapter(this, itemSavedSearch));

		lvSavedSeach.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {

				Object o = lvSavedSeach.getItemAtPosition(position);
				ItemDetails obj_itemDetails = (ItemDetails) o;
				Intent intent = new Intent(F2Activity.this,
						SavedSearchResultActivity.class);
				intent.putExtra("SEARCH_QUERY", obj_itemDetails.getTextHeader());
				startActivity(intent);
			}
		});

		lvSavedSeach
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
		Object o = lvSavedSeach.getItemAtPosition(position);
		ItemDetails obj_itemDetails = (ItemDetails) o;
		String rowText = obj_itemDetails.getTextHeader();
		deleteRow(rowText, position);
	}

	

	private ArrayList<ItemDetails> getSavedSearch() {
		
		ArrayList<ItemDetails> results = new ArrayList<ItemDetails>();

		ArrayList<ArrayList> resultList = null;
		ArrayList<String> rowList = null;

		resultList = getSavedSearchList();

		if (resultList != null) {

			if (resultList.size() > 0) {

				for (int i = 0; i < resultList.size(); i++) {

					rowList = resultList.get(i);

					ItemDetails item_details = new ItemDetails();
					item_details.setTextHeader(rowList.get(1));
					item_details.setNotifyEnbale(isNotifyEnabled(Integer.parseInt(rowList.get(2))));
					item_details.setImageNumber(getNewResultAvailableFlg());
									 	
					results.add(item_details);

				}

			}
			
		}
		/*ItemDetails item_details = new ItemDetails();
		item_details.setTextHeader("Java basics");
		item_details.setNotifyEnbale(true);
		item_details.setImageNumber(1);
		results.add(item_details);*/
		

		return results;
	}
public Boolean isNotifyEnabled(int notifyFlg){
		
		Boolean notifyEnbaled=false;
		
		switch (notifyFlg) {
		case 1:
			notifyEnbaled=true;
			break;
		}
		return notifyEnbaled;
	}
	
	public int getNewResultAvailableFlg(){
		
		int resultFlg=0,randomNo;
		randomNo=(int)Math.random()*10;
		
		if(randomNo>5){
			resultFlg=1;
		}
		
		return resultFlg;
	}
	ArrayList<ArrayList> getSavedSearchList(){

		ArrayList<ArrayList> resultList;				
		resultList=db.getSavedSearch(userID);	
		
		return resultList;
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	void deleteRow(String rowText, int position) {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Delete saved search?");
		alert.setMessage(rowText);
		// Set an EditText view to get user input

		final int positionIndex = position;
		alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {

				itemSavedSearch.remove(positionIndex);
				lvSavedSeach.setAdapter(new ItemListBaseAdapter(
						F2Activity.this, itemSavedSearch));
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

} // end class
