package com.codepath.apps.simpleclienttwitter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.codepath.apps.simpleclienttwitter.constant.Config;
import com.codepath.apps.simpleclienttwitter.model.Tweet;
import com.codepath.apps.simpleclienttwitter.twitterapi.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by bhaskarjaiswal on 8/14/16.
 */
public class SearchFragment extends TweetsListFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static SearchFragment newInstance(String search_string) {
        SearchFragment searchFragment = new SearchFragment();
        Bundle bundle = new Bundle();
        bundle.putString("search_string", search_string);
        searchFragment.setArguments(bundle);
        Log.d("Retruning srchFragment", search_string);
        return searchFragment;
    }

    protected void populateTimeline() {
        String search_string = getArguments().getString("search_string");
        if (isConnectionAvailable()) {
            client.getSearchResult(search_string, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.d("Search Result", response.toString());
                    try {
                        ArrayList<Tweet> list = Tweet.fromJSONArray(response.getJSONArray("statuses"));
                        addTweets(list);
                        progressBar_center.setVisibility(View.INVISIBLE);
                        swipeContainer.setRefreshing(false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d("getSearchResult", errorResponse + "");
                }
            });
        }
    }

    protected void cleanupOnSwipe() {
        tweetsList.clear();
        tweetAdapter.notifyDataSetChanged();
        TwitterClient.params_timeline.remove(Config.MAX_ID);
        populateTimeline();
    }
}
