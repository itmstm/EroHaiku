package com.itmstm.erohaiku.element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.google.cloud.backend.android.CloudBackendMessaging;
import com.google.cloud.backend.android.CloudCallbackHandler;
import com.google.cloud.backend.android.CloudEntity;
import com.google.cloud.backend.android.CloudQuery;
import com.google.cloud.backend.android.CloudQuery.Order;
import com.google.cloud.backend.android.CloudQuery.Scope;
import com.itmstm.erohaiku.R;
import com.itmstm.erohaiku.R.layout;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

/**
 * @author masatomo.ito
 *
 */
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
	protected static final String TAG = KuListManager.class.getSimpleName();

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

	public String getUeKu() {
		return mUe;
	}

	public String getNakaKu() {
		return mNaka;
	}

	public String getShitaKu() {
		return mShita;
	}

	private CloudBackendMessaging mCloudBackend;
	private Context mContext;
	private Random mRandom;


	public KuListManager( Context context ) {
		mContext = context;
		initList();
		initArrayAdapters();
		
		// RNGの初期化 (epochをseedとする)
		mRandom = new Random( System.currentTimeMillis() );
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
		mUeList.add( "特大の" );
		mUeList.add( "濡れている" );
		mUeList.add( "脈を打つ" );
		mUeList.add( "目覚めたら" );
		mUeList.add( "隆々と" );
		mUeList.add( "立ち上がれ" );
		mUeList.add( "股間には" );
		mUeList.add( "本能で" );
		
		// 中の句
		mNakaList.add( "背後に忍ぶ");	
		mNakaList.add( "頬を赤らめ");	
		mNakaList.add( "ベッドの中には");	
		mNakaList.add( "触り放題");	
		mNakaList.add( "びしゃびしゃびしゃ");	
		mNakaList.add( "一本杉は");	
		mNakaList.add( "俺の分身");	
		mNakaList.add( "波打ち際で");	
		mNakaList.add( "ミトコンドリア");	
		mNakaList.add( "勃つに勃たない");	
		mNakaList.add( "キラリと光る");	
		mNakaList.add( "ついに開いた");	
		mNakaList.add( "ほのかに薫る");	
		mNakaList.add( "野生のままの");	
		mNakaList.add( "セクシー放題");	
		
		// 下 の句
		mShitaList.add( "黒真珠");	
		mShitaList.add( "えのき茸");	
		mShitaList.add( "まつたけよ");	
		mShitaList.add( "二つの目");	
		mShitaList.add( "富士さんさん");	
		mShitaList.add( "暴発だ");	
		mShitaList.add( "二日酔い");	
		mShitaList.add( "でくのぼう");	
		mShitaList.add( "感触よ");	
		mShitaList.add( "我が息子");	
		mShitaList.add( "煩悩よ");	
		mShitaList.add( "やり放題");	
		mShitaList.add( "未開の地");	
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

	/**
	 * Google mobile backendから句のリストを取得する 
	 */
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
	    /*
	    mCloudBackend.listByKind(KU_UE_ENTITY_NAME_DBG, CloudEntity.PROP_CREATED_AT, Order.DESC, 50,
	        Scope.FUTURE_AND_PAST, handler);
        */
	    CloudQuery cq = new CloudQuery( KU_UE_ENTITY_NAME_DBG );
	    mCloudBackend.list(cq, handler);
	    
	    Log.d(TAG, "getKuListFromBE() done");
	}

	/**
	 * mResultList内容をViewに反映する。
	 * mResultListのCloudEntity取得は行わない。
	 * まだ未完成。
	 * 
	 */
	protected void updateKuList() {
		Log.d(TAG, "updateKuList() called" );
		for( CloudEntity entity : mResultList ) {
			Log.d(TAG, "Created at: " + entity.getCreatedAt());
		}
		Log.d(TAG, "updateKuList() done" );
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

	/**
	 * 指定された値より小さいランダムな値を返す
	 * @param num
	 * @return ランダムな値（＜size)
	 */
	private int getRamdomNum(int num) {
		return mRandom.nextInt( num );
	}

	public String getUeRandomKu() {
		return mUeList.get(getRamdomNum( mUeList.size() ));
	}
	public String getNakaRandomKu() {
		return mNakaList.get(getRamdomNum( mNakaList.size() ));
	}
	public String getShitaRandomKu() {
		return mShitaList.get(getRamdomNum( mShitaList.size() ));
	}

}
