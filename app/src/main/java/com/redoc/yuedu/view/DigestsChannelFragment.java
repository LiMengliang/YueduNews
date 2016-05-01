package com.redoc.yuedu.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.redoc.yuedu.R;
import com.redoc.yuedu.controller.DigestsAdapter;

public class DigestsChannelFragment extends ChannelFragment {

    private boolean fragmentCreated = false;
    // private TextView mTextView;
    private ListView mDigestsList;
    private DigestsAdapter digestsAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.     *
     * @return A new instance of fragment DigestFragment.
     * @param digestsAdapter
     */
    // TODO: Rename and change types and number of parameters
    public static DigestsChannelFragment newInstance(DigestsAdapter digestsAdapter) {
        DigestsChannelFragment fragment = new DigestsChannelFragment();
        fragment.digestsAdapter = digestsAdapter;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_digest_channel, container, false);
        if(mDigestsList != null) {
            return rootView;
        }
        // View digestListFootView = inflater.inflate(R.layout.view_digest_list_foot, container, false);
        mDigestsList = (ListView)rootView.findViewById(R.id.digestsList);
        TextView textView = new TextView(getContext());
        textView.setText(R.string.fetch_news_ing);
        mDigestsList.addFooterView(textView);
        mDigestsList.setAdapter(digestsAdapter);
        mDigestsList.setOnScrollListener(new DigestListOnScrollListener());
        // digestsAdapter.setAdaptedListView(mDigestsList);
        digestsAdapter.fetchLatest(getActivity());
        fragmentCreated = true;
        return rootView;
    }

    public boolean reachTopOfAllNewsOrFirstInitiated() {
        return mDigestsList == null || mDigestsList.getFirstVisiblePosition() == 0;

    }

    @Override
    public boolean shouldRefreshChannel() {
        return false;
    }

    // TODO: This listener could be a public class, if we have different digest channel fragment.
    class DigestListOnScrollListener implements AbsListView.OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                if (view.getLastVisiblePosition() == view.getCount() - 1) {
                    digestsAdapter.fetchMore(getActivity());
                }
                //                ((NewsDigestsAdapter)newsDigestsAdapter).setIsDigestListViewScrolling(false);
                //                int firstVisiblePosition = mDigestsList.getFirstVisiblePosition();
                //                int lastVisiblePosition = mDigestsList.getLastVisiblePosition();
                //                // TODO: need a digest view provider to be able to load image occording to different digest model type
                //                // Test comments for oom
                //                for(int i = 0; i < lastVisiblePosition - firstVisiblePosition; i++ ) {
                //                    View digestRootView = mDigestsList.getChildAt(i);
                //
                //                    INewsDigestView digestView = ((NewsDigestsAdapter) newsDigestsAdapter).getExistingDigestViewWithoutImages(digestRootView);
                //                    if(digestView != null) {
                //                        digestView.loadDigestImages();
                //                        ((NewsDigestsAdapter) newsDigestsAdapter).removeDigestViewWithImages(mDigestsList.getChildAt(i));
                //                    }
                //                }
                //            }
                //            else {
                //                ((NewsDigestsAdapter)newsDigestsAdapter).setIsDigestListViewScrolling(true);
                //            }
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        }
    }
}
