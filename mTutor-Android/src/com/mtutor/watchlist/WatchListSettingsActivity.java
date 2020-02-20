package com.mtutor.watchlist;

import java.util.Calendar;
import java.util.Locale;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.mtutor.attachment.AttachmentActivity;
import com.mtutor.dashboard.DashboardActivity;
import com.mtutor.dashboard.R;
import com.mtutor.publication.ItemInfoActivity;

public class WatchListSettingsActivity extends DashboardActivity {

	Button btnPickDate, btnPickTime;
	TextView txtReminderDate, txtReminderTime,txtHeaderText;
	CheckBox chkEnableReminder;
	LinearLayout linReminder;

	private int mYear, mMonth, mDay, mHour, mMinute;
	static final int DATE_DIALOG_ID = 0;
	static final int TIME_DIALOG_ID = 1;

	Calendar remDate, remTime;
	
	String typeID,docID,attID,authorID,itemTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_watchlist_settings);
		setTitleFromActivityLabel(R.id.title_text);

		setStartUp();
	}

	void setStartUp() {
		
		if (getIntent().getExtras().getSerializable("ITEM_TYPE") != null) {

			typeID = getIntent().getExtras().getSerializable("ITEM_TYPE")
					.toString();
			attID = getIntent().getExtras().getSerializable("ATT_ID")
					.toString();
			docID = getIntent().getExtras().getSerializable("DOC_ID")
			.toString();
			authorID = getIntent().getExtras().getSerializable("AUTHOR_ID")
			.toString();
			itemTitle= getIntent().getExtras().getSerializable("ITEM_TITLE")
			.toString();
		}

		btnPickDate = (Button) findViewById(R.id.btnPickDate);
		btnPickTime = (Button) findViewById(R.id.btnPickTime);
		txtReminderDate = (TextView) findViewById(R.id.txtReminderDate);
		txtReminderTime = (TextView) findViewById(R.id.txtReminderTime);
		txtHeaderText=(TextView)findViewById(R.id.txtHeaderText);
		chkEnableReminder = (CheckBox) findViewById(R.id.chkEnableReminder);
		linReminder = (LinearLayout) findViewById(R.id.linReminder);

		txtHeaderText.setText(itemTitle);
		
		remTime = Calendar.getInstance();
		chkEnableReminder
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							linReminder
									.setVisibility(android.view.View.VISIBLE);
						} else {
							linReminder.setVisibility(android.view.View.GONE);
						}
					}
				});

		// add a click listener to the button
		btnPickDate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});

		// get the current date
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		// display the current date (this method is below)
		updateDateDisplay();

		// add a click listener to the button
		btnPickTime.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(TIME_DIALOG_ID);
			}
		});
		// get the current time
		final Calendar _c = Calendar.getInstance();
		mHour = _c.get(Calendar.HOUR_OF_DAY);
		mMinute = _c.get(Calendar.MINUTE);

		// display the current date
		updateTimeDisplay();
	}

	public void onClickViewitem(View view){
		
		if(typeID.equalsIgnoreCase("1")){
			
			Intent intent = new Intent(getApplicationContext(),
					ItemInfoActivity.class);	
				
				intent.putExtra("DOC_ID", docID);
				intent.putExtra("AUTHOR_ID", authorID);				
				startActivity(intent);
		}else{
			Intent intent = new Intent(getApplicationContext(),
					AttachmentActivity.class);	
								
				intent.putExtra("ATT_ID", attID);
				intent.putExtra("DOC_ID", docID);
				startActivity(intent);
			
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
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	// updates the date in the TextView

	private void updateDateDisplay() {

		remDate = Calendar.getInstance();
		remDate.set(mYear, mMonth, mDay);
		String month = remDate.getDisplayName(Calendar.MONTH, Calendar.LONG,
				Locale.US);

		txtReminderDate.setText(new StringBuilder().append(month).append(",")
		// Month is 0 based so add 1
				.append(mDay).append(" ").append(mYear).append(" "));
	}

	// updates the time we display in the TextView
	private void updateTimeDisplay() {

		remTime.set(mYear, mMonth, mDay, mHour, mMinute);
		String am_pm = remTime.getDisplayName(Calendar.AM_PM, Calendar.SHORT,
				Locale.US);
		int _hour = remTime.get(Calendar.HOUR);
		if (_hour == 0) {
			_hour = 12;
		}
		txtReminderTime.setText(new StringBuilder().append(pad(_hour))
				.append(":").append(pad(mMinute)).append(" ").append(am_pm));
	}

	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}

	// the callback received when the user "sets" the date in the dialog
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDateDisplay();
		}
	};

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		case TIME_DIALOG_ID:

			return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute,
					false);

		}
		return null;
	}

	// the callback received when the user "sets" the time in the dialog
	private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// TODO Auto-generated method stub

			mHour = hourOfDay;
			mMinute = minute;
			updateTimeDisplay();

		}
	};

}
