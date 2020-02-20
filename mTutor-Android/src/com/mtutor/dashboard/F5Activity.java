package com.mtutor.dashboard;

import com.mtutor.activities.RecentActivitiesActivity;
import com.mtutor.dashboard.R;
import com.mtutor.notification.Alerts;
import com.mtutor.recentviews.RecentViewActivity;
import com.mtutor.servicecall.WebServiceCaller;
import com.mtutor.connection.ConnectionsActivity;
import com.mtutor.user.EditProfileActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

public class F5Activity extends DashboardActivity implements OnClickListener {

	Button editProfile, userConnections;
	RelativeLayout relLayoutActivities, relLayoutRecentViews,
			relLayoutFacebook, relLayoutGooglePlus, relLayoutLinkedIn,
			relLayoutSkype, relLayoutTwitter;
	ImageView imgProfileImage;
	TextView txtFullname, txtPosition, txtWorkPlace, txtFirstName, txtLastName,
			txtEmail, txtAddress, txtContactNo, txtFacebook, txtGooglePlus,
			txtLinkedIn, txtSkype, txtwitter, txtUserName, txtPassword,
			txtRepeatPasword, txtAnswer, txtRecentActivity, txtRecentView;
	Spinner spSecQuestion;
	TableRow trContact, trEmail;

	WebServiceCaller webServiceCaller;
	Alerts alert;

	private static final String PREFS_NAME = "MyPrefsFile";
	private static final String PREF_USERID = "userid";
	public static final String PREFS_FULLNAME = "fullname";
	private static final String PREF_POSITION = "position";
	private static final String PREF_WORK_PLACE = "work_place";
	private static final String PREF_PROF_IMG = "profileImg";

	String url,userID;

