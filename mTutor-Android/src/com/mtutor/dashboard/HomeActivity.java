/*
 * Copyright (C) 2011 Wglxy.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mtutor.dashboard;

import com.mtutor.dashboard.R;
import com.mtutor.notification.Alerts;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends DashboardActivity {

	ImageView imgProfileImage;
	TextView txtFullname, txtPosition, txtWorkPlace;
	Alerts alert;

	private static final String PREFS_NAME = "MyPrefsFile";
	private static final String PREF_USERID = "userid";
	public static final String PREFS_FULLNAME = "fullname";
	private static final String PREF_POSITION = "position";
	private static final String PREF_WORK_PLACE = "work_place";
	private static final String PREF_PROF_IMG = "profileImg";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard);
		setStartUp();
	}

	public void setStartUp() {
		alert = new Alerts(this);
		txtFullname = (TextView) findViewById(R.id.txtName);
		txtPosition = (TextView) findViewById(R.id.txtPosition);
		txtWorkPlace = (TextView) findViewById(R.id.txtWorkPlace);
		imgProfileImage = (ImageView) findViewById(R.id.imgProfPic);

		SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		txtFullname.setText(pref.getString(PREFS_FULLNAME, null));
		txtPosition.setText(pref.getString(PREF_POSITION, null));
		txtWorkPlace.setText(pref.getString(PREF_WORK_PLACE, null));
		setProfileImage(pref.getString(PREF_PROF_IMG, null));
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
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

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
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
		}
	}

} // end class
