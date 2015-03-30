package com.hungtdo.demoactionbar;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SearchResultActivity extends ActionBarActivity {

	private ListView lvSearch;
	private String mSearchValue;
	private ProgressDialog dialog;
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_result_search);
		mContext = SearchResultActivity.this;
		
		// Set-up Up Button
		setUpButton();

		lvSearch = (ListView) findViewById(R.id.lvSearch);
		mSearchValue = getIntent().getStringExtra(MainActivity.KEY_SEARCHABLE);

		// Search data
		new SearchThread().execute();
	}

	private void setUpButton() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}
	
	private void fillList(List<String> list) {
		// Fill adapter
		if (list != null && !list.isEmpty()) {
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, list);
			lvSearch.setAdapter(adapter);
		}
	}
	
	private class SearchThread extends AsyncTask<Void, Void, ArrayList<String>> {

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(mContext);
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			dialog.setTitle(null);
	        dialog.setMessage("Loading...");
	        dialog.setIndeterminate(false);
	        dialog.setCancelable(false);
	        dialog.setOnCancelListener(null);
			super.onPreExecute();
		}
		
		private ArrayList<String> searchAppByName(String strApp) {
			String searchValue = strApp.toLowerCase();
			ArrayList<String> arr = new ArrayList<String>();
			// Filter all Applications in Launcher
			Intent ifilter = new Intent(Intent.ACTION_MAIN);
			ifilter.addCategory(Intent.CATEGORY_LAUNCHER);
			// Get List ResolveInfo with Intent filter
			PackageManager pm = mContext.getPackageManager();
			List<ResolveInfo> mInfoApp = pm.queryIntentActivities(ifilter, 0);
			int sizeInfo = mInfoApp.size();
			for (int i = 0; i < sizeInfo; i++) {
				ResolveInfo info = mInfoApp.get(i);
				String appName = info.loadLabel(pm).toString();
				String temp = appName.toLowerCase();
				if(temp.indexOf(searchValue) >= 0) {
					arr.add(appName);
				}
			}
			return arr;
		}


		@Override
		protected ArrayList<String> doInBackground(Void... params) {
			return searchAppByName(mSearchValue);
		}

		@Override
		protected void onPostExecute(ArrayList<String> result) {
			boolean show = dialog.isShowing();
			if (show) {
				dialog.dismiss();
			}
			
			//Fill list
			fillList(result);
			super.onPostExecute(result);
		}
	}

}
