package com.mtutor.tutorial;

import com.mtutor.dashboard.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class TuteOneActivity extends Activity implements OnClickListener{
	
private Button btnClickMe;

@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_tone);
	setStartUp();
}

void setStartUp(){
	
	btnClickMe=(Button)findViewById(R.id.btnClickMe);
	btnClickMe.setOnClickListener(this);
	
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
public void onClick(View v) {
	// TODO Auto-generated method stub
	if(v==btnClickMe){
		
		Toast.makeText(this, "Hiii", Toast.LENGTH_LONG).show();
		
	}
}

}
