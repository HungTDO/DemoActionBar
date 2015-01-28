package com.hungtdo.demoactionbar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			gotoSettings();
			return true;
		} else if (id == R.id.action_search) {
			showToast("Click: Search");
			return true;
		} else if (id == R.id.action_compose) {
			showToast("Click: Compose");
			// TODO: Go to compose activity
			gotoCompose();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void openCompose(View v) {
		gotoCompose();
	}
	
	public void openSettings(View v) {
		gotoSettings();
	}
	
	private void gotoCompose() {
		Intent intent = new Intent(this, ComposeActivity.class);
		startActivity(intent);
	}
	
	private void gotoSettings() {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}

	private void showToast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
}
