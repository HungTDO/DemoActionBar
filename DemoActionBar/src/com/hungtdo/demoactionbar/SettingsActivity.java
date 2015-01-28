package com.hungtdo.demoactionbar;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView tv = new TextView(this);
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		tv.setLayoutParams(params);
		tv.setGravity(Gravity.CENTER);
		tv.setTextSize(50f);
		tv.setTextColor(Color.RED);
		tv.setText(R.string.action_settings);
		setContentView(tv);
		
		//Enable Up Button
		setUpButton();
		
		//Set event
		tv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				Toast.makeText(SettingsActivity.this, "Finish", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private void setUpButton() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}
}
