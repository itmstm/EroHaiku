package com.itmstm.erohaiku;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends FragmentActivity 
	implements ActionBar.TabListener, KuListFragment.OnListItemSelectedListener {

    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
	private static final String TAG = "MainActivity";
	private KuListManager mKuListManager;
	private int mSelectedTab;
	private KuContainterFragment mKuContainerFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mKuListManager = new KuListManager( this );
        
        // set listview
        setContentView(R.layout.activity_main);
        

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // For each of the sections in the app, add a tab to the action bar.
        actionBar.addTab(actionBar.newTab().setText(R.string.title_section1).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(R.string.title_section2).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(R.string.title_section3).setTabListener(this));
        
     // 句を入れるFragment の初期化
        mKuContainerFragment = new KuContainterFragment();
        mKuContainerFragment.setKuListManager( mKuListManager );

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.ku_container, mKuContainerFragment)
                .commit(); 

        // Fragment managerのPendingタスクの非同期実行
        getSupportFragmentManager().executePendingTransactions();
        
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
	public void onListItemSelected(int position) {
		Log.d(TAG, "onListItemSelected: " + position );
		mKuContainerFragment.setKu(mSelectedTab, position);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
        Log.d(TAG, "Tab selected:" + tab.getPosition());
    	mSelectedTab = tab.getPosition() + 1;
    	
    	// Bundleでパラメータを渡す
    	Bundle args = new Bundle();
    	args.putInt(KuListFragment.ARG_SECTION_NUMBER, mSelectedTab );
    	
    	 // 句のリストを表示するFragmentの初期化
        KuListFragment kuListFragment = new KuListFragment();
        kuListFragment.setArguments(args);
        
        kuListFragment.setKuListManager( mKuListManager );
        
        getSupportFragmentManager().beginTransaction().replace(R.id.container, kuListFragment)
                .commit();
        
        // Fragment managerのPendingタスクの非同期実行
        getSupportFragmentManager().executePendingTransactions();		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

}
