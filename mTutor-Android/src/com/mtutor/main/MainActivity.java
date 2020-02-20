package com.mtutor.main;

import com.mtutor.dashboard.HomeActivity;
import com.mtutor.dashboard.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity implements Runnable {

	boolean isRunning = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		isRunning = true;

	}

	@Override
	public void onStart() {
		super.onStart();

		Thread background = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					int currentcount = 0;
					int maxCount = 5;

					while (currentcount < maxCount && isRunning) {
						try {
							Thread.sleep(500);
							currentcount = currentcount + 1;
						} catch (InterruptedException e) {
							return;
						} catch (Exception e) {
							return;
						}
					}

					startActivity(new Intent(MainActivity.this,
							HomeActivity.class));

				} catch (Throwable t) {
				}
			}
		});
		isRunning = true;
		// start the background thread
		background.start();
		isRunning = true;

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int currentcount = 0;
		int maxCount = 5;

		while (currentcount < maxCount && isRunning) {
			try {
				Thread.sleep(500);
				currentcount = currentcount + 1;
			} catch (InterruptedException e) {
				return;
			} catch (Exception e) {
				return;
			}

		}
		startActivity(new Intent(getApplicationContext(), HomeActivity.class));
	}

	@Override
	public void onStop() {
		super.onStop();
		isRunning = false;

	}

	@Override
	public void onPause() {
		super.onPause();
		isRunning = false;
		finish();

	}
}
