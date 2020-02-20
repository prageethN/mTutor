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
import com.mtutor.events.EventsItemListBaseAdapter;
import com.mtutor.events.ItemDetails;
import com.mtutor.publication.ImageItemDetails;
import com.mtutor.servicecall.WebServiceCaller;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class F4Activity extends DashboardActivity {
	ListView lvEventList;
	ArrayList<ItemDetails> itemEventList;
	TextView txtHeaderText;
	
	WebServiceCaller webServiceCaller;
	SharedPreferences pref;

	private static final String PREFS_NAME = "MyPrefsFile";
	private static final String PREF_USERID = "userid";
	
	String userID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_f4);
		setTitleFromActivityLabel(R.id.title_text);
		setupStartUp();
	}

	void setupStartUp() {

		webServiceCaller = new WebServiceCaller();

		pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		userID = pref.getString(PREF_USERID, null);
		
		txtHeaderText=(TextView)findViewById(R.id.txtHeaderText);
		
		itemEventList = getEventsList();
		lvEventList = (ListView) findViewById(R.id.lvEvents);
		lvEventList.setAdapter(new EventsItemListBaseAdapter(this,
				itemEventList));
	}

	

	private ArrayList<ItemDetails> getEventsList() {
		ArrayList<ItemDetails> results = new ArrayList<ItemDetails>();
		
		ArrayList<ArrayList> resultList = null;
		ArrayList<String> rowList = null;

		resultList = webServiceCaller.getUserEvent(userID);

		if (resultList != null) {

			if (resultList.size() > 0) {

				for (int i = 0; i < resultList.size(); i++) {

					rowList = resultList.get(i);

					ItemDetails item_details = new ItemDetails();
					item_details.setItemID(rowList.get(0));
					item_details.setTextHeader(rowList.get(6));
					item_details.setTextNormal(rowList.get(3));
					item_details.setTextFooter(rowList.get(2));
					
					results.add(item_details);

				}

			}
			txtHeaderText.setText(resultList.size()+" events");
		}

		/*ItemDetails item_details = new ItemDetails();
		item_details.setTextHeader("How to program java");
		item_details.setTextNormal("New content was added | video");
		item_details.setTextFooter("August 23,2012 | 9:30 GMT");
		results.add(item_details);*/
		

		return results;
	}

} // end class
