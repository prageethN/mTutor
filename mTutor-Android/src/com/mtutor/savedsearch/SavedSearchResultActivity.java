package com.mtutor.savedsearch;

import java.util.ArrayList;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.mtutor.dashboard.DashboardActivity;
import com.mtutor.dashboard.R;
import com.mtutor.data.DatabaseHandler;
import com.mtutor.servicecall.WebServiceCaller;


public class SavedSearchResultActivity extends DashboardActivity {

	ListView lvSavedSeachList;
	ArrayList<ItemDetails> itemSavedSearch;
	
	TextView txtSearchTerm,txtResultCount;

	WebServiceCaller webServiceCaller;
	SharedPreferences pref;
	DatabaseHandler db;
	
	private static final String PREFS_NAME = "MyPrefsFile";
	private static final String PREF_USERID = "userid";
		
	String userID,searchQuery="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_saved_search);
		setTitleFromActivityLabel(R.id.title_text);
		setupStartUp();
	}

	void setupStartUp() {

		webServiceCaller = new WebServiceCaller();

		pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		userID = pref.getString(PREF_USERID, null);

		if (getIntent().getExtras().getSerializable("SEARCH_QUERY") != null) {

			searchQuery = getIntent().getExtras().getSerializable("SEARCH_QUERY")
					.toString();
			
		}
		
		db = new DatabaseHandler(this);
		
		txtSearchTerm=(TextView)findViewById(R.id.txtSearchTerm);
		txtResultCount=(TextView)findViewById(R.id.txtResultCount);
		
		txtSearchTerm.setText(searchQuery);
		
		itemSavedSearch = getSavedSearchList();
		lvSavedSeachList = (ListView) findViewById(R.id.lvSavedSearchResult);
		lvSavedSeachList.setAdapter(new SavedSearchItemListBaseAdapter(this,
				itemSavedSearch));
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

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	

	private ArrayList<ItemDetails> getSavedSearchList() {
		ArrayList<ItemDetails> results = new ArrayList<ItemDetails>();
		
		ArrayList<ArrayList> resultList = null;
		ArrayList<String> rowList = null;

		resultList = webServiceCaller.getDocumentSearchResult(searchQuery);

		if (resultList != null) {

			if (resultList.size() > 0) {

				for (int i = 0; i < resultList.size(); i++) {

					rowList = resultList.get(i);

					ItemDetails item_details = new ItemDetails();
					item_details.setItemID(rowList.get(0));					
					item_details.setTextHeader(rowList.get(1));
					item_details.setTextNormal(rowList.get(4));
					item_details.setTextFooter(rowList.get(2));

					results.add(item_details);

				}

			}
			txtResultCount.setText(results.size()+" results");
		}
		
		/*ItemDetails item_details = new ItemDetails();
		item_details.setTextHeader("prageethkandy");
		item_details.setTextNormal("Waaw, this is awesome..");
		item_details.setTextFooter("August 23,2012 | 9:30 GMT");
		results.add(item_details);
		*/

		return results;
	}
}
