package com.itmstm.erohaiku;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class KuListFragment extends Fragment {

	private KuListManager mKuListManager;
	private TextView mTextView;
	private int mSelectedTab;

	public KuListFragment() {
		// constructor
    }

	public static final String ARG_SECTION_NUMBER = "section_number";
	protected static final String TAG = "KuListFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        int selectedTab = getArguments().getInt(ARG_SECTION_NUMBER);
        getArguments().get(KuListManager.ARG_KU_LIST_MANAGER);
        
        View view = inflater.inflate(R.layout.ku_list_fragment, container, false);
        
        // おおもとのview内にある各部分のviewを取得
        mTextView = (TextView) view.findViewById(R.id.ku_frag_text_view);
        ListView listview = (ListView) view.findViewById(R.id.ku_frag_list_view);
        
        // listviewにListenerをセット
        listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) 
			{
				Log.d(TAG,  "Clicked: " + arg0 );
				Log.d(TAG,  "ID: " + id );
				Log.d(TAG,  "Position: " + position );
				
				KuListManager.setSelectedItem( position );
				
		        mTextView.setText( KuListManager.getCombinedKu() );
			};
        });
        
        // Set text
		mTextView.setText( "Selceted tab = " + mSelectedTab );
        
        setKuItems(listview, selectedTab);
        
        return view;
    }

	private void setKuItems(ListView lv, int selectedTab) {
		mSelectedTab = selectedTab;
		
		switch ( selectedTab ) {
		case 1: // Ue
			KuListManager.setSelectedTab( KuListManager.KuPosition.KU_UE );
			lv.setAdapter( mKuListManager.getUeAdapter());
			break;
		case 2: // Naka
			KuListManager.setSelectedTab( KuListManager.KuPosition.KU_NAKA );
			lv.setAdapter( mKuListManager.getNakaAdapter());
			break;
		case 3: // Shita
			KuListManager.setSelectedTab( KuListManager.KuPosition.KU_SHITA );
			lv.setAdapter( mKuListManager.getShitaAdapter());
			break;
		default:
		}
	}

	public void setKuListManager(KuListManager listmanager) {
		mKuListManager = listmanager;
		
	}
}
