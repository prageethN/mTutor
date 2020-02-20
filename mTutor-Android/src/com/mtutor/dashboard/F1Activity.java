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
import java.util.HashMap;

import com.mtutor.dashboard.R;
import com.mtutor.data.DatabaseHandler;
import com.mtutor.notification.MyProgressDialog;
import com.mtutor.publication.ItemInfoActivity;
import com.mtutor.scannar.ScanActivity;
import com.mtutor.search.SearchListBaseAdapter;
import com.mtutor.servicecall.WebServiceCaller;
import com.mtutor.support.ItemDetails;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

/**
 * This is the activity for feature 1 in the dashboard application. It displays
 * some text and provides a way to get back to the home activity.
 * 
 */

public class F1Activity extends CustomListActivity implements
		android.view.View.OnClickListener, OnItemClickListener {

	/**
	 * onCreate
	 * 
	 * Called when the activity is first created. This is where you should do
	 * all of your normal static set up: create views, bind data to lists, etc.
	 * This method also provides you with a Bundle containing the activity's
	 * previously frozen state, if there was one.
	 * 
	 * Always followed by onStart().
	 * 
	 * @param savedInstanceState
	 *            Bundle
	 */
	Button btnQRReadtActivity, btnSearch;
	ImageButton btnSaveSearch;
	EditText txtSearch;
	MyProgressDialog dialog;
	Thread background;

	ListView lvSearchResult;
	ArrayList<ItemDetails> itemSearchResult;

	WebServiceCaller webServiceCaller;
	SharedPreferences pref;
	DatabaseHandler db;
	
	private static final String PREFS_NAME = "MyPrefsFile";
	private static final String PREF_USERID = "userid";
	
	String userID;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_f1);
		setTitleFromActivityLabel(R.id.title_text);
		setStartUp();
	}

	public void setStartUp() {

		webServiceCaller = new WebServiceCaller();

		pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		userID = pref.getString(PREF_USERID, null);

		db = new DatabaseHandler(this);
		
		btnQRReadtActivity = (Button) findViewById(R.id.btnScan);
		btnQRReadtActivity.setOnClickListener(this);
		btnSaveSearch= (ImageButton) findViewById(R.id.btnSaveSearch);
		btnSearch = (Button) findViewById(R.id.btnSearch);
		btnSearch.setOnClickListener(this);
		txtSearch = (EditText) findViewById(R.id.txtSearch);

		lvSearchResult = getListView();
		int x = 10;
		/*
		 * txtSearch.addTextChangedListener(new TextWatcher(){ public void
		 * afterTextChanged(Editable s) { resultList.clear(); setUpResultList();
		 * 
		 * } public void beforeTextChanged(CharSequence s, int start, int count,
		 * int after){} public void onTextChanged(CharSequence s, int start, int
		 * before, int count){ dialog= new ProgressDialog(F1Activity.this);
		 * dialog = ProgressDialog.show(F1Activity.this, "",
		 * "Loading. Please wait...", true);
		 * dialog.setProgressStyle(R.style.NewDialog); dialog.show();
		 * 
		 * test(); InputMethodManager imm = (InputMethodManager)
		 * getSystemService(Context.INPUT_METHOD_SERVICE);
		 * imm.showSoftInput(txtSearch, InputMethodManager.SHOW_IMPLICIT);
		 * 
		 * } });
		 */

	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if (view == btnQRReadtActivity) {

			startQRCodeReader();
		}
		if (view == btnSearch) {
			dialog = MyProgressDialog.show(F1Activity.this, "",
					"Loading. Please wait...", true);
			test();
			populateListView();
		}
	}

	private void populateListView() {

		itemSearchResult = getSearchResultList();
		lvSearchResult.setAdapter(new SearchListBaseAdapter(this,
				itemSearchResult));
		lvSearchResult.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				Object o = lvSearchResult.getItemAtPosition(position);
				ItemDetails obj_itemDetails = (ItemDetails) o;

				Intent intent = new Intent(getApplicationContext(),
						ItemInfoActivity.class);
				intent.putExtra("DOC_ID", obj_itemDetails.getItemId());
				intent.putExtra("AUTHOR_ID", obj_itemDetails.getTextExtra());
				startActivity(intent);
			}
		});

	}

	private ArrayList<ItemDetails> getSearchResultList() {

		String searchQuery = "";
		searchQuery = txtSearch.getText().toString();

		ArrayList<ItemDetails> resultsList = new ArrayList<ItemDetails>();

		ArrayList<ArrayList> resultList = null;
		ArrayList<String> rowList = null;

		resultList = webServiceCaller.getDocumentSearchResult(searchQuery);

		if (resultList != null) {

			if (resultList.size() > 0) {

				for (int i = 0; i < resultList.size(); i++) {

					rowList = resultList.get(i);

					ItemDetails item_details = new ItemDetails();
					item_details.setItemID(rowList.get(0));
					item_details.setTextExtra(rowList.get(3).replace("anyType{}", ""));// author id
					item_details.setTextHeader(rowList.get(1).replace("anyType{}", ""));
					item_details.setTextNormal(rowList.get(4).replace("anyType{}", ""));
					item_details.setTextFooter(rowList.get(2).replace("anyType{}", ""));

					resultsList.add(item_details);

				}

			}

		}

		return resultsList;
	}

	public void startQRCodeReader() {
		try {
			Intent intent = new Intent("com.google.zxing.client.android.SCAN");
			intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
			startActivityForResult(intent, 0);

		} catch (Exception ex) {

			Toast.makeText(this, "Error in QR scanning", 10);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		String[] docInfo = null;
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				String content = intent.getStringExtra("SCAN_RESULT");
				Intent intnt = new Intent(getApplicationContext(),
						ItemInfoActivity.class);
				docInfo = getDocumentInfo(content);

				if (docInfo != null) {
					intent.putExtra("DOC_ID", docInfo[0]);
					intent.putExtra("AURTHOR_ID", docInfo[1]);
					startActivity(intnt);
				} else {
					toast("Invalid QR Code");
				}

			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(this, "Error in QR reading", 10).show();
			}
		}
	}

	String[] getDocumentInfo(String qrCode) {

		String[] resultSet = null;
		String[] docInfo = new String[2];

		resultSet = webServiceCaller.getDocumentInfoQR(qrCode);
		if (resultSet != null) {
			docInfo[0] = resultSet[0];
			docInfo[1] = resultSet[1];

		}
		return docInfo;

	}

	void test() {

		background = new Thread(new Runnable() {
			@Override
			public void run() {
				try {

					try {
						Thread.sleep(2000);

					} catch (InterruptedException e) {
						return;
					} catch (Exception e) {
						return;
					}

					dialog.dismiss();

				} catch (Throwable t) {
				}
			}
		});

		// start the background thread

		background.start();

	}
	public void onClickSaveSearch(View view){	
		
		
		String searchQuery=txtSearch.getText().toString();
		if(!searchQuery.equalsIgnoreCase("")){
			
			db.addSavedSearchItem(userID,searchQuery);
			btnSaveSearch.setVisibility(android.view.View.GONE);
			toast("Successfully saved to saved search list");
		}
		
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

} // end class
