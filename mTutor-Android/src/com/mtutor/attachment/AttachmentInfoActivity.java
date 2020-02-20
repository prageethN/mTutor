package com.mtutor.attachment;

import java.util.ArrayList;

import com.mtutor.connection.UserConnectionsActivity;
import com.mtutor.dashboard.DashboardActivity;
import com.mtutor.dashboard.R;
import com.mtutor.data.DatabaseHandler;
import com.mtutor.servicecall.WebServiceCaller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class AttachmentInfoActivity extends DashboardActivity {

	TextView txtDescriptionMore, txtItemTitle, txtItemDate, txtItemStatistics,
			txtDocCategory,txtReviewCount,txtViewCount,txtRateThis,txtRateDate;
	ImageButton btnAddWatchList;
	RelativeLayout lnRateAttachment;
	RatingBar rbMyRating, ratingbar;
	
	
	WebServiceCaller webServiceCaller;
	SharedPreferences pref;
	DatabaseHandler db;
	
	private static final String PREFS_NAME = "MyPrefsFile";
	private static final String PREF_USERID = "userid";
	
	String docID, attID, attURL, attType,userID;
	int userRateFlg=0,actionFlg=0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_attachment_info);
		setStartUp();
	}

	void setStartUp() {

		webServiceCaller = new WebServiceCaller();

		pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		userID = pref.getString(PREF_USERID, null);
		
		db = new DatabaseHandler(this);
		
		if (getIntent().getExtras().getSerializable("ATT_ID") != null) {

			docID = getIntent().getExtras().getSerializable("DOC_ID")
					.toString();
			attID = getIntent().getExtras().getSerializable("ATT_ID")
					.toString();
		}

		txtDescriptionMore = (TextView) findViewById(R.id.txtDescription_more);
		txtItemTitle = (TextView) findViewById(R.id.txtItemTitle);
		txtItemDate = (TextView) findViewById(R.id.txtItemDate);
		txtViewCount = (TextView) findViewById(R.id.txtViewCount);
		txtDocCategory = (TextView) findViewById(R.id.txtDocCategory);
		txtReviewCount=(TextView) findViewById(R.id.txtReviewCount);
		txtRateThis=(TextView) findViewById(R.id.txtRateThis);
		txtRateDate=(TextView) findViewById(R.id.txtRateDate);
		
		lnRateAttachment = (RelativeLayout) findViewById(R.id.lnRateAttachment);

		rbMyRating=(RatingBar)findViewById(R.id.rbMyRating);
		ratingbar=(RatingBar)findViewById(R.id.ratingbar);
		
		btnAddWatchList = (ImageButton ) findViewById(R.id.btnAddWatchList);
		
		setAttachmentInfo();
		setAttachmentRating() ;
		setAttachmentUserRating();
		saveAttachmentView();
		setAdvertAction() ;
	}

	void setAttachmentInfo() {

		String[] docInfo = webServiceCaller.getAttachmentInfo(attID);
		if (docInfo != null) {

			txtItemTitle.setText(docInfo[3]);
			txtItemDate.setText(docInfo[4]);
			txtDescriptionMore.setText(docInfo[5]);
			txtDocCategory.setText(docInfo[7]);
			attURL = docInfo[10];
			attType = docInfo[2];
		}

	}
	void setAttachmentRating() {
	
		String[] rateInfo = webServiceCaller.getAttachmentRating(attID);
		
		if (rateInfo != null) {

			txtReviewCount.setText("("+rateInfo[0]+")");
			txtViewCount.setText("| "+rateInfo[2]+" views");
			ratingbar.setRating(Float.parseFloat(rateInfo[1].replace("anyType{}", "0.0")));			
			
		}

	}
	void setAttachmentUserRating() {
		
		ArrayList<String> inputList = new ArrayList<String>();

		inputList.add(userID);
		inputList.add(docID);
		inputList.add(attID);
		
		String[] rateInfo = webServiceCaller.getUserAttachmentRating(inputList);
		
		if (rateInfo != null) {

			txtRateThis.setText("Your rating");
			txtRateDate.setText("Rated on "+rateInfo[4]);
			rbMyRating.setRating(Float.parseFloat(rateInfo[3]));			
			
			userRateFlg=1;
			lnRateAttachment.setClickable(false);
		}

	}
	
	
	public void onClickViewAttachment(View view) {

		Intent intent = new Intent(getApplicationContext(),
				AttachmentViewActivity.class);
		intent.putExtra("ATT_URL",attURL);
		intent.putExtra("ATT_TYPE",attType);
		startActivity(intent);;
	}
	public void onClickRateThis(View view){
		if(userRateFlg==0){
			showPopup(this,p);
		}
		
	}
	/*  ---------------------------     Rating advertisement -------------------------------------------- */
	Point p;
	RatingBar rbRate;
	TextView txtRate;
		
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {

		int[] location = new int[2];
		RelativeLayout lnRateItem  = (RelativeLayout) findViewById(R.id.lnRateAttachment);

		// Get the x, y location and store it in the location[] array
		// location[0] = x, location[1] = y.
		lnRateItem.getLocationOnScreen(location);

		// Initialize the Point with x, and y positions
		p = new Point();
		p.x = location[0];
		p.y = location[1];
	}
	
	private void showPopup(final Activity context,Point p) {
			
			int popupWidth = ViewGroup.LayoutParams.FILL_PARENT;
			int popupHeight = ViewGroup.LayoutParams.WRAP_CONTENT;

			// Inflate the popup_layout.xml
			LinearLayout viewGroup = (LinearLayout) context
					.findViewById(R.id.popupRateItem);
			LayoutInflater layoutInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View layout = layoutInflater.inflate(R.layout.popup_rate_attachment, viewGroup);

			// Creating the PopupWindow
			final PopupWindow popup = new PopupWindow(context);
			popup.setContentView(layout);
			popup.setWidth(popupWidth);
			popup.setHeight(popupHeight);
			popup.setFocusable(true);

			// Some offset to align the popup a bit to the right, and a bit down,
			// relative to button's position.
			int OFFSET_X = 30;
			int OFFSET_Y = 30;

			// Clear the default translucent background
			popup.setBackgroundDrawable(new BitmapDrawable());

			// Displaying the popup at the specified location, + offsets.
			popup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y
					+ OFFSET_Y);

			rbRate=(RatingBar)layout.findViewById(R.id.rbRate);
			txtRate = (TextView) layout.findViewById(R.id.txtRate);
						
			rbRate.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
				public void onRatingChanged(RatingBar ratingBar, float rating,
						boolean fromUser) {
			 
					txtRate.setText("My rating: "+String.valueOf((int)rating));
			 
					}
				});
			// Getting a reference to Close button, and close the popup when
			// clicked.
			Button close = (Button) layout.findViewById(R.id.btnClose);
			close.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					popup.dismiss();
				}
			});
			Button done = (Button) layout.findViewById(R.id.btnOk);
			done.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {					
					
					saveAttachmentRating(rbRate.getRating());
					popup.dismiss();
				}
			});
		}
	void saveAttachmentRating( Float _rateValue){
		
		ArrayList paraList =new ArrayList<String>();
		
		paraList.add(attID);
		paraList.add(docID);		
		paraList.add(userID);
		paraList.add(_rateValue);
		
		Boolean isSuccess=webServiceCaller.saveAttachmentRating(paraList);
		
		if(isSuccess){
			toast("Rating successfully saved");
			setAttachmentRating() ;
			setAttachmentUserRating();
			
		}else{
			toast("Could not complete your request, please try again");
			return;
		}
		
	}
	void saveAttachmentView() {

		Boolean isSuccess = false;
		ArrayList<String> inputList = new ArrayList<String>();

		inputList.add(attID);
		inputList.add(userID);		

		isSuccess = webServiceCaller.saveAttachmentView(inputList);
		if(isSuccess){
			setAttachmentRating() ;
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

		resultList = db.getWatchList(userID,"2");

		if (resultList != null) {

			for (int i = 0; i < resultList.size(); i++) {

				rowList = resultList.get(i);

				if (rowList.get(2).equalsIgnoreCase(attID)) {
					isFavSeller = true;
					break;
				}
				;

			}

		}
		return isFavSeller;

	}
	public void onClickWatchList(View view){
		
		db.addWatchListItem(userID,attID ,docID,"2");	
		btnAddWatchList.setVisibility(android.view.View.GONE);
		toast("Successfully added to watchlist");
	}
/* --------------------------------------------------------------    View attachment-----------------------------------------------------------------------*/
	
}