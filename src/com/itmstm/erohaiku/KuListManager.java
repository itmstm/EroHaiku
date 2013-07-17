package com.itmstm.erohaiku;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.google.cloud.backend.android.CloudBackendMessaging;
import com.google.cloud.backend.android.CloudCallbackHandler;
import com.google.cloud.backend.android.CloudEntity;
import com.google.cloud.backend.android.CloudQuery.Order;
import com.google.cloud.backend.android.CloudQuery.Scope;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class KuListManager {
	private static final int NUM_ADAPTERS = 3; // 上、中、下の３つの句
	private static int mSelectedItem;
	private static KuPosition mSelectedKuPosition;
	private static ArrayAdapter<String> mKuUeListAdapter;
	private static ArrayAdapter<String> mKuNakaListAdapter;
	private static ArrayAdapter<String> mKuShitaListAdapter;

	private List<CloudEntity> mResultList = new LinkedList<CloudEntity>();
	
	public static final String ARG_KU_LIST_MANAGER = "ku_list_manager";
	private static final String KU_UE_ENTITY_NAME = "KU_UE";
	private static final String KU_NAKA_ENTITY_NAME = "KU_NAKA";
	private static final String KU_SHITA_ENTITY_NAME = "KU_SHITA";
	private static final String KU_UE_ENTITY_NAME_DBG = "KU_UE_DBG";
	private static final String KU_NAKA_ENTITY_NAME_DBG = "KU_NAKA_DBG";
	private static final String KU_SHITA_ENTITY_NAME_DBG = "KU_SHITA_DBG";
	protected static final String TAG = "KuListManager";

	public enum KuPosition {
		KU_UE,
		KU_NAKA,
		KU_SHITA,
	}
	
	private static ArrayList<String> mUeList;
	private static ArrayList<String> mNakaList;
	private static ArrayList<String> mShitaList;
	private static String mUe;
	private static String mNaka;
	private static String mShita;

	public static String getUeKu() {
		return mUe;
	}

	public static String getNakaKu() {
		return mNaka;
	}

	public static String getShitaKu() {
		return mShita;
	}

	private CloudBackendMessaging mCloudBackend;
	private Context mContext;


	KuListManager( Context context ) {
		mContext = context;
		initList();
		initArrayAdapters();
	}
	 
	public void initList() {
		mUeList = new ArrayList<String>();
		mNakaList = new ArrayList<String>();
		mShitaList = new ArrayList<String>();
		
		// 上の句
		mUeList.add( "たそがれの" );
		mUeList.add( "うるわしの" );
		mUeList.add( "夜の背に" );
		mUeList.add( "もだえてる" );
		mUeList.add( "いきりたつ" );
		
		// 中の句
		mNakaList.add( "背後に忍ぶ");	
		mNakaList.add( "頬を赤らめ");	
		mNakaList.add( "ベッドの中には");	
		mNakaList.add( "びしゃびしゃびしゃ");	
		
		// 下 の句
		mShitaList.add( "黒真珠");	
		mShitaList.add( "えのき茸");	
		mShitaList.add( "まつたけよ");	
		mShitaList.add( "二つの目");	
		mShitaList.add( "富士さんさん");	
	}

	private void initArrayAdapters() {
		mKuUeListAdapter = new ArrayAdapter<String>( mContext, R.layout.ku_list_textview, mUeList );
		mKuNakaListAdapter = new ArrayAdapter<String>( mContext, R.layout.ku_list_textview, mNakaList );
		mKuShitaListAdapter = new ArrayAdapter<String>( mContext, R.layout.ku_list_textview, mShitaList );
	}

	public ArrayList<String> getUeList() {
		return mUeList;
	}

	public ArrayList<String> getNakaList() {
		return mNakaList;
	}

	public ArrayList<String> getShitaList() {
		return mShitaList;
	}

	public int getNumOfList() {
		return NUM_ADAPTERS;
	}

	public ArrayAdapter<String> getUeAdapter() {
		return mKuUeListAdapter;
	}

	public ArrayAdapter<String> getShitaAdapter() {
		return mKuShitaListAdapter;
	}

	public ArrayAdapter<String> getNakaAdapter() {
		return mKuNakaListAdapter;
	}

	public static String getCombinedKu() {
		return mUe + " " + mNaka + " " + mShita;
	}

	public static void setSelectedTab(KuPosition selectedTab) {
		mSelectedKuPosition = selectedTab;
	}

	public static void setSelectedItem(int position) {
		mSelectedItem = position;
		
		switch( mSelectedKuPosition ) {
		case KU_UE:  
			mUe = mUeList.get(mSelectedItem);
			break;
		case KU_NAKA:
			mNaka = mNakaList.get(mSelectedItem);
			break;
		case KU_SHITA:
			mShita = mShitaList.get(mSelectedItem);
			break;
		}
	}

	public void initCloudBackend(CloudBackendMessaging cloudBackend) {
		// TODO Auto-generated method stub
		mCloudBackend = cloudBackend;
	}

	public void getKuListFromBE() {
		// BEから句のリストを取得する

		// create a response handler that will receive the query result or an error
	    CloudCallbackHandler<List<CloudEntity>> handler = new CloudCallbackHandler<List<CloudEntity>>() {

			@Override
		    public void onComplete(List<CloudEntity> results) {
		    	mResultList = results;
		        updateKuList();
		        Log.d(TAG, "onComplete called");
		    }
	
		    @Override
		    public void onError(IOException exception) {
		    	handleEndpointException(exception);
	    	}
	    };

	    // execute the query with the handler
	    mCloudBackend.listByKind(KU_UE_ENTITY_NAME_DBG, CloudEntity.PROP_CREATED_AT, Order.DESC, 50,
	        Scope.FUTURE_AND_PAST, handler);
	    
	    Log.d(TAG, "getKuListFromBE() done");
	}

	/**
	 * mResultList内容をViewに反映する。
	 * mResultListのCloudEntity取得は行わない。
	 * まだ未完成。
	 * 
	 */
	protected void updateKuList() {
		// TODO Auto-generated method stub
		for( CloudEntity entity : mResultList ) {
			Log.d(TAG, "Created at: " + entity.getCreatedAt());
		}
	}

	protected void handleEndpointException(IOException e) {
		Toast.makeText(mContext, e.toString(), Toast.LENGTH_LONG).show();
	}

	/**
	 * Google mobile backendへCloudEntitiyが登録出来るかの単純なテストファンクション。
	 * テスト目的のためだけの関数なので、いずれ消す。
	 * 
	 * @param str 登録する文字列
	 */
	public void debugRegisterKu(String str ) {
		CloudEntity newPost = new CloudEntity( KU_UE_ENTITY_NAME_DBG );
		newPost.put( "Ku", str );
		
		CloudCallbackHandler<CloudEntity> handler = new CloudCallbackHandler<CloudEntity>() {
		      @Override
		      public void onComplete(final CloudEntity result) {
		        mResultList.add(0, result);
		        updateKuList();
		        //etMessage.setText("");
		        //btSend.setEnabled(true);
		      }

		      @Override
		      public void onError(final IOException exception) {
		        handleEndpointException(exception);
		      }
		    };

		    // execute the insertion with the handler
		    mCloudBackend.insert(newPost, handler);
		    //btSend.setEnabled(false);;
	}
}
