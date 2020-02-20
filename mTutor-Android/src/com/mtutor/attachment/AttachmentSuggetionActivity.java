package com.mtutor.attachment;

import java.util.ArrayList;

import com.mtutor.dashboard.DashboardActivity;
import com.mtutor.dashboard.R;
import com.mtutor.publication.ImageItemDetails;
import com.mtutor.publication.ImageItemListBaseAdapter;
import com.mtutor.publication.ItemDetails;
import com.mtutor.servicecall.WebServiceCaller;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author Adil Soomro
 * 
 */
public class AttachmentSuggetionActivity extends DashboardActivity {

	ListView lvRelated;
	ArrayList<ItemDetails> itemRelated;

	WebServiceCaller webServiceCaller;
	SharedPreferences pref;
	
	String docID, attID,userID;

	private static final String PREFS_NAME = "MyPrefsFile";
	private static final String PREF_USERID = "userid";
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_attachment_suggestion);

		setupStartup();
	}

	void setupStartup() {

		webServiceCaller = new WebServiceCaller();
		
		pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		userID = pref.getString(PREF_USERID, null);
		
		if (getIntent().getExtras().getSerializable("ATT_ID") != null) {

			docID = getIntent().getExtras().getSerializable("DOC_ID")
					.toString();
			attID = getIntent().getExtras().getSerializable("ATT_ID")
					.toString();
		}

		ArrayList<ImageItemDetails> relatedItems = GetRelatedItems();
		lvRelated = (ListView) findViewById(R.id.lvRelated);
		lvRelated.setAdapter(new ImageItemListBaseAdapter(this, relatedItems));
		lvRelated.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				Object o = lvRelated.getItemAtPosition(position);
				ImageItemDetails obj_itemDetails = (ImageItemDetails) o;
				Intent intent = new Intent(getApplicationContext(),
						AttachmentActivity.class);
				intent.putExtra("ATT_ID", obj_itemDetails.getItemId());
				intent.putExtra("DOC_ID", obj_itemDetails.getTextOptional());
				startActivity(intent);
			}
		});
	}

	private ArrayList<ImageItemDetails> GetRelatedItems() {
		ArrayList<ImageItemDetails> results = new ArrayList<ImageItemDetails>();

		ArrayList<ArrayList> resultList = null;
		ArrayList<String> rowList = null;

		String qString=getQueryString();
		
		resultList = webServiceCaller.getAttachmentSuggetionList(qString);

		if (resultList != null) {

			if (resultList.size() > 0) {

				for (int i = 0; i < resultList.size(); i++) {

					rowList = resultList.get(i);

					ImageItemDetails item_details = new ImageItemDetails();
					item_details.setItemID(rowList.get(0));
					item_details.setTextHeader(rowList.get(2));
					item_details.setTextNormal(rowList.get(4));
					item_details.setTextFooter(rowList.get(3) + " | "
							+ rowList.get(5) + " views");
					item_details.setTextExtra(getAttachmentType(Integer
							.parseInt(rowList.get(1))));
					item_details
							.setImageNumber(Integer.parseInt(rowList.get(1)));
					item_details.setTextOptional(rowList.get(6));
					results.add(item_details);

				}

			}

		}

		/*
		 * ImageItemDetails item_details = new ImageItemDetails();
		 * item_details.setTextHeader("Hello world with Java");
		 * item_details.setTextNormal("Prof. Isuru Jayasooriya");
		 * item_details.setTextFooter("August 23,2012 | 121 views");
		 * item_details.setTextExtra("video"); item_details.setImageNumber(1);
		 * results.add(item_details);
		 */

		return results;
	}
	String getQueryString(){
		
		String qString="";
		String resultArr[]=webServiceCaller.getSuggetionAttachmentList(userID,attID);
		
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
	String getAttachmentType(int typeFlg) {

		String attType = "";

		switch (typeFlg) {
		case 1:
			attType = "Video";
			break;
		case 2:
			attType = "PDF";
			break;
		case 3:
			attType = "Image";
			break;
		case 4:
			attType = "MS Word";
			break;
		case 5:
			attType = "MS Powerpoint";
			break;
		case 6:
			attType = "MS Excel";
			break;

		default:
			attType = "Other";
			break;
		}
		return attType;
	}
}
