package com.itmstm.erohaiku;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
	private static final String TAG = "MainActivity";
	private KuListManager mKuListManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mKuListManager = new KuListManager( this );
        
        setContentView(R.layout.activity_main);
        getActionBar().setDisplayHomeAsUpEnabled(false);

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // For each of the sections in the app, add a tab to the action bar.
        actionBar.addTab(actionBar.newTab().setText(R.string.title_section1).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(R.string.title_section2).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(R.string.title_section3).setTabListener(this));
        
        Log.d(TAG, "onCreate ends");
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
            getActionBar().setSelectedNavigationItem(
                    savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
        }
        Log.d(TAG, "onCreate ends");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM,
                getActionBar().getSelectedNavigationIndex());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, show the tab contents in the container
        Bundle args = new Bundle();
        args.putInt(KuListFragment.ARG_SECTION_NUMBER, tab.getPosition() + 1);
        
        KuListFragment fragment = new KuListFragment();
        fragment.setArguments(args);
        fragment.setKuListManager( mKuListManager );
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
        
        Log.d(TAG, "Tab selected:" + tab.toString());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }
}
