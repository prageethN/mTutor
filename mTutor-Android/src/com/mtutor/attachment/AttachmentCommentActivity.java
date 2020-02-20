package com.mtutor.attachment;

import java.util.ArrayList;
import java.util.Date;

import com.mtutor.dashboard.DashboardActivity;
import com.mtutor.dashboard.R;
import com.mtutor.publication.AttachmentInfoActivity;
import com.mtutor.publication.ImageItemDetails;
import com.mtutor.publication.ItemDetails;
import com.mtutor.publication.ItemListBaseAdapter;
import com.mtutor.servicecall.WebServiceCaller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RatingBar.OnRatingBarChangeListener;

/**
 * @author Adil Soomro
 * 
 */
public class AttachmentCommentActivity extends DashboardActivity {

	ListView lvComments;
	ArrayList<ItemDetails> itemComments;

	WebServiceCaller webServiceCaller;
	SharedPreferences pref;

	private static final String PREFS_NAME = "MyPrefsFile";
	private static final String PREF_USERID = "userid";
	public static final String PREFS_FULLNAME = "fullname";

	String docID, attID,userID,userFullName;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_attachment_comment);
		setupStartup();

	}

	void setupStartup() {

		webServiceCaller = new WebServiceCaller();

		pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		userID = pref.getString(PREF_USERID, null);
		userFullName=pref.getString(PREFS_FULLNAME, null);
		
		if (getIntent().getExtras().getSerializable("ATT_ID") != null) {

			docID = getIntent().getExtras().getSerializable("DOC_ID")
					.toString();
			attID = getIntent().getExtras().getSerializable("ATT_ID")
					.toString();
		}

		itemComments = GetComments();
		lvComments = (ListView) findViewById(R.id.lvComments);
		lvComments.setAdapter(new ItemListBaseAdapter(this, itemComments));

		lvComments.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				Object o = lvComments.getItemAtPosition(position);
				ItemDetails obj_itemDetails = (ItemDetails) o;
				Toast.makeText(
						getApplicationContext(),
						"You have chosen : " + " "
								+ obj_itemDetails.getTextHeader(),
						Toast.LENGTH_LONG).show();
			}
		});
	}

	void loadAttachmentComment(){
		
		itemComments = GetComments();
		lvComments = (ListView) findViewById(R.id.lvComments);
		lvComments.setAdapter(new ItemListBaseAdapter(this, itemComments));
	}
	

	private ArrayList<ItemDetails> GetComments() {
		ArrayList<ItemDetails> results = new ArrayList<ItemDetails>();
		ArrayList<ArrayList> resultList = null;
		ArrayList<String> rowList = null;

		resultList = webServiceCaller.getAttachmentCommentList(attID);

		if (resultList != null) {

			if (resultList.size() > 0) {

				for (int i = 0; i < resultList.size(); i++) {

					rowList = resultList.get(i);

					ItemDetails item_details = new ItemDetails();
					item_details.setTextHeader(rowList.get(2));
					item_details.setTextNormal(rowList.get(0));
					item_details.setTextFooter(rowList.get(1));

					results.add(item_details);

				}

			}

		}

		/*
		 * ItemDetails item_details = new ItemDetails();
		 * item_details.setTextHeader("prageethkandy");
		 * item_details.setTextNormal("Waaw, this is awesome..");
		 * item_details.setTextFooter("August 23,2012 | 9:30 GMT");
		 * results.add(item_details);
		 */

		return results;
	}

	void postComment() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Post Comment");
		alert.setMessage("Enter your comment here...");
		// Set an EditText view to get user input
		final EditText input = new EditText(this);
		input.setWidth(LayoutParams.FILL_PARENT);
		input.setHeight(LayoutParams.WRAP_CONTENT);
		alert.setView(input);

		alert.setPositiveButton("Post", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				String txtcomment = input.getText().toString();

				ItemDetails item_details = new ItemDetails();
				item_details = new ItemDetails();
				item_details.setTextHeader("Maggie");
				item_details.setTextNormal(txtcomment);
				item_details.setTextFooter("August 20,2012 | 9:35 GMT");
				//results.add(item_details);
			}
		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
					}
				});

		alert.show();

	}

	public void onClickComment(View view) {

		showPopup(this,p);
	}
	/*  ---------------------------     Commenting advertisement -------------------------------------------- */
	Point p;
	RatingBar rbRate;
	EditText txtUserComment;
		
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {

		int[] location = new int[2];
		View txtClickComment  =  findViewById(R.id.divider);

		// Get the x, y location and store it in the location[] array
		// location[0] = x, location[1] = y.
		txtClickComment.getLocationOnScreen(location);

		// Initialize the Point with x, and y positions
		p = new Point();
		p.x = location[0];
		p.y = location[1];
	}
	void getViewPoint(){
		
		int[] location = new int[2];
		View txtClickComment  =  findViewById(R.id.divider);

		// Get the x, y location and store it in the location[] array
		// location[0] = x, location[1] = y.
		txtClickComment.getLocationOnScreen(location);

		// Initialize the Point with x, and y positions
		p = new Point();
		p.x = location[0];
		p.y = location[1];
	}
	private void showPopup(final Activity context,Point p) {
		
		int[] location = new int[2];
		View txtClickComment  =  findViewById(R.id.divider);

		// Get the x, y location and store it in the location[] array
		// location[0] = x, location[1] = y.
		txtClickComment.getLocationOnScreen(location);

		// Initialize the Point with x, and y positions
		p = new Point();
		p.x = location[0];
		p.y = location[1];
		
		int popupWidth = ViewGroup.LayoutParams.FILL_PARENT;
		int popupHeight = ViewGroup.LayoutParams.WRAP_CONTENT;

		// Inflate the popup_layout.xml
		LinearLayout viewGroup = (LinearLayout) context
				.findViewById(R.id.popupCommentItem);
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = layoutInflater.inflate(R.layout.popup_comment_attachment, viewGroup);

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
		
		txtUserComment = (EditText) layout.findViewById(R.id.txtUserComment);
					
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
				
				saveAttachmentComment(txtUserComment.getText().toString());
				popup.dismiss();
			}
		});
	}
	void saveAttachmentComment( String userComment){
		
		ArrayList paraList =new ArrayList<String>();
		
		paraList.add(docID);
		paraList.add(attID);		
		paraList.add(userID);
		paraList.add(userComment);
		
		Boolean isSuccess=webServiceCaller.saveAttachmentComment(paraList);
		
		if(isSuccess){
			toast("Your comment successfully saved");
			loadAttachmentComment();
			
		}else{
			toast("Could not complete your request, please try again");
			return;
		}
		
	}
}
