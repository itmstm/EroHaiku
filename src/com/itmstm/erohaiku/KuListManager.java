package com.itmstm.erohaiku;

import java.util.ArrayList;

import android.content.Context;
import android.widget.ArrayAdapter;

public class KuListManager {
	private static final int NUM_ADAPTERS = 3; // 上、中、下の３つの句
	private static int mSelectedItem;
	private static KuPosition mSelectedKuPosition;
	private static ArrayAdapter<String> mKuUeListAdapter;
	private static ArrayAdapter<String> mKuNakaListAdapter;
	private static ArrayAdapter<String> mKuShitaListAdapter;
	
	public static final String ARG_KU_LIST_MANAGER = "ku_list_manager";
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

	KuListManager( Context context ) {
		initList();
		initArrayAdapters( context );
	}
	 
	private void initList() {
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

	private void initArrayAdapters( Context context ) {
		mKuUeListAdapter = new ArrayAdapter<String>( context, R.layout.ku_list_textview, mUeList );
		mKuNakaListAdapter = new ArrayAdapter<String>( context, R.layout.ku_list_textview, mNakaList );
		mKuShitaListAdapter = new ArrayAdapter<String>( context, R.layout.ku_list_textview, mShitaList );
		
		//mKuUeListAdapter.addAll(mUeList);
		//mKuNakaListAdapter.addAll(mNakaList);
		//mKuShitaListAdapter.addAll(mShitaList);
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
}
