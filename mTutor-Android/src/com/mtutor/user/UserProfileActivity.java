package com.mtutor.user;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.mtutor.activities.RecentActivitiesActivity;
import com.mtutor.connection.ItemDetails;
import com.mtutor.connection.UserConnectionsActivity;
import com.mtutor.dashboard.DashboardActivity;
import com.mtutor.dashboard.R;
import com.mtutor.data.DatabaseHandler;
import com.mtutor.notification.Alerts;
import com.mtutor.recentviews.RecentViewActivity;
import com.mtutor.servicecall.WebServiceCaller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class UserProfileActivity extends DashboardActivity implements
		OnClickListener {

	RelativeLayout relLayoutActivities, relLayoutRecentViews,
			relLayoutConnections;

	TextView txtName,txtPosition,txtWorkPlace,txtActivityCount,txtViewCount,txtConnectionCount,txtFirstname,txtLastname,
	txtEmail,txtAddress,txtMobileNo,txtFaceBook,txtGooglePlus,txtLinkedIn,txtSkype,txtTwitter,txtActionText;
	
	ImageView imgProfileImage;
	ImageButton btnAddCon,btnRespond,btnRemove;
	
	WebServiceCaller webServiceCaller;
	Alerts alert;
	SharedPreferences pref;
	
	private static final String PREFS_NAME = "MyPrefsFile";
	private static final String PREF_USERID = "userid";
	
	String userID,fullName,loggedUser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);
		setTitleFromActivityLabel(R.id.title_text);
		setStartUp();
	}

	void setStartUp() {
		
		webServiceCaller = new WebServiceCaller();
		alert = new Alerts(this);
		
		pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		loggedUser = pref.getString(PREF_USERID, null);
		
		if (getIntent().getExtras().getSerializable("USER_ID") != null) {

			userID = getIntent().getExtras().getSerializable("USER_ID")
					.toString();
			
		
		}
		
		txtName = (TextView) findViewById(R.id.txtName);
		txtPosition = (TextView) findViewById(R.id.txtPosition);
		txtWorkPlace = (TextView) findViewById(R.id.txtWorkPlace);
		imgProfileImage = (ImageView) findViewById(R.id.imgProfPic);
		txtFirstname = (TextView) findViewById(R.id.txtFirstname);
		txtLastname = (TextView) findViewById(R.id.txtLastname);
		txtEmail = (TextView) findViewById(R.id.txtEmail);
		txtAddress = (TextView) findViewById(R.id.txtAddress);
		txtMobileNo = (TextView) findViewById(R.id.txtMobileNo);
		txtFaceBook = (TextView) findViewById(R.id.txtFaceBook);
		txtGooglePlus = (TextView) findViewById(R.id.txtGooglePlus);
		txtLinkedIn = (TextView) findViewById(R.id.txtLinkedIn);
		txtSkype = (TextView) findViewById(R.id.txtSkype);
		txtTwitter = (TextView) findViewById(R.id.txtTwitter);
		txtActionText=(TextView) findViewById(R.id.txtActionText);
		
		txtActivityCount= (TextView) findViewById(R.id.txtActivityCount);
		txtViewCount= (TextView) findViewById(R.id.txtViewCount);
		txtConnectionCount= (TextView) findViewById(R.id.txtConnectionCount);
		
		relLayoutActivities = (RelativeLayout) findViewById(R.id.relLayoutActivities);
		relLayoutActivities.setOnClickListener(this);
		relLayoutRecentViews = (RelativeLayout) findViewById(R.id.relLayoutRecentViews);
		relLayoutRecentViews.setOnClickListener(this);
		relLayoutConnections = (RelativeLayout) findViewById(R.id.relLayoutConnections);
		relLayoutConnections.setOnClickListener(this);
		
		btnAddCon=(ImageButton)findViewById(R.id.btnAddCon);
		btnRespond=(ImageButton)findViewById(R.id.btnRespond);
		btnRemove=(ImageButton)findViewById(R.id.btnRemove);
				
		setProfileInfo();
		setProfileCount();
		setActionButtonControls();
	}
	void setProfileInfo() {

		String[] userProfile_Arr = null;
		userProfile_Arr = webServiceCaller.getUserprofile(userID);
		try {

			txtName.setText(userProfile_Arr[1]+" "+userProfile_Arr[2]);
			fullName=userProfile_Arr[1]+" "+userProfile_Arr[2];
			txtPosition.setText(userProfile_Arr[6]);
			txtWorkPlace.setText(userProfile_Arr[7]);
			txtFirstname.setText(userProfile_Arr[1]);
			txtLastname.setText(userProfile_Arr[2]);
			txtEmail.setText(userProfile_Arr[3]);
			txtAddress.setText(userProfile_Arr[4].replace("anyType{}", ""));
			txtMobileNo.setText(userProfile_Arr[5].replace("anyType{}", ""));
			txtFaceBook.setText(userProfile_Arr[8].replace("anyType{}", ""));
			txtGooglePlus.setText(userProfile_Arr[9].replace("anyType{}", ""));
			txtLinkedIn.setText(userProfile_Arr[10].replace("anyType{}", ""));
			txtSkype.setText(userProfile_Arr[11].replace("anyType{}", ""));
			txtTwitter.setText(userProfile_Arr[12].replace("anyType{}", ""));
			
			String imgUrl=userProfile_Arr[13];
			Bitmap bimage=  getBitmapFromURL(imgUrl);
			imgProfileImage.setImageBitmap(bimage);
		} catch (Exception e) {
			// TODO: handle exception

			alert.Alertbox("Exception", e.toString());
		}
	}
	void setProfileCount(){
		
		String[] userProfile_Count = null;
		userProfile_Count = webServiceCaller.getUserProfileCount(userID);
		
		if (!userProfile_Count[0].equalsIgnoreCase("0")) {
			txtActivityCount.setText(userProfile_Count[0]
					+ " updates this week");
		} else {
			txtActivityCount.setText("No updates this week");
		}
		if (!userProfile_Count[1].equalsIgnoreCase("0")) {
			txtViewCount.setText(userProfile_Count[1] + " views this week");
		} else {
			txtViewCount.setText("No views this week");
		}
		
		txtConnectionCount.setText(""+userProfile_Count[2] +" connections");
		
		
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
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if (view == relLayoutActivities) {
			Intent intent = new Intent(getApplicationContext(),
					RecentActivitiesActivity.class);
			intent.putExtra("USER_ID",userID);
			intent.putExtra("FULL_NAME",fullName);
			startActivity(intent);
		}
		if (view == relLayoutRecentViews) {
			Intent intent = new Intent(getApplicationContext(),
					RecentViewActivity.class);
			intent.putExtra("USER_ID",userID);
			startActivity(intent);
		}
		if (view == relLayoutConnections) {
			Intent intent = new Intent(getApplicationContext(),
					UserConnectionsActivity.class);
			intent.putExtra("USER_ID",userID);
			startActivity(intent);;
		}
	}
	public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src",src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
            return null;
        }
    }
	public void onClickAddCon(View view){
		ArrayList paraList =new ArrayList<String>();
		
		paraList.add(loggedUser);
		paraList.add(userID);
		
		boolean isSuccess=webServiceCaller.saveContactRequest(paraList);
		if(isSuccess){
			toast("Contact request sent");
			setActionButtonControls();
		}else{
			
			toast("Eroor! Could not connect to the server");
		}
	}
