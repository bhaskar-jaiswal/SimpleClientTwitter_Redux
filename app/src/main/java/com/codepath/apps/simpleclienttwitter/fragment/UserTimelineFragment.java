package com.codepath.apps.simpleclienttwitter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.apps.simpleclienttwitter.constant.Config;
import com.codepath.apps.simpleclienttwitter.model.Tweet;
import com.codepath.apps.simpleclienttwitter.twitterapi.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by bhaskarjaiswal on 8/11/16.
 */
public class UserTimelineFragment extends TweetsListFragment {

    private ArrayList<Tweet> mentionsList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        populateTimeline();
    }

    public static UserTimelineFragment newInstance(String screen_name){
        UserTimelineFragment userTimelineFragment = new UserTimelineFragment();
        Bundle bundle = new Bundle();
        bundle.putString("screen_name", screen_name);
        userTimelineFragment.setArguments(bundle);
        return userTimelineFragment;
    }

    protected void populateTimeline() {
        String screen_name = getArguments().getString("screen_name");
        if(isConnectionAvailable()) {
            client.getUserTimeline(screen_name, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                    Log.d("Success Debug", response.toString());
                    ArrayList<Tweet> list = Tweet.fromJSONArray(response);

                    addTweets(list);
                    swipeContainer.setRefreshing(false);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d("getUserTimeline", errorResponse + "");
                }
            });
        }
    }
    protected void cleanupOnSwipe(){
        tweetsList.clear();
        tweetAdapter.notifyDataSetChanged();
        TwitterClient.params_timeline.remove(Config.MAX_ID);
        populateTimeline();
    }
}
