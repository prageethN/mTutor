package com.mtutor.main;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;

import com.mtutor.dashboard.R;
import com.mtutor.dashboard.R.color;
import com.mtutor.notification.Alerts;
import com.mtutor.servicecall.WebServiceCaller;
import com.mtutor.user.SignUpActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {
	/** Called when the activity is first created. */

	Button display, cancel, signup;
	TextView linkRegister;

	private static final String PREFS_NAME = "MyPrefsFile";
	private static final String PREF_USERID = "userid";
	public static final String PREFS_FULLNAME = "fullname";
	private static final String PREF_POSITION = "position";
	private static final String PREF_WORK_PLACE = "work_place";
	private static final String PREF_PROF_IMG = "profileImg";

	private static final String PATH = "/sdcard/";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		setStartUp();

	}

	void setStartUp() {

		display = (Button) findViewById(R.id.sumbit);
		display.setOnClickListener(this);
		cancel = (Button) findViewById(R.id.cancel);
		cancel.setOnClickListener(this);
		linkRegister = (TextView) findViewById(R.id.link_to_register);
		linkRegister.setOnClickListener(this);

	}

	@Override
	public void onClick(View view) {

		if (view == display) {

			Alerts alert = new Alerts(this);

			EditText username = (EditText) findViewById(R.id.txtUserName);
			EditText password = (EditText) findViewById(R.id.txtPassword);

			String _username = username.getText().toString();
			String _password = password.getText().toString();

			if (_username.equals("") | _password.equals("")) {
				alert.Alertbox("Access Denied",
						"Please enter both user name and passwrod");
				return;
			} else {

				String _name = "", _userid = "", _position = "", _workplace = "", _profimage = "";
				String userID = "0000";
				try {
					AsyncTask<String, Integer, Long> x;
					WebServiceCaller webServiceCaller = new WebServiceCaller();
					
					StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				       .detectNetwork() // or .detectAll() for all detectable problems
				       //.penaltyDialog()  //show a dialog
				       .permitNetwork() //permit Network access 
				       .build());
					userID = webServiceCaller.authenticateUser(_username,
						_password);

					if (!userID.equalsIgnoreCase("error")) {

						String[] userProfile_Arr = null;
						userProfile_Arr = webServiceCaller
								.getUserprofile(userID);
						_name = userProfile_Arr[1] + " " + userProfile_Arr[2];
						_position = userProfile_Arr[6];
						_workplace = userProfile_Arr[7];
						_profimage = getProfileImage(userID,
								userProfile_Arr[13]);
						_userid = userID;

						getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit()
								.putString(PREFS_FULLNAME, _name)
								.putString(PREF_POSITION, _position)
								.putString(PREF_WORK_PLACE, _workplace)
								.putString(PREF_PROF_IMG, _profimage)
								.putString(PREF_USERID, _userid).commit();

						username.setText("");
						password.setText("");

						Intent i = new Intent(getApplicationContext(),
								MainActivity.class);
						startActivity(i);

					} else {
						alert.Alertbox("Access Denied",
								"Please enter correct login Details");
						return;
					}
				} catch (Exception ex) {

					Toast.makeText(
							LoginActivity.this,
							"ERROR! Connection to the server is lost, please try again",
							Toast.LENGTH_SHORT).show();
				}
			}

		}
		if (view == cancel) {

			AlertDialog.Builder builder = new AlertDialog.Builder(
					LoginActivity.this);
			builder.setMessage("Are you sure you want to exit?")
					.setCancelable(false)
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int id) {
									LoginActivity.this.finish();
								}
							})
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							});
			AlertDialog alert = builder.create();
			alert.show();
		}
		if (view == linkRegister) {

			linkRegister.setTextColor(color.gray);
			Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
			startActivity(i);
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
			
			ucon.setConnectTimeout(10);
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