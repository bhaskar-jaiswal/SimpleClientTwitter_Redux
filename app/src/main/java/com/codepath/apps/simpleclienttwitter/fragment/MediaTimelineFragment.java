package com.codepath.apps.simpleclienttwitter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.codepath.apps.simpleclienttwitter.constant.Config;
import com.codepath.apps.simpleclienttwitter.model.Tweet;
import com.codepath.apps.simpleclienttwitter.twitterapi.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by bhaskarjaiswal on 8/14/16.
 */
public class MediaTimelineFragment extends TweetsListFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static MediaTimelineFragment newInstance(String screen_name){
        MediaTimelineFragment mediaTimelineFragment = new MediaTimelineFragment();
        Bundle bundle = new Bundle();
        bundle.putString("screen_name", screen_name);
        mediaTimelineFragment.setArguments(bundle);
        return mediaTimelineFragment;
    }

    protected void populateTimeline() {
        String screen_name = getArguments().getString("screen_name");
        if (isConnectionAvailable()) {
            client.getUserTimeline(screen_name,new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    Log.d("MediaImages Timeline", response.toString());
                    ArrayList<Tweet> list = Tweet.getMediaImagefromJSONArray(response);

                    addTweets(list);

                    if (accountUser == null && isConnectionAvailable()) {
                        retrieveUserDetails();
                    }
                    progressBar_center.setVisibility(View.INVISIBLE);
                    swipeContainer.setRefreshing(false);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d("getMediaImages", errorResponse + "");
                }
            });
        }
    }

    @Override
    protected void cleanupOnSwipe(){
        tweetsList.clear();
        tweetAdapter.notifyDataSetChanged();
        TwitterClient.params_timeline.remove(Config.MAX_ID);
        populateTimeline();
    }
}
