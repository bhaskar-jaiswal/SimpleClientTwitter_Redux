package com.codepath.apps.simpleclienttwitter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.apps.simpleclienttwitter.constant.Config;
import com.codepath.apps.simpleclienttwitter.model.User;
import com.codepath.apps.simpleclienttwitter.twitterapi.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by bhaskarjaiswal on 8/13/16.
 */
public class FollowersFragment extends FollowFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (TwitterClient.params_follow.has(Config.STRING_NEXT_CURSOR)){
            TwitterClient.params_follow.remove(Config.STRING_NEXT_CURSOR);
        }
        populateTimeline();
    }

    public static FollowersFragment newInstance(String screen_name){
        FollowersFragment followersFragment = new FollowersFragment();
        Bundle bundle = new Bundle();
        bundle.putString("screen_name", screen_name);
        followersFragment.setArguments(bundle);
        return followersFragment;
    }

    protected void populateTimeline() {
        String screen_name = getArguments().getString("screen_name");
        if(isConnectionAvailable()) {
            if(client != null){
                client.getFollowersList(screen_name, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                    Log.d("getFollowersList", response.toString());
                        ArrayList<User> list = User.getFollowList(response);

                        addUsers(list);
                        swipeContainer.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.d("getFollowersList", errorResponse + "");
                    }
                });
            }
        }
    }

    protected void cleanupOnSwipe(){
        followList.clear();
        followAdapter.notifyDataSetChanged();
        TwitterClient.params_follow.remove(Config.STRING_NEXT_CURSOR);
        populateTimeline();
    }
}
