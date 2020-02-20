package com.mtutor.publication;

import java.util.ArrayList;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.mtutor.dashboard.DashboardActivity;
import com.mtutor.dashboard.R;

public class AttachmentInfoActivity extends DashboardActivity implements
		OnClickListener {

	ImageView ivLike, ivDislike;
	WebView webViewAttachment;
	Button btnRefresh;
	ImageView imgPreview;
	EditText txtComment, txtPopupComment;
	ListView lvRelated, lvComments;
	ArrayList<ItemDetails> itemComments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_attachment);
		setTitleFromActivityLabel(R.id.title_text);
		setupStartup();

	}

	void setupStartup() {

		TabHost tabs = (TabHost) findViewById(R.id.TabHostAttachment);
		tabs.setup();
		TabHost.TabSpec spec1 = tabs.newTabSpec("tag1");
		spec1.setContent(R.id.tabInfo);
		spec1.setIndicator("Info");
		tabs.addTab(spec1);

		TabHost.TabSpec spec2 = tabs.newTabSpec("tag2");
		spec2.setContent(R.id.tabSuggestions);
		spec2.setIndicator("Related");
		tabs.addTab(spec2);

		TabHost.TabSpec spec3 = tabs.newTabSpec("tag3");
		spec3.setContent(R.id.tabComments);
		spec3.setIndicator("Comments");
		tabs.addTab(spec3);

		webViewAttachment = (WebView) findViewById(R.id.webViewAttachment);
		String pdfString = "https://docs.google.com/viewer?url=http://samplepdf.com/sample.pdf";
		String docString = "http://www.search.org/files/doc/Sample%20Project%20Plan.doc";

		webViewAttachment.getSettings().setJavaScriptEnabled(true);
		webViewAttachment.getSettings().setAllowFileAccess(true);
		webViewAttachment.getSettings().setPluginsEnabled(true);
		String googleDocsUrl = "http://docs.google.com/viewer?url=";
		webViewAttachment
				.loadUrl("https://docs.google.com/viewer?url=http://samplepdf.com/sample.pdf");
		// webViewAttachment.loadUrl("https://docs.google.com/file/d/0B1lPdp2FYMzxN2ZiNzYxYTYtNmE3My00ZDkwLTg1Y2YtYjA0MzI4ZWI3MmUy/edit?authkey=CIf1q5wL&hl=en");

		String widthAndHeight = "width='350' height='250'";
		String videoURL = "http://www.youtube.com/v/DZi6DEJsOJ0?fs=1&amp;hl=nl_NL";

		String temp = "<object "
				+ widthAndHeight
				+ ">"
				+ "<param name='allowFullScreen' value='false'>"
				+ "</param><param name='allowscriptaccess' value='always'>"
				+ "</param><embed src='"
				+ videoURL
				+ "'"
				+ " type='application/x-shockwave-flash' allowscriptaccess='always' allowfullscreen='true'"
				+ widthAndHeight + "></embed></object>";

		webViewAttachment.getSettings().setJavaScriptEnabled(true);
		webViewAttachment.getSettings().setPluginsEnabled(true);
		webViewAttachment.loadData(temp, "text/html", "utf-8");

		ivLike = (ImageView) findViewById(R.id.ivLike);
		ivLike.setOnClickListener(this);
		ivDislike = (ImageView) findViewById(R.id.ivDislike);
		ivDislike.setOnClickListener(this);

		ArrayList<ImageItemDetails> relatedItems = GetRelatedItems();
		lvRelated = (ListView) findViewById(R.id.lvRelated);
		lvRelated.setAdapter(new ImageItemListBaseAdapter(this, relatedItems));

		itemComments = GetComments();
		lvComments = (ListView) findViewById(R.id.lvComments);
		lvComments.setAdapter(new ItemListBaseAdapter(this, itemComments));

		lvComments.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				Object o = lvComments.getItemAtPosition(position);
				ItemDetails obj_itemDetails = (ItemDetails) o;
				Toast.makeText(
						AttachmentInfoActivity.this,
						"You have chosen : " + " "
								+ obj_itemDetails.getTextHeader(),
						Toast.LENGTH_LONG).show();
			}
		});

		txtComment = (EditText) findViewById(R.id.txtComment);
		txtComment.setOnClickListener(this);

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
		if (view == ivLike) {
			ivLike.setImageResource(R.drawable.like_pressed);
			ivDislike.setImageResource(R.drawable.dislike);
		}
		if (view == ivDislike) {
			ivDislike.setImageResource(R.drawable.dislike_pressed);
			ivLike.setImageResource(R.drawable.like);
		}

		if (view == txtComment) {

			postComment();

		}
	}

	private ArrayList<ImageItemDetails> GetRelatedItems() {
		ArrayList<ImageItemDetails> results = new ArrayList<ImageItemDetails>();

		ImageItemDetails item_details = new ImageItemDetails();
		item_details.setTextHeader("Hello world with Java");
		item_details.setTextNormal("Prof. Isuru Jayasooriya");
		item_details.setTextFooter("August 23,2012 | 121 views");
		item_details.setTextExtra("video");
		item_details.setImageNumber(1);
		results.add(item_details);

		item_details = new ImageItemDetails();
		item_details.setTextHeader("Java- basics");
		item_details.setTextNormal("Mr. Nipuna Dissanayake");
		item_details.setTextFooter("August 20,2012 | 51 views");
		item_details.setTextExtra("Audio");
		item_details.setImageNumber(2);
		results.add(item_details);

		item_details = new ImageItemDetails();
		item_details.setTextHeader("Getting familiar with Java");
		item_details.setTextNormal("Dr. Dinusha Kankanamge");
		item_details.setTextFooter("January 23,2011 | 331 views");
		item_details.setTextExtra("PDF");
		item_details.setImageNumber(3);
		results.add(item_details);

		item_details = new ImageItemDetails();
		item_details.setTextHeader("Hello world with Java");
		item_details.setTextNormal("Prof. Isuru Jayasooriya");
		item_details.setTextFooter("August 23,2012 | 121 views");
		item_details.setTextExtra("Docs");
		item_details.setImageNumber(4);
		results.add(item_details);

		return results;
	}

	ArrayList<ItemDetails> results = new ArrayList<ItemDetails>();

	private ArrayList<ItemDetails> GetComments() {

		ItemDetails item_details = new ItemDetails();
		item_details.setTextHeader("prageethkandy");
		item_details.setTextNormal("Waaw, this is awesome..");
		item_details.setTextFooter("August 23,2012 | 9:30 GMT");
		results.add(item_details);

		item_details = new ItemDetails();
		item_details.setTextHeader("Maggie");
		item_details
				.setTextNormal("Yr, it explains the whole stuff pretty nicely");
		item_details.setTextFooter("August 20,2012 | 9:35 GMT");
		results.add(item_details);

		return results;
	}

	void postComment() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Post Comment");
		alert.setMessage("Enter your comment here...");
		// Set an EditText view to get user input
		final EditText input = new EditText(this);
		input.setWidth(LayoutParams.FILL_PARENT);
		input.setHeight(LayoutParams.WRAP_CONTENT);
		alert.setView(input);

		alert.setPositiveButton("Post", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				String txtcomment = input.getText().toString();

				ItemDetails item_details = new ItemDetails();
				item_details = new ItemDetails();
				item_details.setTextHeader("Maggie");
				item_details.setTextNormal(txtcomment);
				item_details.setTextFooter("August 20,2012 | 9:35 GMT");
				results.add(item_details);
			}
		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
					}
				});

		alert.show();

	}
}
