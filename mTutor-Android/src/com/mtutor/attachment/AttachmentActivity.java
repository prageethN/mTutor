package com.mtutor.attachment;

import com.mtutor.dashboard.AboutActivity;
import com.mtutor.dashboard.F1Activity;
import com.mtutor.dashboard.HomeActivity;
import com.mtutor.dashboard.R;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

/**
 * @author Adil Soomro
 * 
 */
public class AttachmentActivity extends TabActivity {
	/** Called when the activity is first created. */

	String docID, attID;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_activity);

		if (getIntent().getExtras().getSerializable("ATT_ID") != null) {

			docID = getIntent().getExtras().getSerializable("DOC_ID")
					.toString();
			attID = getIntent().getExtras().getSerializable("ATT_ID")
					.toString();
		}

		setTabs();
	}

	private void setTabs() {
		addTab("Info", R.drawable.tab_info, AttachmentInfoActivity.class);
		addTab("Related", R.drawable.tab_related,
				AttachmentSuggetionActivity.class);
		addTab("Comments", R.drawable.tab_comment,
				AttachmentCommentActivity.class);

	}

	private void addTab(String labelId, int drawableId, Class<?> c) {
		TabHost tabHost = getTabHost();
		Intent intent = new Intent(this, c);
		intent.putExtra("ATT_ID", attID);
		intent.putExtra("DOC_ID", docID);
		TabHost.TabSpec spec = tabHost.newTabSpec("tab" + labelId);

		View tabIndicator = LayoutInflater.from(this).inflate(
				R.layout.tab_indicator, getTabWidget(), false);
		TextView title = (TextView) tabIndicator.findViewById(R.id.title);
		title.setText(labelId);
		ImageView icon = (ImageView) tabIndicator.findViewById(R.id.icon);
		icon.setImageResource(drawableId);

		spec.setIndicator(tabIndicator);
		spec.setContent(intent);
		tabHost.addTab(spec);
	}

	public void onClickAbout(View v) {
		startActivity(new Intent(getApplicationContext(), AboutActivity.class));
	}

	public void onClickHome(View v) {
		goHome(this);
	}

	public void goHome(Context context) {
		final Intent intent = new Intent(context, HomeActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
	}

	public void setTitleFromActivityLabel(int textViewId) {
		TextView tv = (TextView) findViewById(textViewId);
		if (tv != null)
			tv.setText(getTitle());
	}

	public void onClickSearch(View v) {
		startActivity(new Intent(getApplicationContext(), F1Activity.class));
	}
}