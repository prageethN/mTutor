package com.mtutor.user;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.mtutor.notification.Alerts;
import com.mtutor.servicecall.WebServiceCaller;
import com.mtutor.dashboard.R;
import com.mtutor.dashboard.R.color;
import com.mtutor.main.LoginActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends Activity implements OnClickListener {
	TextView linkLogin;
	EditText txtPosition, txtWorkPlace, txtFirstName, txtLastName, txtEmail,
			txtAddress, txtContactNo, txtFacebook, txtGooglePlus, txtLinkedIn,
			txtSkype, txtwitter, txtUserName, txtPassword, txtRepeatPasword,
			txtAnswer;
	Spinner spSecQuestion;
	ImageView profilePicture;
	Button btnSignUp, btnUploadImage;

	private static final int SELECT_PHOTO = 100;
	private static final String TEMP_PHOTO_FILE = "temporary_holder.jpg";
	private static final int REQ_CODE_PICK_IMAGE = 100;
	Boolean isImagePick = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		setStartUp();

	}

	private void setStartUp() {

		ArrayAdapter<CharSequence> adapter;
		profilePicture = (ImageView) findViewById(R.id.imgProfPic);
		txtPosition = (EditText) findViewById(R.id.txtPosition);
		txtWorkPlace = (EditText) findViewById(R.id.txtWorkPlace);
		txtFirstName = (EditText) findViewById(R.id.txtFirstname);
		txtLastName = (EditText) findViewById(R.id.txtLastname);
		txtEmail = (EditText) findViewById(R.id.txtEmail);
		txtAddress = (EditText) findViewById(R.id.txtAddress);
		txtContactNo = (EditText) findViewById(R.id.txtContactNo);
		txtFacebook = (EditText) findViewById(R.id.txtFacebook);
		txtGooglePlus = (EditText) findViewById(R.id.txtGooglePlus);
		txtLinkedIn = (EditText) findViewById(R.id.txtLinkedIn);
		txtSkype = (EditText) findViewById(R.id.txtSkype);
		txtwitter = (EditText) findViewById(R.id.txtTwitter);
		txtUserName = (EditText) findViewById(R.id.txtUserName);
		txtPassword = (EditText) findViewById(R.id.txtPassWord);
		txtRepeatPasword = (EditText) findViewById(R.id.txtRepeatPassword);
		txtAnswer = (EditText) findViewById(R.id.txtAnswer);
		spSecQuestion = (Spinner) findViewById(R.id.spSecQuestion);

		linkLogin = (TextView) findViewById(R.id.link_to_login);
		linkLogin.setOnClickListener(this);
		btnSignUp = (Button) findViewById(R.id.btn_signup);
		btnSignUp.setOnClickListener(this);
		btnUploadImage = (Button) findViewById(R.id.btnUploadImage);
		btnUploadImage.setOnClickListener(this);
		adapter = ArrayAdapter.createFromResource(this, R.array.sec_question,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spSecQuestion.setAdapter(adapter);
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
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if (view == linkLogin) {

			linkLogin.setTextColor(color.gray);
			Intent i = new Intent(getApplicationContext(), LoginActivity.class);
			startActivity(i);
		}
		if (view == btnUploadImage) {
			pickImageFromGallery();
		}
		if (view == btnSignUp) {

			if (isInputValid())
				registerNewUser();

		}
	}

	public String getMaxUserID() {

		String memID = null;
		WebServiceCaller webServiceCaller = new WebServiceCaller();
		memID = webServiceCaller.getMaxUserID();
		return memID;
	}

	public Boolean isInputValid() {
		Alerts alert = new Alerts(this);
		String heading = "Invalid Input Data", message = "";

		if (txtPosition.getText().toString().equals("")) {

			message = "Position can not be empty";
			alert.Alertbox(heading, message);
			return false;
		}
		if (txtWorkPlace.getText().toString().equals("")) {

			message = "Working place can not be empty";
			alert.Alertbox(heading, message);
			return false;
		}
		if (txtFirstName.getText().toString().equals("")) {

			message = "First name can not be empty";
			alert.Alertbox(heading, message);
			return false;
		}
		if (txtEmail.getText().toString().equals("")) {

			message = "User name can not be empty";
			alert.Alertbox(heading, message);
			return false;
		} else if (!txtEmail.getText().toString()
				.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
			message = "Invalid Email address";
			alert.Alertbox("Invalid Input Data", message);
			return false;
		}
		if (txtUserName.getText().toString().equals("")) {

			message = "User name can not be empty";
			alert.Alertbox(heading, message);
			return false;
		}
		if (txtPassword.getText().toString().equals("")) {

			message = "Password place can not be empty";
			alert.Alertbox(heading, message);
			return false;
		}
		if (txtAnswer.getText().toString().equals("")) {

			message = "Answer name can not be empty";
			alert.Alertbox(heading, message);
			return false;
		}
		if (!(txtPassword.getText().toString().equals(txtRepeatPasword
				.getText().toString()))) {

			message = "Password and repeat password must be same";
			alert.Alertbox(heading, message);
			return false;
		}
		return true;
	}

	public void registerNewUser() {
		Boolean isSuccess = false;
		Integer spSecQu = spSecQuestion.getSelectedItemPosition();

		ArrayList<String> userDetail = new ArrayList<String>();

		userDetail.add(0, getProfileImage());
		userDetail.add(1, getMaxUserID());
		userDetail.add(2, (String) txtPosition.getText().toString());
		userDetail.add(3, (String) txtWorkPlace.getText().toString());
		userDetail.add(4, (String) txtFirstName.getText().toString());
		userDetail.add(5, (String) txtLastName.getText().toString());
		userDetail.add(6, (String) txtEmail.getText().toString());
		userDetail.add(7, (String) txtAddress.getText().toString());
		userDetail.add(8, (String) txtContactNo.getText().toString());
		userDetail.add(9, (String) txtFacebook.getText().toString());
		userDetail.add(10, (String) txtGooglePlus.getText().toString());
		userDetail.add(11, (String) txtLinkedIn.getText().toString());
		userDetail.add(12, (String) txtSkype.getText().toString());
		userDetail.add(13, (String) txtwitter.getText().toString());
		userDetail.add(14, (String) txtUserName.getText().toString());
		userDetail.add(15, (String) txtPassword.getText().toString());
		userDetail.add(16, spSecQu.toString());
		userDetail.add(17, (String) txtAnswer.getText().toString());

		try {
			WebServiceCaller webServiceCaller = new WebServiceCaller();
			isSuccess = webServiceCaller.registerNewUser(userDetail);
			isSuccess = true;
			if (isSuccess) {
				txtPosition.setText("");
				txtWorkPlace.setText("");
				txtFirstName.setText("");
				txtLastName.setText("");
				txtEmail.setText("");
				txtAddress.setText("");
				txtContactNo.setText("");
				txtFacebook.setText("");
				txtGooglePlus.setText("");
				txtLinkedIn.setText("");
				txtSkype.setText("");
				txtwitter.setText("");
				txtUserName.setText("");
				txtPassword.setText("");
				txtRepeatPasword.setText("");
				txtAnswer.setText("");
				spSecQuestion.setSelection(0);
				profilePicture
						.setBackgroundResource(R.drawable.img_default_propic);
				Toast.makeText(SignUpActivity.this, "Successfully Registered",
						Toast.LENGTH_SHORT).show();
				finish();
			} else {
				Toast.makeText(
						SignUpActivity.this,
						"ERROR! Connection to the server is lost, please try again",
						Toast.LENGTH_SHORT).show();
			}
		} catch (Exception ex) {
			Toast.makeText(
					SignUpActivity.this,
					"ERROR! Connection to the server is lost, please try again",
					Toast.LENGTH_SHORT).show();
		}

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
					profilePicture.setImageBitmap(selectedImage);
					profilePicture.setAdjustViewBounds(true);
					profilePicture.setMinimumHeight(177);
					profilePicture.setMinimumWidth(177);
					profilePicture.setMaxHeight(177);
					profilePicture.setMaxWidth(177);
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
}
