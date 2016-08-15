package com.codepath.apps.simpleclienttwitter.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.codepath.apps.simpleclienttwitter.activity.ReplyActivity;
import com.codepath.apps.simpleclienttwitter.adapter.MessageAdapter;
import com.codepath.apps.simpleclienttwitter.constant.Config;
import com.codepath.apps.simpleclienttwitter.model.Tweet;
import com.codepath.apps.simpleclienttwitter.model.User;
import com.codepath.apps.simpleclienttwitter.twitterapi.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by bhaskarjaiswal on 8/13/16.
 */
public class FriendsFragment extends MessageFragment {

    /*public static FriendsFragment newInstance(String screen_name, Tweet tweet){
        FriendsFragment friendsFragment = new FriendsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("screen_name", screen_name);
        friendsFragment.setArguments(bundle);

        messageAdapter.setOnProfileImageClick(new MessageAdapter.OnProfileImageClick() {
            @Override
            public void onSendDirectMessage(View itemView, int position) {
                User user = messageList.get(position);
            }
        });

        return friendsFragment;
    }*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Tweet tweet = (Tweet) Parcels.unwrap(getArguments().getParcelable("tweet"));

        messageAdapter.setOnProfileImageClick(new MessageAdapter.OnProfileImageClick() {
            @Override
            public void onSendDirectMessage(View itemView, int position) {
                User user = messageList.get(position);
                Log.d("info",tweet.body+" "+user.getScreenName());
                Intent intent = new Intent(getActivity(), ReplyActivity.class);
                intent.putExtra("tweet", Parcels.wrap(tweet));
                intent.putExtra("user", Parcels.wrap(user));
                startActivity(intent);
                getActivity().finish();
            }
        });

    }


    protected void populateTimeline() {
        String screen_name = getArguments().getString("screen_name");
        if(isConnectionAvailable()) {
            if(client != null){
                client.getFriendsList(screen_name, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                    Log.d("getFriendsList", response.toString());
                        ArrayList<User> list = User.getFollowList(response);
                        progressBar_center.setVisibility(View.INVISIBLE);
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
        messageList.clear();
        messageAdapter.notifyDataSetChanged();
        TwitterClient.params_follow.remove(Config.STRING_NEXT_CURSOR);
        populateTimeline();
    }

    public void postDirectMessage(final String text, final String screen_name) {

        if (isConnectionAvailable()) {
            client.postDirectMessages(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d("postUnFavoriteTweet", errorResponse.toString());
                }
            }, screen_name, text);
        } else {
//            Toast.makeText(getActivity(), Config.NETWORK_UNAVAILABLE, Toast.LENGTH_SHORT).show();
        }
    }
}
