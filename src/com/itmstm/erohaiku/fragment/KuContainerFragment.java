package com.itmstm.erohaiku.fragment;

import com.itmstm.erohaiku.R;
import com.itmstm.erohaiku.R.id;
import com.itmstm.erohaiku.R.layout;
import com.itmstm.erohaiku.element.KuListManager;
import com.itmstm.erohaiku.fragment.KuListFragment.MainActivityCallback;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class KuContainerFragment extends Fragment {

	private KuListManager mKuListManager;
	private TextView mUeTextView;
	private TextView mNakaTextView;
	private TextView mShitaTextView;
	private MainActivityCallback mCallback;

	public interface MainActivityCallback {
		public void setSelectedTab(int i);
		public void setShowKuList(int i);
    }

	public KuContainerFragment() {
		// constructor
    }

	public static final String ARG_SECTION_NUMBER = "section_number";
	protected static final String TAG = KuContainerFragment.class.getSimpleName();

    @Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
		// Main activityへのcallbackを設定
		try {
            mCallback = (MainActivityCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }	
    }

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        View view = inflater.inflate(R.layout.ku_containter_fragment, container, false);
        
        // おおもとのview内にある各部分のviewを取得
        mUeTextView = (TextView) view.findViewById(R.id.ku_frag_ue_text_view);
        mNakaTextView = (TextView) view.findViewById(R.id.ku_frag_naka_text_view);
        mShitaTextView = (TextView) view.findViewById(R.id.ku_frag_shita_text_view);

        // Textviewをクリック可能に設定
        mUeTextView.setClickable(true);
        mNakaTextView.setClickable(true);
        mShitaTextView.setClickable(true);
        
        
        // TextViewにクリックリスナーを設定
        mUeTextView.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				// タブの選択
				mCallback.setSelectedTab( 1 );

				// 句のアップデートをMainActivity経由でListFragmentに伝える
				mCallback.setShowKuList(1);
			}
        });
        
        mNakaTextView.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				// タブの選択
				mCallback.setSelectedTab( 2 );

				// 句のアップデートをMainActivity経由でListFragmentに伝える
				mCallback.setShowKuList(2);
			}
        });
        
        mShitaTextView.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				// タブの選択
				mCallback.setSelectedTab( 3 );
				
				// 句のアップデートをMainActivity経由でListFragmentに伝える
				mCallback.setShowKuList(3);
			}
        });

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
