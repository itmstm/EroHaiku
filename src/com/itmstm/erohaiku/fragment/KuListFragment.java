package com.itmstm.erohaiku.fragment;

import com.itmstm.erohaiku.R;
import com.itmstm.erohaiku.R.id;
import com.itmstm.erohaiku.R.layout;
import com.itmstm.erohaiku.element.KuListManager;
import com.itmstm.erohaiku.element.KuListManager.KuPosition;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class KuListFragment extends Fragment {
	private KuListManager mKuListManager;

	public static final String ARG_SECTION_NUMBER = "section_number";
	protected static final String TAG = KuListFragment.class.getSimpleName();

	private MainActivityCallback mCallback;
	private ListView mListView;

	public interface MainActivityCallback {
        public void onListItemSelected();
        public int getSelectedTab();
		public void setSelectedTab(int i);
    }

	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (MainActivityCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

	public KuListFragment() {
		// constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        // ListviewをXML layoutからinflate
        View view = inflater.inflate(R.layout.ku_list_fragment, container, false);
        mListView = (ListView) view.findViewById(R.id.ku_frag_list_view);

	    // 句のリストを表示
        showKuList( mCallback.getSelectedTab() );
        
        // listviewにListenerをセット
        mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) 
			{
				Log.d(TAG,  "Clicked: " + arg0 );
				Log.d(TAG,  "ID: " + id );
				Log.d(TAG,  "Position: " + position );
				
				KuListManager.setSelectedItem( position );
				mCallback.onListItemSelected();
				
				// 次の句（タブ）に移動する
				switch( mCallback.getSelectedTab() ) {
				case 1: // UE
					mCallback.setSelectedTab( 2 );
					break;
				case 2: // Naka
					mCallback.setSelectedTab( 3 );
					break;
				case 3: // Shita
					mCallback.setSelectedTab( 1 );
					break;
				default:
					//
				}
				showKuList( mCallback.getSelectedTab() );
			};
        });
        
        return view;
    }

	public void showKuList(int selectedTab) {
		
		Log.d(TAG, "showKuList(" + selectedTab + ")" );
		
		switch ( selectedTab ) {
		case 1: // Ue
			KuListManager.setSelectedTab( KuListManager.KuPosition.KU_UE );
			mListView.setAdapter( mKuListManager.getUeAdapter());
			
			// Log.d(TAG, "Strings: " + mKuListManager.getUeList().get(0) );
			
			break;
		case 2: // Naka
			KuListManager.setSelectedTab( KuListManager.KuPosition.KU_NAKA );
			mListView.setAdapter( mKuListManager.getNakaAdapter());
			break;
		case 3: // Shita
			KuListManager.setSelectedTab( KuListManager.KuPosition.KU_SHITA );
			mListView.setAdapter( mKuListManager.getShitaAdapter());
			break;
		default:
		}
	}

	public void setKuListManager(KuListManager listmanager) {
		mKuListManager = listmanager;
	}
}
