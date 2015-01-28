package com.hungtdo.demoactionbar;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ComposeActivity extends ActionBarActivity implements OnItemClickListener {

	@Override
	public void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ListView lv = new ListView(this);
		lv.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		setContentView(lv);
		lv.setOnItemClickListener(this);
		
		//Set-up List
		List<String> list = new ArrayList<String>();
		for (int i = 1; i <= 100; i++) {
			String item = "Item " + i;
			list.add(item);
		}
		
		//Fill adapter
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
		lv.setAdapter(adapter);
		
		//Enable Up button
		showUpButton();
	};
	
	private void showUpButton() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	private void gotoDetails() {
		Intent intent = new Intent(this, DetailsActivity.class);
		startActivity(intent);
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
		gotoDetails();
	}

}
