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
import com.mtutor.settings.FeedbackActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;

public class F6Activity extends DashboardActivity implements OnClickListener {

	CheckBox chkEnableHistoryTypes;
	LinearLayout linClearHistory;
	RelativeLayout relLayoutAbout, relLayoutFeedback;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_f6);
		setTitleFromActivityLabel(R.id.title_text);
		setStartUp();
	}

	void setStartUp() {

		chkEnableHistoryTypes = (CheckBox) findViewById(R.id.chkEnableHistoryTypes);
		linClearHistory = (LinearLayout) findViewById(R.id.linClearHistory);
		relLayoutAbout = (RelativeLayout) findViewById(R.id.relLayoutAbout);
		relLayoutAbout.setOnClickListener(this);
		relLayoutFeedback = (RelativeLayout) findViewById(R.id.relLayoutFeedback);
		relLayoutFeedback.setOnClickListener(this);
		chkEnableHistoryTypes
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							linClearHistory
									.setVisibility(android.view.View.VISIBLE);
						} else {
							linClearHistory
									.setVisibility(android.view.View.GONE);
						}
					}
				});

	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if (view == relLayoutAbout) {

			Intent i = new Intent(getApplicationContext(), AboutActivity.class);
			startActivity(i);
		}
		if (view == relLayoutFeedback) {

			Intent i = new Intent(getApplicationContext(),
					FeedbackActivity.class);
			startActivity(i);
		}
	}
} // end class
