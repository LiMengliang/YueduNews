package com.redoc.yuedu.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.redoc.yuedu.R;
import com.redoc.yuedu.YueduApplication;
import com.redoc.yuedu.bean.Digest;
import com.redoc.yuedu.controller.DigestsAdapter;
import com.redoc.yuedu.news.bean.Consts;
import com.redoc.yuedu.news.bean.NewsDigest;
import com.redoc.yuedu.news.controller.NewsDigestsAdapter;
import com.redoc.yuedu.utilities.network.LoadImageUtilities;
import com.redoc.yuedu.utilities.network.NetworkUtilities;
import com.redoc.yuedu.utilities.network.VolleyUtilities;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DigestsChannelFragment extends ChannelFragment {

    private ListView mDigestsList;
    private DigestsAdapter digestsAdapter;
    private int lastFirstVisiblePosition = 0;
    private boolean canFetchLatestNews = true;
    private final static String lastVisiblePosition = "VisiblePosition";
    private int firstPositionWithImage = Integer.MAX_VALUE;
    private int lastPositionWithImages = Integer.MIN_VALUE;

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
        if(savedInstanceState != null) {
            lastFirstVisiblePosition = savedInstanceState.getInt(lastVisiblePosition);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_digest_channel, container, false);
        mDigestsList = (ListView)rootView.findViewById(R.id.digestsList);
        View listFooter = inflater.inflate(R.layout.widegt_fetch_more_digest_notification, null, false);
        mDigestsList.addFooterView(listFooter);
        mDigestsList.setAdapter(digestsAdapter);
        mDigestsList.setOnScrollListener(new DigestListOnScrollListener());
        mDigestsList.setOnItemClickListener(new NewsDigestClickListener(digestsAdapter, getActivity()));
        // mDigestsList.setOnScrollListener(new PauseOnScrollListener(LoadImageUtilities.imageLoader, true, true));
        if(shouldRefreshChannel()) {
            digestsAdapter.fetchLatest();
        }
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(lastVisiblePosition, mDigestsList.getFirstVisiblePosition());
    }

    @Override
    public boolean shouldRefreshChannel() {
        return lastFirstVisiblePosition == 0;
    }

    @Override
    public void refresh() {
        // digestsAdapter.fetchLatest();
        if(canFetchLatestNews) {
            mDigestsList.setSelection(0);
            mDigestsList.smoothScrollToPosition(0);
            digestsAdapter.fetchLatest();
            // Toast toast = Toast.makeText(getContext(), "获取最新新闻", Toast.LENGTH_SHORT);
            // toast.show();
        }
    }

    // TODO: This listener could be a public class, if we have different digest channel fragment.
    class DigestListOnScrollListener implements AbsListView.OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                canFetchLatestNews = true;
                if (view.getLastVisiblePosition() == view.getCount() - 1) {
                    digestsAdapter.fetchMore();
                }
                int firstVisiblePosition = mDigestsList.getFirstVisiblePosition();
                int lastVisiblePosition = mDigestsList.getLastVisiblePosition();
                for(int i = 0; i <= lastVisiblePosition - firstVisiblePosition; i++ ) {
                    View digestRootView = mDigestsList.getChildAt(i);
                    DelayLoadImageControl delayLoadImageControl = (DelayLoadImageControl)digestRootView.getTag();
                    if(delayLoadImageControl != null && delayLoadImageControl.isImageCleared()) {
                        if(firstPositionWithImage > i + firstVisiblePosition || lastPositionWithImages < i + firstVisiblePosition) {
                            delayLoadImageControl.loadImages((Digest) digestsAdapter.getItem(i + firstVisiblePosition));
                        }
                    }
                }
                firstPositionWithImage = firstVisiblePosition;
                lastPositionWithImages = lastVisiblePosition;
            }
            else {
                canFetchLatestNews = false;
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        }
    }

    static class NewsDigestClickListener implements OnItemClickListener {
        private DigestsAdapter digestsAdapter;
        private Context context;

        public NewsDigestClickListener(DigestsAdapter newsDigestsAdapter, Context context) {
            digestsAdapter = newsDigestsAdapter;
            this.context = context;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            NewsDigest newsDigest = (NewsDigest)digestsAdapter.getItem(position);
            if(newsDigest.getPhotoSetId() != null) {
                Intent intent = new Intent(context, ImageViewerActivity.class);
                // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                String photoSetId = newsDigest.getPhotoSetId();
                String path1 = photoSetId.substring("00AO".length(), photoSetId.indexOf('|'));
                String path2 = photoSetId.substring(photoSetId.indexOf('|') + 1);
                String photosetUrl = String.format("http://c.3g.163.com/photo/api/set/%s/%s.json",
                        path1, path2);
                intent.putExtra(Consts.PHOTO_SET_URL, photosetUrl);
                context.startActivity(intent);
            }
        }
    }
}
