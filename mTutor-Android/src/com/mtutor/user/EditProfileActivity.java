package com.mtutor.user;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.apache.http.util.ByteArrayBuffer;

import com.mtutor.connection.ConnectionsActivity;
import com.mtutor.dashboard.DashboardActivity;
import com.mtutor.dashboard.R;
import com.mtutor.notification.Alerts;
import com.mtutor.servicecall.WebServiceCaller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EditProfileActivity extends DashboardActivity implements
		OnClickListener {

	RelativeLayout relLayoutManageConnections;
	TextView txtName, txtPosition, txtWorkPlace, txtConnectionCount;
	EditText txtEditPosition, txtEditWorkPlace, txtFirstname, txtLastname,
			txtEmail, txtAddress, txtContactNo, txtFaceBook, txtGooglePlus,
			txtLinkedIn, txtSkype, txtTwitter, txtPassword, txtRepeatPassword,
			txtAnswer, txtUsername;
	Spinner spSecQuestion;
	ImageView imgProfileImage;

	ArrayAdapter<CharSequence> adapter;

	WebServiceCaller webServiceCaller;
	SharedPreferences pref;
	Alerts alert;

	private static final String PREFS_NAME = "MyPrefsFile";
	private static final String PREF_USERID = "userid";
	public static final String PREFS_FULLNAME = "fullname";
	private static final String PREF_POSITION = "position";
	private static final String PREF_WORK_PLACE = "work_place";
	private static final String PREF_PROF_IMG = "profileImg";

	private static final String PATH = "/sdcard/";

	String userID;

	private static final int SELECT_PHOTO = 100;
	private static final String TEMP_PHOTO_FILE = "temporary_holder.jpg";
	private static final int REQ_CODE_PICK_IMAGE = 100;
	Boolean isImagePick = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_profile);
		setTitleFromActivityLabel(R.id.title_text);
		setStartUp();
	}

	void setStartUp() {

		webServiceCaller = new WebServiceCaller();
		alert = new Alerts(this);
		pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		userID = pref.getString(PREF_USERID, null);

		imgProfileImage = (ImageView) findViewById(R.id.imgProfPic);
		txtPosition = (TextView) findViewById(R.id.txtPosition);
		txtWorkPlace = (TextView) findViewById(R.id.txtWorkPlace);
		txtName = (TextView) findViewById(R.id.txtName);

		txtEditPosition = (EditText) findViewById(R.id.txtEditPosition);
		txtEditWorkPlace = (EditText) findViewById(R.id.txtEditWorkPlace);
		txtFirstname = (EditText) findViewById(R.id.txtFirstname);
		txtLastname = (EditText) findViewById(R.id.txtLastname);
		txtEmail = (EditText) findViewById(R.id.txtEmail);
		txtAddress = (EditText) findViewById(R.id.txtAddress);
		txtContactNo = (EditText) findViewById(R.id.txtContactNo);
		txtFaceBook = (EditText) findViewById(R.id.txtFaceBook);
		txtGooglePlus = (EditText) findViewById(R.id.txtGooglePlus);
		txtLinkedIn = (EditText) findViewById(R.id.txtLinkedIn);
		txtSkype = (EditText) findViewById(R.id.txtSkype);
		txtTwitter = (EditText) findViewById(R.id.txtTwitter);
		txtUsername = (EditText) findViewById(R.id.txtUsername);
		txtPassword = (EditText) findViewById(R.id.txtPassword);
		txtRepeatPassword = (EditText) findViewById(R.id.txtRepeatPassword);
		txtAnswer = (EditText) findViewById(R.id.txtAnswer);

		txtConnectionCount = (TextView) findViewById(R.id.txtConnectionCount);
		spSecQuestion = (Spinner) findViewById(R.id.spSecQuestion);

		adapter = ArrayAdapter.createFromResource(this, R.array.sec_question,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spSecQuestion.setAdapter(adapter);

		relLayoutManageConnections = (RelativeLayout) findViewById(R.id.relLayoutManageConnections);
		relLayoutManageConnections.setOnClickListener(this);

		setProfileInfo();
		setProfileImage(pref.getString(PREF_PROF_IMG, null));
		setProfileCount();
	}

	void setProfileInfo() {

		String[] userProfile_Arr = null;
		userProfile_Arr = webServiceCaller.getUserprofile(userID);
		try {

			txtName.setText(userProfile_Arr[1] + " " + userProfile_Arr[2]);
			txtPosition.setText(userProfile_Arr[6]);
			txtEditPosition.setText(userProfile_Arr[6]);
			txtWorkPlace.setText(userProfile_Arr[7]);
			txtEditWorkPlace.setText(userProfile_Arr[7]);
			txtFirstname.setText(userProfile_Arr[1].replace("anyType{}", ""));
			txtLastname.setText(userProfile_Arr[2].replace("anyType{}", ""));
			txtEmail.setText(userProfile_Arr[3].replace("anyType{}", ""));
			txtAddress.setText(userProfile_Arr[4].replace("anyType{}", ""));
			txtContactNo.setText(userProfile_Arr[5].replace("anyType{}", ""));
			txtFaceBook.setText(userProfile_Arr[8].replace("anyType{}", ""));
			txtGooglePlus.setText(userProfile_Arr[9].replace("anyType{}", ""));
			txtLinkedIn.setText(userProfile_Arr[10].replace("anyType{}", ""));
			txtSkype.setText(userProfile_Arr[11].replace("anyType{}", ""));
			txtTwitter.setText(userProfile_Arr[12].replace("anyType{}", ""));
			txtUsername.setText(userProfile_Arr[14].replace("anyType{}", ""));
			txtPassword.setText(userProfile_Arr[15].replace("anyType{}", ""));
			txtAnswer.setText(userProfile_Arr[17].replace("anyType{}", ""));
			spSecQuestion.setSelection(Integer.parseInt(userProfile_Arr[16]));

		} catch (Exception e) {
			// TODO: handle exception

			alert.Alertbox("Exception", e.toString());
		}
	}

	void setProfileCount() {

		String[] userProfile_Count = null;
		userProfile_Count = webServiceCaller.getUserProfileCount(userID);

		txtConnectionCount.setText("You have " + userProfile_Count[2]
				+ " connections");

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
		if (view == relLayoutManageConnections) {

			Intent i = new Intent(getApplicationContext(),
					ConnectionsActivity.class);
			startActivity(i);
		}
	}

	public void onClickUpdate(View view) {

		if (txtPassword.getText().toString().equalsIgnoreCase("")) {
			alert.Alertbox("Invalid input", "Password can not be empty");

		} else if (!txtPassword.getText().toString()
				.equalsIgnoreCase(txtRepeatPassword.getText().toString())) {
			alert.Alertbox("Invalid input",
					"Password and Repeat password fields should be the same");

		} else {
			updaterUserProfile();
		}
	}

	public void updaterUserProfile() {
		Boolean isSuccess = false;
		Integer spSecQu = spSecQuestion.getSelectedItemPosition();

		ArrayList<String> userDetail = new ArrayList<String>();
		if (!getProfileImage().equalsIgnoreCase("")) {
			userDetail.add(0, getProfileImage());
		} else {
			userDetail.add(0,
					getProfileImage(pref.getString(PREF_PROF_IMG, null)));
		}

		userDetail.add(1, userID);
		userDetail.add(2, (String) txtPosition.getText().toString());
		userDetail.add(3, (String) txtWorkPlace.getText().toString());
		userDetail.add(4, (String) txtFirstname.getText().toString());
		userDetail.add(5, (String) txtLastname.getText().toString());
		userDetail.add(6, (String) txtEmail.getText().toString());
		userDetail.add(7, (String) txtAddress.getText().toString());
		userDetail.add(8, (String) txtContactNo.getText().toString());
		userDetail.add(9, (String) txtFaceBook.getText().toString());
		userDetail.add(10, (String) txtGooglePlus.getText().toString());
		userDetail.add(11, (String) txtLinkedIn.getText().toString());
		userDetail.add(12, (String) txtSkype.getText().toString());
		userDetail.add(13, (String) txtTwitter.getText().toString());
		userDetail.add(14, (String) txtUsername.getText().toString());
		userDetail.add(15, (String) txtPassword.getText().toString());
		userDetail.add(16, spSecQu.toString());
		userDetail.add(17, (String) txtAnswer.getText().toString());

		try {
			WebServiceCaller webServiceCaller = new WebServiceCaller();
			isSuccess = webServiceCaller.updaterUserProfile(userDetail);
			isSuccess = true;
			if (isSuccess) {

				updatePreferences();
				toast("Successfully Updated");
			} else {
				toast("ERROR! Connection to the server is lost, please try again");
			}
		} catch (Exception ex) {
			toast("ERROR! Connection to the server is lost, please try again");
		}

	}

	public void updatePreferences() {
		String[] userProfile_Arr = webServiceCaller.getUserprofile(userID);
		String _profimage = getProfileImage(userID, userProfile_Arr[13]);
		getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
				.edit()
				.putString(PREFS_FULLNAME,
						userProfile_Arr[1] + " " + userProfile_Arr[2])
				.putString(PREF_POSITION, userProfile_Arr[6])
				.putString(PREF_WORK_PLACE, userProfile_Arr[7])
				.putString(PREF_PROF_IMG, _profimage)
				.putString(PREF_USERID, userID).commit();
	}

	public void onClickPickImage(View view) {

		pickImageFromGallery();
	}

	void pickImageFromGallery() {

		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		photoPickerIntent.setType("image/*");
		photoPickerIntent.putExtra("crop", "true");
		photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
		photoPickerIntent.putExtra("outputFormat",
				Bitmap.CompressFormat.JPEG.toString());
		startActivityForResult(photoPickerIntent, REQ_CODE_PICK_IMAGE);
	}

	private Uri getTempUri() {
		return Uri.fromFile(getTempFile());
	}

	private File getTempFile() {
		if (isSDCARDMounted()) {

			File f = new File(Environment.getExternalStorageDirectory(),
					TEMP_PHOTO_FILE);
			try {
				f.createNewFile();
			} catch (IOException e) {

			}
			return f;
		} else {
			return null;
		}
	}

	private boolean isSDCARDMounted() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED))
			return true;
		return false;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent imageReturnedIntent) {
		super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

		switch (requestCode) {
		case REQ_CODE_PICK_IMAGE:
			if (resultCode == RESULT_OK) {
				if (imageReturnedIntent != null) {

					File tempFile = getTempFile();
					String filePath = Environment.getExternalStorageDirectory()
							+ "/temporary_holder.jpg";
					System.out.println("path " + filePath);

					Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
					imgProfileImage.setImageBitmap(selectedImage);
					imgProfileImage.setAdjustViewBounds(true);
					imgProfileImage.setMinimumHeight(177);
					imgProfileImage.setMinimumWidth(177);
					imgProfileImage.setMaxHeight(177);
					imgProfileImage.setMaxWidth(177);
					isImagePick = true;
					// uploadProfilePic(selectedImage);
				}
			}
		}
	}

	String getProfileImage() {
		if (isImagePick) {
			File tempFile = getTempFile();
			String filePath = Environment.getExternalStorageDirectory()
					+ "/temporary_holder.jpg";
			System.out.println("path " + filePath);

			Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			selectedImage.compress(Bitmap.CompressFormat.JPEG, 90, bao);
			byte[] ba = bao.toByteArray();
			String ba1 = Base64.encodeBytes(ba);
			return ba1;
		} else {
			return "";
		}

	}

	String getProfileImage(String profileJpgPath) {

		File tempFile = getTempFile();
		String filePath = profileJpgPath;
		System.out.println("path " + filePath);

		Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		selectedImage.compress(Bitmap.CompressFormat.JPEG, 90, bao);
		byte[] ba = bao.toByteArray();
		String ba1 = Base64.encodeBytes(ba);
		return ba1;

	}

	public String getProfileImage(String userID, String resourceURL) {
		String profileImage = "";
		if (DownloadFromUrl(PATH + userID + ".jpg", resourceURL)) {
			profileImage = PATH + userID + ".jpg";
		} else {
			profileImage = "default";
		}

		return profileImage;
	}

	public Boolean DownloadFromUrl(String fileName, String resourceURL) {
		try {
			URL url = new URL(resourceURL); // you can write here any link
			File file = new File(fileName);

			long startTime = System.currentTimeMillis();
			System.out.print("Starting download......from " + url);
			URLConnection ucon = url.openConnection();
			InputStream is = ucon.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			/*
			 * Read bytes to the Buffer until there is nothing more to read(-1).
			 */
			ByteArrayBuffer baf = new ByteArrayBuffer(50);
			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}

			FileOutputStream fos = new FileOutputStream(file);
			fos.write(baf.toByteArray());
			fos.close();
			System.out.print("Download Completed in"
					+ ((System.currentTimeMillis() - startTime) / 1000)
					+ " sec");

		} catch (IOException e) {
			System.out.print("Error: " + e);
			return false;
		}
		return true;
	}
}