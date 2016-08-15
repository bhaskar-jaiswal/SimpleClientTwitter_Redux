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
 * Created by bhaskarjaiswal on 8/10/16.
 */
public class HomeTimelineFragment extends TweetsListFragment {

    /*private TweetDetailDialog tweetDetailDialog;
    private final int REQUEST_CODE = 20;
    private final int RESULT_OK = 200;*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void populateTimeline() {
        if (isConnectionAvailable()) {
            client.getHomeTimeline(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    Log.d("Populate Timeline", response.toString());
                    ArrayList<Tweet> list = Tweet.fromJSONArray(response);

                    addTweets(list);

                    if (accountUser == null && isConnectionAvailable()) {
                        retrieveUserDetails();
                    }
                    progressBar_center.setVisibility(View.INVISIBLE);
                    swipeContainer.setRefreshing(false);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d("getHomeTimeline", errorResponse + "");
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

    /*protected void retrieveUserDetails() {
        client.getUserDetails(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                Log.d("Account User",response.toString());
                user = new User(response);
//                Log.d("account user",accountUser.getScreenname()+" "+accountUser.getUsername());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("retrieveUserDetails", errorResponse.toString());
            }
        });
    }*/
}
