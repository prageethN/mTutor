package com.mtutor.scannar;

import com.mtutor.notification.Alerts;
import com.mtutor.publication.ItemInfoActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class ScanActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.main);
		processQRCode();
	}

	public void processQRCode() {

		startQRCodeReader();

	}

	public void startQRCodeReader() {
		try {
			Intent intent = new Intent("com.google.zxing.client.android.SCAN");
			intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
			startActivityForResult(intent, 0);

		} catch (Exception ex) {

			Toast.makeText(this, "Error in QR reading", 10);
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
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				String content = intent.getStringExtra("SCAN_RESULT");
				final Alerts alert = new Alerts(this);
				// String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
				/*
				 * WebServiceCaller webServiceCaller = new WebServiceCaller();
				 * 
				 * boolean isValidQR =
				 * webServiceCaller.isValidReferenceCode("qwert");
				 * alert.Alertbox("QR Status", "Scanned QR is "+content);
				 */
				Intent intnt = new Intent(ScanActivity.this,
						ItemInfoActivity.class);
				startActivity(intnt);
			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(this, "Error in QR reading", 10);
			}
		}

	}

}