	// String urlFacebook,urlGooglePlus,urlLinkedIn,urlSkype,urlTwitter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_f5);
		setTitleFromActivityLabel(R.id.title_text);
		setStartUp();
	}

	private void setStartUp() {
		webServiceCaller = new WebServiceCaller();
		alert = new Alerts(this);
		spSecQuestion = (Spinner) findViewById(R.id.SecQuestion);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.sec_question,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spSecQuestion.setAdapter(adapter);
		spSecQuestion.setSelection(1);

		editProfile = (Button) findViewById(R.id.btnEditProf);
		editProfile.setOnClickListener(this);
		userConnections = (Button) findViewById(R.id.btnConnections);
		userConnections.setOnClickListener(this);

		relLayoutActivities = (RelativeLayout) findViewById(R.id.relLayoutActivities);
		relLayoutActivities.setOnClickListener(this);
		relLayoutRecentViews = (RelativeLayout) findViewById(R.id.relLayoutRecentViews);
		relLayoutRecentViews.setOnClickListener(this);
		relLayoutFacebook = (RelativeLayout) findViewById(R.id.relLayoutFacebook);
		relLayoutFacebook.setOnClickListener(this);
		relLayoutGooglePlus = (RelativeLayout) findViewById(R.id.relLayoutGooglePlus);
		relLayoutGooglePlus.setOnClickListener(this);
		relLayoutLinkedIn = (RelativeLayout) findViewById(R.id.relLayoutLinkedIn);
		relLayoutLinkedIn.setOnClickListener(this);
		relLayoutSkype = (RelativeLayout) findViewById(R.id.relLayoutSkype);
		relLayoutSkype.setOnClickListener(this);
		relLayoutTwitter = (RelativeLayout) findViewById(R.id.relLayoutTwitter);
		relLayoutTwitter.setOnClickListener(this);

		trContact = (TableRow) findViewById(R.id.trMobile);
		trContact.setOnClickListener(this);
		trEmail = (TableRow) findViewById(R.id.trEmail);
		trEmail.setOnClickListener(this);

		txtFullname = (TextView) findViewById(R.id.txtName);
		txtPosition = (TextView) findViewById(R.id.txtPosition);
		txtWorkPlace = (TextView) findViewById(R.id.txtWorkPlace);
		imgProfileImage = (ImageView) findViewById(R.id.imgProfPic);
		txtFirstName = (TextView) findViewById(R.id.txtFirstname);
		txtLastName = (TextView) findViewById(R.id.txtLastname);
		txtEmail = (TextView) findViewById(R.id.txtEmail);
		txtAddress = (TextView) findViewById(R.id.txtAddress);
		txtContactNo = (TextView) findViewById(R.id.txtMobileNo);
		txtFacebook = (TextView) findViewById(R.id.txtFacebook);
		txtGooglePlus = (TextView) findViewById(R.id.txtGoogle);
		txtLinkedIn = (TextView) findViewById(R.id.txtLinkedIn);
		txtSkype = (TextView) findViewById(R.id.txtSkype);
		txtwitter = (TextView) findViewById(R.id.txtTwitter);
		txtUserName = (TextView) findViewById(R.id.txtUserName);
		txtPassword = (TextView) findViewById(R.id.txtPassWord);
		txtRecentActivity = (TextView) findViewById(R.id.txtRecentActivity);
		txtRecentView = (TextView) findViewById(R.id.txtRecentView);

		SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		txtFullname.setText(pref.getString(PREFS_FULLNAME, null));
		txtPosition.setText(pref.getString(PREF_POSITION, null));
		txtWorkPlace.setText(pref.getString(PREF_WORK_PLACE, null));
		setProfileImage(pref.getString(PREF_PROF_IMG, null));

		userID=pref.getString(PREF_USERID, null);
		setProfileInfo(pref.getString(PREF_USERID, null));
		setProfileCount();
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if (view == editProfile) {

			Intent i = new Intent(getApplicationContext(),
					EditProfileActivity.class);
			startActivity(i);
		}
		if (view == userConnections) {
			Intent i = new Intent(getApplicationContext(),
					ConnectionsActivity.class);
			startActivity(i);
		}
		if (view == relLayoutActivities) {
			Intent i = new Intent(getApplicationContext(),
					RecentActivitiesActivity.class);
			startActivity(i);
		}
		if (view == relLayoutRecentViews) {
			Intent i = new Intent(getApplicationContext(),
					RecentViewActivity.class);
			startActivity(i);
		}
		if (view == relLayoutFacebook) {
			url = txtFacebook.getText().toString();
			if (!url.startsWith("http://") && !url.startsWith("https://"))
				url = "http://" + url;

			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			startActivity(intent);

		}
		if (view == relLayoutGooglePlus) {
			url = txtGooglePlus.getText().toString();
			if (!url.startsWith("http://") && !url.startsWith("https://"))
				url = "http://" + url;
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			startActivity(intent);

		}
		if (view == relLayoutLinkedIn) {
			url = txtLinkedIn.getText().toString();
			if (!url.startsWith("http://") && !url.startsWith("https://"))
				url = "http://" + url;
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			startActivity(intent);

		}
		if (view == relLayoutSkype) {
			Intent sky = new Intent("android.intent.action.VIEW");
			sky.setData(Uri.parse("skype:" + txtSkype.getText().toString()));
			startActivity(sky);

		}
		if (view == relLayoutTwitter) {
			url = txtwitter.getText().toString();
			if (!url.startsWith("http://") && !url.startsWith("https://"))
				url = "http://" + url;
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			startActivity(intent);

		}
		if (view == trContact) {
			Intent dial = new Intent();
			dial.setAction("android.intent.action.DIAL");
			dial.setData(Uri.parse("tel:" + txtContactNo.getText().toString()));
			startActivity(dial);
		}
		if (view == trEmail) {
			final Intent emailIntent = new Intent(
					android.content.Intent.ACTION_SEND);
			emailIntent.setType("plain/text");
			emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
					new String[] { txtEmail.getText().toString() });
			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
			emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
			startActivity(Intent.createChooser(emailIntent, "Send mail..."));
		}
	}

	void setProfileImage(String profileJpgPath) {
		try {
			if (profileJpgPath != "default") {
				BitmapFactory.Options options = new BitmapFactory.Options();
				// options.inSampleSize = 2;
				Bitmap bm = BitmapFactory.decodeFile(profileJpgPath, options);
				imgProfileImage.setImageBitmap(bm);

				imgProfileImage.setAdjustViewBounds(true);
				imgProfileImage.setMinimumHeight(177);
				imgProfileImage.setMinimumWidth(177);
				imgProfileImage.setMaxHeight(177);
				imgProfileImage.setMaxWidth(177);
			} else {
				imgProfileImage
						.setBackgroundResource(R.drawable.img_default_propic);
			}
		} catch (Exception e) {
			// TODO: handle exception
			alert.Alertbox("Exception", e.toString());
		}
	}
	void setProfileCount(){
		
		String[] userProfile_Count = null;
		userProfile_Count = webServiceCaller.getUserProfileCount(userID);
		
		if (!userProfile_Count[0].equalsIgnoreCase("0")) {
			txtRecentActivity.setText(userProfile_Count[0]
					+ " updates this week");
		} else {
			txtRecentActivity.setText("No updates this week");
		}
		if (!userProfile_Count[1].equalsIgnoreCase("0")) {
			txtRecentView.setText(userProfile_Count[1] + " views this week");
		} else {
			txtRecentView.setText("No views this week");
		}
		
	}
	void setProfileInfo(String userID) {

		String[] userProfile_Arr = null;
		userProfile_Arr = webServiceCaller.getUserprofile(userID);
		try {

			txtFirstName.setText(userProfile_Arr[1]);
			txtLastName.setText(userProfile_Arr[2]);
			txtEmail.setText(userProfile_Arr[3]);
			txtAddress.setText(userProfile_Arr[4].replace("anyType{}", ""));
			txtContactNo.setText(userProfile_Arr[5].replace("anyType{}", ""));
			txtFacebook.setText(userProfile_Arr[8].replace("anyType{}", ""));
			txtGooglePlus.setText(userProfile_Arr[9].replace("anyType{}", ""));
			txtLinkedIn.setText(userProfile_Arr[10].replace("anyType{}", ""));
			txtSkype.setText(userProfile_Arr[11].replace("anyType{}", ""));
			txtwitter.setText(userProfile_Arr[12].replace("anyType{}", ""));
			txtUserName.setText(userProfile_Arr[14].replace("anyType{}", ""));
			txtPassword.setText(userProfile_Arr[15].replace("anyType{}", ""));

			spSecQuestion.setSelection(Integer.parseInt(userProfile_Arr[16].replace("anyType{}", "0")));

			if (!userProfile_Arr[18].equalsIgnoreCase("0")) {
				txtRecentActivity.setText(userProfile_Arr[18]
						+ " updates this week");
			} else {
				txtRecentActivity.setText("No updates this week");
			}
			if (!userProfile_Arr[19].equalsIgnoreCase("0")) {
				txtRecentView.setText(userProfile_Arr[18] + " views this week");
			} else {
				txtRecentView.setText("No views this week");
			}
		} catch (Exception e) {
			// TODO: handle exception

			alert.Alertbox("Exception", e.toString());
		}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		txtFullname.setText(pref.getString(PREFS_FULLNAME, null));
		txtPosition.setText(pref.getString(PREF_POSITION, null));
		txtWorkPlace.setText(pref.getString(PREF_WORK_PLACE, null));
		setProfileImage(pref.getString(PREF_PROF_IMG, null));
	}
} // end class
