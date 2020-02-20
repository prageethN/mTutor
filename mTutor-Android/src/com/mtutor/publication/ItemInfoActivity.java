package com.mtutor.publication;

import java.util.ArrayList;

import com.mtutor.dashboard.DashboardActivity;
import com.mtutor.dashboard.R;
import com.mtutor.data.DatabaseHandler;
import com.mtutor.servicecall.WebServiceCaller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ItemInfoActivity extends DashboardActivity implements
		OnClickListener {

	Button btnExpand;
	TextView txtDescriptionLess, txtDescriptionMore, txtItemTitle, txtItemDate,
			txtItemStatistics, txtAuthorName, txtAuthorDesignation,
			txtWorkPlace, txtDocCategory, txtAttCount, txtAttSummary;
	ImageButton  ivLike, ivDislike,btnAddWatchList;
	RelativeLayout relLayoutAttachments, relLayoutRecomendation;

	WebServiceCaller webServiceCaller;
	SharedPreferences pref;
	DatabaseHandler db;

	private static final String PREFS_NAME = "MyPrefsFile";
	private static final String PREF_USERID = "userid";

	String docID, authorID, userID;
	int rateFlg = 0,actionFlg=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_iteminfo);
		setTitleFromActivityLabel(R.id.title_text);
		setStartUp();
	}

	void setStartUp() {

		webServiceCaller = new WebServiceCaller();

		pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		userID = pref.getString(PREF_USERID, null);
		
		db = new DatabaseHandler(this);
		

		if (getIntent().getExtras().getSerializable("DOC_ID") != null) {

			docID = getIntent().getExtras().getSerializable("DOC_ID")
					.toString();
			authorID = getIntent().getExtras().getSerializable("AUTHOR_ID")
					.toString();
		}

		btnExpand = (Button) findViewById(R.id.btnExpand);
		btnExpand.setOnClickListener(this);

		txtDescriptionMore = (TextView) findViewById(R.id.txtDescription_more);
		txtDescriptionLess = (TextView) findViewById(R.id.txtDescription_less);

		txtItemTitle = (TextView) findViewById(R.id.txtItemTitle);
		txtItemDate = (TextView) findViewById(R.id.txtItemDate);
		txtItemStatistics = (TextView) findViewById(R.id.txtItemStatistics);
		txtAuthorName = (TextView) findViewById(R.id.txtAuthorName);
		txtAuthorDesignation = (TextView) findViewById(R.id.txtAuthorDesignation);
		txtWorkPlace = (TextView) findViewById(R.id.txtWorkPlace);
		txtDocCategory = (TextView) findViewById(R.id.txtDocCategory);
		txtAttCount = (TextView) findViewById(R.id.txtAttCount);
		txtAttSummary = (TextView) findViewById(R.id.txtAttSummary);

		ivLike = (ImageButton ) findViewById(R.id.ivLike);
		ivLike.setOnClickListener(this);
		ivDislike = (ImageButton ) findViewById(R.id.ivDislike);
		ivDislike.setOnClickListener(this);
		btnAddWatchList = (ImageButton ) findViewById(R.id.btnAddWatchList);
		
		relLayoutAttachments = (RelativeLayout) findViewById(R.id.relLayoutAttachments);
		relLayoutAttachments.setOnClickListener(this);
		relLayoutRecomendation = (RelativeLayout) findViewById(R.id.relLayoutRecomendation);
		relLayoutRecomendation.setOnClickListener(this);

		setDocumentInfo();
		setMyRating();
		setAuthorInfo();
		setDocumentRatingInfo();
		setAttachmentInfo();
		setQuickSuggetions();
		saveDocumentView();
		setAdvertAction();
	}

	void setDocumentInfo() {

		String[] docInfo = webServiceCaller.getDocumentInfo(docID);
		if (docInfo != null) {

			txtItemTitle.setText(docInfo[2]);
			txtItemDate.setText(docInfo[3]);
			txtDescriptionLess.setText(docInfo[5]);
			txtDescriptionMore.setText(docInfo[5]);
			txtDocCategory.setText(docInfo[6]);
		}

	}

	void setAuthorInfo() {

		String[] authorInfo = webServiceCaller.getAurthorProfile(authorID);
		if (authorInfo != null) {

			txtAuthorName.setText(authorInfo[8].replace("anyType{}", "") + authorInfo[2].replace("anyType{}", ""));
			txtAuthorDesignation.setText(authorInfo[3].replace("anyType{}", ""));
			txtWorkPlace.setText(authorInfo[4].replace("anyType{}", ""));

		}

	}

	void setDocumentRatingInfo() {

		String rateString = "";

		String[] docRating = webServiceCaller.getDocumentRating(docID);
		if (docRating != null) {
			rateString = docRating[0] + " views," + docRating[1] + " likes,"
					+ docRating[2] + " dislikes";
			txtItemStatistics.setText(rateString);
		}
	}

	void setAttachmentInfo() {

		String attString = "";
		String[] attType = { "", "video", "PDF", "Image", "MS Word",
				"MS Powerpoint", "MS Excel", "Other" };
		String[] docAttachment = webServiceCaller.getAttachmentCount(docID);
		if (docAttachment != null) {

			txtAttCount.setText("This document has " + docAttachment[0]
					+ " attachments");

			for (int count = 1; count < docAttachment.length; count++) {

				if (!docAttachment[count].equalsIgnoreCase("0")) {

					if (attString.equalsIgnoreCase("")) {
						attString = attString + docAttachment[count] + " "
								+ attType[count];
					} else {
						attString = attString + "," + docAttachment[count]
								+ " " + attType[count];
					}
				}
			}
	
			txtAttSummary.setText(attString);
		}
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if (view == btnExpand) {

			if (btnExpand.getText().toString() == "More") {
				txtDescriptionLess.setVisibility(android.view.View.GONE);
				txtDescriptionMore.setVisibility(android.view.View.VISIBLE);
				btnExpand.setText("Less");
				btnExpand.setBackgroundResource(R.drawable.icn_arrow_up);
			} else {
				txtDescriptionMore.setVisibility(android.view.View.GONE);
				txtDescriptionLess.setVisibility(android.view.View.VISIBLE);
				btnExpand.setText("More");
				btnExpand.setBackgroundResource(R.drawable.icn_arrow_down);
			}

		}
		if (view == ivLike) {
			if (rateFlg == 0) {
				saveUserRating("1");
			} else {
				updateUserRating("1");
			}
			setDocumentRatingInfo();
			ivLike.setImageResource(R.drawable.like_pressed);
			ivDislike.setImageResource(R.drawable.dislike);
		}
		if (view == ivDislike) {
			if (rateFlg == 0) {
				saveUserRating("0");
			} else {
				updateUserRating("0");
			}
			setDocumentRatingInfo();
			ivDislike.setImageResource(R.drawable.dislike_pressed);
			ivLike.setImageResource(R.drawable.like);
		}

		if (view == relLayoutAttachments) {

			Intent intent = new Intent(getApplicationContext(),
					AttachmentListActivity.class);
			intent.putExtra("DOC_ID", docID);
			startActivity(intent);

		}
		if (view == relLayoutRecomendation) {

			Intent intent = new Intent(getApplicationContext(),
					RecomendationActivity.class);
			intent.putExtra("DOC_ID", docID);
			startActivity(intent);
		}
	}

	/*
	 * --------------------------------------------------------------------------
	 * -----------
	 */

	TextView txtItemTitle_1, txtItemTitle_2, txtItemTitle_3, txtItemTitle_4,
			txtItemDate_1, txtItemDate_2, txtItemDate_3, txtItemDate_4,
			txtItemStatistics_1, txtItemStatistics_2, txtItemStatistics_3,
			txtItemStatistics_4;

	LinearLayout lnSuggestion_1, lnSuggestion_2, lnSuggestion_3,
			lnSuggestion_4;

	ArrayList<ImageItemDetails> results = new ArrayList<ImageItemDetails>();
	ArrayList<ArrayList> resultListSuggetion = null;
	ArrayList<String> rowList = null;

	void setQuickSuggetions() {

		lnSuggestion_1 = (LinearLayout) findViewById(R.id.lnSuggestion_1);
		lnSuggestion_2 = (LinearLayout) findViewById(R.id.lnSuggestion_2);
		lnSuggestion_3 = (LinearLayout) findViewById(R.id.lnSuggestion_3);
		lnSuggestion_4 = (LinearLayout) findViewById(R.id.lnSuggestion_4);

		txtItemTitle_1 = (TextView) findViewById(R.id.txtItemTitle_1);
		txtItemTitle_2 = (TextView) findViewById(R.id.txtItemTitle_2);
		txtItemTitle_3 = (TextView) findViewById(R.id.txtItemTitle_3);
		txtItemTitle_4 = (TextView) findViewById(R.id.txtItemTitle_4);
		txtItemDate_1 = (TextView) findViewById(R.id.txtItemDate_1);
		txtItemDate_2 = (TextView) findViewById(R.id.txtItemDate_2);
		txtItemDate_3 = (TextView) findViewById(R.id.txtItemDate_3);
		txtItemDate_4 = (TextView) findViewById(R.id.txtItemDate_4);
		txtItemStatistics_1 = (TextView) findViewById(R.id.txtItemStatistics_1);
		txtItemStatistics_2 = (TextView) findViewById(R.id.txtItemStatistics_2);
		txtItemStatistics_3 = (TextView) findViewById(R.id.txtItemStatistics_3);
		txtItemStatistics_4 = (TextView) findViewById(R.id.txtItemStatistics_4);

		TextView[] arrTitle = { txtItemTitle_1, txtItemTitle_2, txtItemTitle_3,
				txtItemTitle_4 };
		TextView[] arrDate = { txtItemDate_1, txtItemDate_2, txtItemDate_3,
				txtItemDate_4 };
		TextView[] arrStatistics = { txtItemStatistics_1, txtItemStatistics_2,
				txtItemStatistics_3, txtItemStatistics_4 };
		LinearLayout[] arrSuggestion = { lnSuggestion_1, lnSuggestion_2,
				lnSuggestion_3, lnSuggestion_4 };

		ArrayList<ItemDetails> results = new ArrayList<ItemDetails>();

		String qString=getQueryString();
		resultListSuggetion = webServiceCaller.getSuggetionList(qString);

		for (int j = 0; j < resultListSuggetion.size(); j++) {

			if (j == 4) {
				break;
			}
			rowList = resultListSuggetion.get(j);

			arrTitle[j].setText(rowList.get(1));
			arrDate[j].setText(getDocumentRatingInfo(rowList.get(3),
					rowList.get(4), rowList.get(5)));
			arrStatistics[j].setText(rowList.get(2));
			arrSuggestion[j].setVisibility(android.view.View.VISIBLE);
		}

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

	public void onClickViewDocument(View view) {
		LinearLayout[] arrSuggestion = { lnSuggestion_1, lnSuggestion_2,
				lnSuggestion_3, lnSuggestion_4 };
		Intent intent = new Intent(getApplicationContext(),
				ItemInfoActivity.class);

		if (view == arrSuggestion[0]) {
			rowList = resultListSuggetion.get(0);
			intent.putExtra("DOC_ID", rowList.get(0));
			intent.putExtra("AUTHOR_ID", rowList.get(6));
		}
		if (view == arrSuggestion[1]) {
			rowList = resultListSuggetion.get(1);
			intent.putExtra("DOC_ID", rowList.get(0));
			intent.putExtra("AUTHOR_ID", rowList.get(6));
		}
		if (view == arrSuggestion[2]) {
			rowList = resultListSuggetion.get(2);
			intent.putExtra("DOC_ID", rowList.get(0));
			intent.putExtra("AUTHOR_ID", rowList.get(6));
		}
		if (view == arrSuggestion[3]) {
			rowList = resultListSuggetion.get(3);
			intent.putExtra("DOC_ID", rowList.get(0));
			intent.putExtra("AUTHOR_ID", rowList.get(6));
		}
		startActivity(intent);
	}

	void setMyRating() {

		String[] userRating = webServiceCaller.getUserRating(docID, userID);

		if (userRating != null) {
			rateFlg = 1;
			switch (Integer.parseInt(userRating[3])) {
			case 1:
				ivLike.setImageResource(R.drawable.like_pressed);
				ivDislike.setImageResource(R.drawable.dislike);
				break;

			case 0:
				ivDislike.setImageResource(R.drawable.dislike_pressed);
				ivLike.setImageResource(R.drawable.like);
				break;
			}
		}
	}

	void saveUserRating(String userRating) {

		Boolean isSuccess = false;
		ArrayList<String> inputList = new ArrayList<String>();

		inputList.add(docID);
		inputList.add(userID);
		inputList.add(userRating);

		isSuccess = webServiceCaller.saveUserRating(inputList);

		if (isSuccess) {

			toast("Saved successfully");
		} else {

			toast("Could not connect to the server");
		}

	}

	void updateUserRating(String userRating) {

		Boolean isSuccess = false;
		ArrayList<String> inputList = new ArrayList<String>();

		inputList.add(docID);
		inputList.add(userID);
		inputList.add(userRating);

		isSuccess = webServiceCaller.updateUserRating(inputList);

		if (isSuccess) {

			toast("Saved successfully");
		} else {

			toast("Could not connect to the server");
		}

	}
	void saveDocumentView() {

		Boolean isSuccess = false;
		ArrayList<String> inputList = new ArrayList<String>();

		inputList.add(docID);
		inputList.add(userID);		

		isSuccess = webServiceCaller.saveDocumentView(inputList);
		if(isSuccess){
			setDocumentRatingInfo();
		}

	}
	public void setAdvertAction() {

		if (isWatchListItem()) {
			btnAddWatchList.setVisibility(android.view.ViewGroup.GONE);
			actionFlg = 0;
		} else {
			btnAddWatchList.setVisibility(android.view.ViewGroup.VISIBLE);
			actionFlg = 1;
		}
	}

	Boolean isWatchListItem() {

		Boolean isFavSeller = false;
		ArrayList<String> arrList = new ArrayList<String>();

		ArrayList<ArrayList<String>> resultList;
		ArrayList<String> rowList;

		resultList = db.getWatchList(userID,"1");

		if (resultList != null) {

			for (int i = 0; i < resultList.size(); i++) {

				rowList = resultList.get(i);

				if (rowList.get(3).equalsIgnoreCase(docID)) {
					isFavSeller = true;
					break;
				}
				;

			}

		}
		return isFavSeller;

	}
	public void onClickWatchList(View view){
		
		db.addWatchListItem(userID, "0000",docID,"1");		
		btnAddWatchList.setVisibility(android.view.View.GONE);
		toast("Successfully added to watchlist");
	}
}
