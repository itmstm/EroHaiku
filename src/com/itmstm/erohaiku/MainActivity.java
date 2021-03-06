package com.itmstm.erohaiku;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.cloud.backend.android.CloudBackendActivity;
import com.itmstm.erohaiku.element.KuListManager;
import com.itmstm.erohaiku.fragment.KuContainerFragment;
import com.itmstm.erohaiku.fragment.KuListFragment;

public class MainActivity extends CloudBackendActivity
	implements ActionBar.TabListener, 
			   KuListFragment.MainActivityCallback, 
			   KuContainerFragment.MainActivityCallback {

    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
	private static final String TAG = MainActivity.class.getSimpleName();

	private KuListManager mKuListManager;
	private int mSelectedTab;
	private KuContainerFragment mKuContainerFragment;
	private KuListFragment mKuListFragment;
	private Button mRegisterButton;


	// Constructor
    public MainActivity() {
		super();
	}

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 句を管理するクラスを作成
        mKuListManager = new KuListManager( this );
        
        // バックエンドと更新するために通信用クラスを設定
		mKuListManager.initCloudBackend( getCloudBackend() );
		//mKuListManager.getKuListFromBE();
        
        // set listview
        setContentView(R.layout.activity_main);
        
        // get button
        mRegisterButton = (Button) findViewById( R.id.registerButton);

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // For each of the sections in the app, add a tab to the action bar.
        actionBar.addTab(actionBar.newTab().setText(R.string.title_section1).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(R.string.title_section2).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(R.string.title_section3).setTabListener(this));
        
        // 句を入れるFragment の初期化
        mKuContainerFragment = new KuContainerFragment();
        mKuContainerFragment.setKuListManager( mKuListManager );

        // Fragment managerのPendingタスクの非同期実行
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.ku_container, mKuContainerFragment)
                .commit(); 
        getSupportFragmentManager().executePendingTransactions();
        
        // 句リストを表示するFragmentの初期化
        mKuListFragment = new KuListFragment();
        mKuListFragment.setKuListManager( mKuListManager );
        
        // Fragment managerのPendingタスクの非同期実行
        getSupportFragmentManager().beginTransaction()
        		.replace(R.id.container, mKuListFragment)
                .commit();
        getSupportFragmentManager().executePendingTransactions();
        
        Log.d(TAG, "onCreate ends");
    }

    @Override
	protected void onPostCreate() {
		super.onPostCreate();
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
	public void onListItemSelected() {
		mKuContainerFragment.setKu(mSelectedTab);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
        Log.d(TAG, "Tab selected:" + tab.getPosition());
    	mSelectedTab = tab.getPosition() + 1;
    	
    	// 最初のコールではまだインスタンスが作成されていないのでNull Checkする
    	if( mKuListFragment != null) {
	    	mKuListFragment.showKuList(mSelectedTab);
    	}
    	
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
	}
	
	// 登録ボタン
	public void onRegisterButtonPressed(View view) {
		Log.d(TAG, "登録ボタン押されたよ");
		// 本来ならここで句の登録ページにいく
		// 実装テストのために、現状は単純にCloudEntitiyの登録だけ行う
		mKuListManager.debugRegisterKu( "Test Erohaiku" );
		
	}
	
	//　ランダムピッカーボタン
	public void onRandomPickerPressed(View view) {
		Log.d(TAG, "ランダムボタン押されたよ");
		mKuContainerFragment.randomPick();
	}

	// クリアボタン
	public void onClearButtonPressed(View view) {
		Log.d(TAG, "クリアボタン押されたよ");
		mKuContainerFragment.clearKu();
	}

	// アップデートボタン
	public void onUpdateButtonPressed(View view) {
		mKuListManager.getKuListFromBE();
	}

	@Override
	public int getSelectedTab() {
		return mSelectedTab;
	}

	@Override
	public void setSelectedTab(int i) {
		getActionBar().selectTab( getActionBar().getTabAt(i-1) );
		mSelectedTab = i;
	}

	@Override
	public void setShowKuList(int i) {
		if( mKuListFragment != null) {
	    	mKuListFragment.showKuList( i );
    	}	
	}

}
