package com.itmstm.erohaiku.fragment;

import com.itmstm.erohaiku.R;
import com.itmstm.erohaiku.R.id;
import com.itmstm.erohaiku.R.layout;
import com.itmstm.erohaiku.element.KuListManager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class KuContainerFragment extends Fragment {

	private KuListManager mKuListManager;
	private TextView mUeTextView;
	private TextView mNakaTextView;
	private TextView mShitaTextView;

	public KuContainerFragment() {
		// constructor
    }

	public static final String ARG_SECTION_NUMBER = "section_number";
	protected static final String TAG = KuContainerFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        View view = inflater.inflate(R.layout.ku_containter_fragment, container, false);
        
        // おおもとのview内にある各部分のviewを取得
        mUeTextView = (TextView) view.findViewById(R.id.ku_frag_ue_text_view);
        mNakaTextView = (TextView) view.findViewById(R.id.ku_frag_naka_text_view);
        mShitaTextView = (TextView) view.findViewById(R.id.ku_frag_shita_text_view);

        return view;
    }

	public void setKuListManager(KuListManager listmanager) {
		mKuListManager = listmanager;
	}
	
	public void setUeKu() {
	    mUeTextView.setText( mKuListManager.getUeKu() );
	}
	public void setNakaKu() {
	    mNakaTextView.setText( mKuListManager.getNakaKu() );
	}
	public void setShitaKu() {
	    mShitaTextView.setText( mKuListManager.getShitaKu() );
	}

	private void setUeRandomKu() {
	    mUeTextView.setText( mKuListManager.getUeRandomKu() );
	}
	private void setNakaRandomKu() {
	    mNakaTextView.setText( mKuListManager.getNakaRandomKu() );
	}
	private void setShitaRandomKu() {
	    mShitaTextView.setText( mKuListManager.getShitaRandomKu() );
	}
	
	
	/**
	 * リストアイテムが選択された際にコールバック経由で呼ばれる。
	 * 指定されたタブ（selectedTab)の句をアップデートする
	 * 
	 * @param selectedTab
	 */
	public void setKu( int selectedTab ) {
		
		// 呼ばれたときに選択されているタブの位置によって句を設定する
		switch( selectedTab) {
		case 1: // UE
			setUeKu();
			break;
		case 2: // Naka
			setNakaKu();
			break;
		case 3: // Shita
			setShitaKu();
			break;
		default:
		}
	}

	/**
	 * 上・中・下の句をランダムに選んで表示する
	 */
	public void randomPick() {
		setUeRandomKu();
		setNakaRandomKu();
		setShitaRandomKu();
	}

	/**
	 * 句コンテナの中身をクリアする
	 */
	public void clearKu() {
		mUeTextView.setText( null );
		mNakaTextView.setText( null );
		mShitaTextView.setText( null );
	}

}