package com.hungtdo.demoactionbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	public static final String KEY_SEARCHABLE = "searchable";
	public static final String KEY_NAME = "name";
	private TextView tvSearch;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tvSearch = (TextView) findViewById(R.id.tvSearch);
		
		//Set-up Tab navigations
	    ActionBar actionBar = getSupportActionBar();
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    actionBar.setDisplayShowTitleEnabled(false);
	    
	    //Set-up Tabs
	    ActionBar.Tab tab = actionBar.newTab()
	    		.setText("Tab 1")
	    		.setTabListener(new TabListener<TabContentFragment>(MainActivity.this, "Tab Content 1", TabContentFragment.class));
	    
	    ActionBar.Tab tab2 = actionBar.newTab()
	    		.setText("Tab 2")
	    		.setTabListener(new TabListener<TabContentFragment>(MainActivity.this, "Tab Content 2", TabContentFragment.class));
	    
	    ActionBar.Tab tab3 = actionBar.newTab()
	    		.setText("Tab 3")
	    		.setTabListener(new TabListener<TabContentFragment>(MainActivity.this, "Tab Content 3", TabContentFragment.class));
	    
	    //Add tabs to Action Bar
	    actionBar.addTab(tab);
	    actionBar.addTab(tab2);
	    actionBar.addTab(tab3);
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		//Add action view (SearchView) to ActionBar
		MenuItem searchItem = menu.findItem(R.id.action_search);
	    SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
	    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String paramString) {
				Toast.makeText(MainActivity.this, "Searching: " + paramString, Toast.LENGTH_SHORT).show();
				gotoSearch(paramString);
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(String paramString) {
				String value = "Search App: " + paramString;
				tvSearch.setText(value);
				return false;
			}
		});
	    
	    //Add Share action provider
	    MenuItem shareItem = menu.findItem(R.id.action_share);
	    ShareActionProvider mShareActionProvider = (ShareActionProvider)
	            MenuItemCompat.getActionProvider(shareItem);
	    mShareActionProvider.setShareIntent(getDefaultIntent());
	    
		return true;
	}
	
	private Intent getDefaultIntent() {
		String shareBody = "Here is the share content body";
		Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
		return sharingIntent;
	}
	
	private void gotoSearch(String str) {
		Intent i = new Intent(this, SearchResultActivity.class);
		i.putExtra(KEY_SEARCHABLE, "" + str);
		startActivity(i);
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
	
	public void toogleAction(View v) {
		ActionBar ab = getSupportActionBar();
		boolean isVisble = !ab.isShowing();
		if(isVisble) {
			ab.show();
		} else {
			ab.hide();
		}
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
	
	public class TabListener<T extends Fragment> implements ActionBar.TabListener {
		
	    private TabContentFragment mFragment;
	    private final Activity mActivity;
	    private final String mTag;
	    private final Class<T> mClass;

	    /** Constructor used each time a new tab is created.
	      * @param activity  The host Activity, used to instantiate the fragment
	      * @param tag  The identifier tag for the fragment
	      * @param clz  The fragment's Class, used to instantiate the fragment
	      */
	    public TabListener(Activity activity, String tag, Class<T> clz) {
	        mActivity = activity;
	        mTag = tag;
	        mClass = clz;
	    }

	    /* The following are each of the ActionBar.TabListener callbacks */

	    public void onTabSelected(Tab tab, FragmentTransaction ft) {
	        // Check if the fragment is already initialized
	        if (mFragment == null) {
	            // If not, instantiate and add it to the activity
//	            mFragment = Fragment.instantiate(mActivity, mClass.getName());
	        	Bundle b = new Bundle();
	        	b.putString(KEY_NAME, mTag);
	        	mFragment = new TabContentFragment();
	        	mFragment.setArguments(b);
	            ft.add(R.id.fragment_content, mFragment, mTag);
	        } else {
	            // If it exists, simply attach it in order to show it
	            ft.attach(mFragment);
	        }
	    }

	    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	        if (mFragment != null) {
	            // Detach the fragment, because another one is being attached
	            ft.detach(mFragment);
	        }
	    }

	    public void onTabReselected(Tab tab, FragmentTransaction ft) {
	        // User selected the already selected tab. Usually do nothing.
	    }
	}

	 public class TabContentFragment extends Fragment {
	        private String mText;

	        public TabContentFragment() {
	        	
	        }
	        
	        @Override
	        public void onCreate(Bundle savedInstanceState) {
	        	super.onCreate(savedInstanceState);
	        	mText = getArguments().getString(KEY_NAME);
	        }

	        public String getText() {
	            return mText;
	        }

	        @Override
	        public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                Bundle savedInstanceState) {
	            View fragView = inflater.inflate(R.layout.layout_tab_content, container, false);

	            TextView text = (TextView) fragView.findViewById(R.id.text);
	            text.setText(mText);

	            return fragView;
	        }

	    }
}
