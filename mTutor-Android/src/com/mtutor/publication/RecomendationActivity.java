package com.mtutor.publication;

import java.util.ArrayList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.mtutor.dashboard.DashboardActivity;
import com.mtutor.dashboard.R;
import com.mtutor.servicecall.WebServiceCaller;

public class RecomendationActivity extends DashboardActivity {

	ListView lvRecomendation;
	ArrayList<ItemDetails> itemRecomendation;

	WebServiceCaller webServiceCaller;
	SharedPreferences pref;
	
	String docID,userID;
	
	private static final String PREFS_NAME = "MyPrefsFile";
	private static final String PREF_USERID = "userid";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recomendation);
		setTitleFromActivityLabel(R.id.title_text);
		setupStartup();
	}

	void setupStartup() {

		webServiceCaller = new WebServiceCaller();

		pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		userID = pref.getString(PREF_USERID, null);
		
		if (getIntent().getExtras().getSerializable("DOC_ID") != null) {

			docID = getIntent().getExtras().getSerializable("DOC_ID")
					.toString();
		}
		ArrayList<ItemDetails> itemRecomendation = GetRecomendedItems();
		lvRecomendation = (ListView) findViewById(R.id.lvRecomendation);
		lvRecomendation.setAdapter(new ItemListBaseAdapter(this,
				itemRecomendation));

		lvRecomendation.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				Object o = lvRecomendation.getItemAtPosition(position);
				ItemDetails obj_itemDetails = (ItemDetails) o;
				Intent intent = new Intent(getApplicationContext(),
						ItemInfoActivity.class);
				intent.putExtra("DOC_ID", obj_itemDetails.getItemId());
				intent.putExtra("AUTHOR_ID", obj_itemDetails.getTextExtra());
				startActivity(intent);
			}
		});
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

	private ArrayList<ItemDetails> GetRecomendedItems() {
		ArrayList<ItemDetails> results = new ArrayList<ItemDetails>();

		ArrayList<ArrayList> resultList = null;
		ArrayList<String> rowList = null;

		String qString=getQueryString();
		//resultList = webServiceCaller.getSuggetionList(docID);
		resultList = webServiceCaller.getSuggetionList(qString);

		if (resultList != null) {

			if (resultList.size() > 0) {

				for (int i = 0; i < resultList.size(); i++) {

					rowList = resultList.get(i);

					ItemDetails item_details = new ItemDetails();
					item_details.setItemID(rowList.get(0));
					item_details.setTextHeader(rowList.get(1));
					item_details.setTextNormal(getDocumentRatingInfo(
							rowList.get(3), rowList.get(4), rowList.get(5)));
					item_details.setTextFooter(rowList.get(2));
					item_details.setTextExtra(rowList.get(6));
					results.add(item_details);

				}

			}

		}

		return results;
	}
	String getQueryString(){
	
	String qString="";
	String resultArr[]=webServiceCaller.getSuggetionDocumentList(userID,docID);
	
	if(resultArr!=null){
		for(int i=0;i<resultArr.length;i++){
			
			if(i!=0){
				qString+=","+resultArr[i];
			}else{
				
				qString+=resultArr[i];
			}
			
		}
		
	
	}
	return qString;
}
	String getDocumentRatingInfo(String viewCount, String likeCount,
			String dislikeCount) {

		String rateString = "";

		String[] docRating = webServiceCaller.getDocumentRating(docID);
		if (docRating != null) {
			rateString = viewCount + " views," + likeCount + " likes,"
					+ dislikeCount + " dislikes";

		}
		return rateString;

	}
}