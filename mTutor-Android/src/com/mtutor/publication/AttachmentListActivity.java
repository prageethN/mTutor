package com.mtutor.publication;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.mtutor.attachment.AttachmentActivity;
import com.mtutor.dashboard.DashboardActivity;
import com.mtutor.dashboard.R;
import com.mtutor.servicecall.WebServiceCaller;

public class AttachmentListActivity extends DashboardActivity implements
		OnClickListener {

	ListView lvAttachmentList;
	ArrayList<ImageItemDetails> itemAttachmentList;

	TextView txtResultCount;

	WebServiceCaller webServiceCaller;

	String docID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_attachment_list);
		setTitleFromActivityLabel(R.id.title_text);
		setupStart();
	}

	void setupStart() {

		webServiceCaller = new WebServiceCaller();

		if (getIntent().getExtras().getSerializable("DOC_ID") != null) {

			docID = getIntent().getExtras().getSerializable("DOC_ID")
					.toString();
		}

		txtResultCount = (TextView) findViewById(R.id.txtResultCount);

		ArrayList<ImageItemDetails> attachmentList = GetAttachmentList();
		lvAttachmentList = (ListView) findViewById(R.id.lvAttachmentList);
		lvAttachmentList.setAdapter(new AttachmentItemListBaseAdapter(this,
				attachmentList));

		lvAttachmentList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				Object o = lvAttachmentList.getItemAtPosition(position);
				ImageItemDetails obj_itemDetails = (ImageItemDetails) o;
				Intent intent = new Intent(getApplicationContext(),
						AttachmentActivity.class);
				intent.putExtra("ATT_ID", obj_itemDetails.getItemId());
				intent.putExtra("DOC_ID", docID);
				startActivity(intent);
			}
		});
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

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub

	}

	private ArrayList<ImageItemDetails> GetAttachmentList() {
		ArrayList<ImageItemDetails> results = new ArrayList<ImageItemDetails>();

		ArrayList<ArrayList> resultList = null;
		ArrayList<String> rowList = null;

		resultList = webServiceCaller.getAttachmentList(docID);

		if (resultList != null) {

			if (resultList.size() > 0) {

				for (int i = 0; i < resultList.size(); i++) {

					rowList = resultList.get(i);

					ImageItemDetails item_details = new ImageItemDetails();
					item_details.setItemID(rowList.get(0));
					item_details.setTextHeader(rowList.get(2));
					item_details.setTextNormal(rowList.get(3));
					item_details
							.setImageNumber(Integer.parseInt(rowList.get(1)));

					results.add(item_details);

				}

			}
			txtResultCount.setText(results.size() + " attachments");
		}

		return results;
	}
}