public void onClickRespond(View view){
	ArrayList paraList =new ArrayList<String>();
	
	paraList.add(loggedUser);
	paraList.add(userID);
	
	boolean isSuccess=webServiceCaller.acceptContactRequest(paraList);
	if(isSuccess){
		toast("Contact request successfully accepted");
		setActionButtonControls();
	}else{
		
		toast("Eroor! Could not connect to the server");
	}
	}
public void onClickRemove(View view){
	ArrayList paraList =new ArrayList<String>();
	
	paraList.add(loggedUser);
	paraList.add(userID);
	
	boolean isSuccess=webServiceCaller.removeContact(paraList);
	if(isSuccess){
		toast("Successfully Removed from contacts");
		setActionButtonControls();
	}else{
		
		toast("Eroor! Could not connect to the server");
	}
}

void setActionButtonControls(){
	
	if(isMyContact()){
		
		btnRemove.setVisibility(android.view.View.VISIBLE);
		btnRespond.setVisibility(android.view.View.GONE);
		btnAddCon.setVisibility(android.view.View.GONE);
		txtActionText.setText("Click to remove from contacts");

	}else if(isMyContactRequest()){
		
		btnRespond.setVisibility(android.view.View.VISIBLE);
		btnRemove.setVisibility(android.view.View.GONE);
		btnAddCon.setVisibility(android.view.View.GONE);
		txtActionText.setText("Click to respond to contact request");
	}else{
		
		btnAddCon.setVisibility(android.view.View.VISIBLE);
		btnRemove.setVisibility(android.view.View.GONE);
		btnRespond.setVisibility(android.view.View.GONE);
		txtActionText.setText("Click to add to contacts");
	}
	
}
private boolean isMyContact() {
	
	ArrayList<ArrayList> resultList=null;
	ArrayList<String> rowList=null;
	Boolean isContact=false;
	
	String contactID;
	resultList=webServiceCaller.getUserContactList(loggedUser);
			
			
	if(resultList!=null){
		
	for(int i=0;i<resultList.size();i++){
		
			rowList=resultList.get(i);
			
			ItemDetails item_details = new ItemDetails();
			contactID=rowList.get(0);			
			if(contactID.equalsIgnoreCase(userID)){
				
				isContact=true;
				break;
			}			
		
	}

	}
	

	return isContact;
}
private boolean isMyContactRequest() {
	
	ArrayList<ArrayList> resultList=null;
	ArrayList<String> rowList=null;
	Boolean isContact=false;
	
	String contactID;
	resultList=webServiceCaller.getUserContactRequestList(loggedUser);
			
			
	if(resultList!=null){
		
	for(int i=0;i<resultList.size();i++){
		
			rowList=resultList.get(i);
			
			ItemDetails item_details = new ItemDetails();
			contactID=rowList.get(0);			
			if(contactID.equalsIgnoreCase(userID)){
				
				isContact=true;
				break;
			}			
		
	}

	}
	

	return isContact;
}
}
