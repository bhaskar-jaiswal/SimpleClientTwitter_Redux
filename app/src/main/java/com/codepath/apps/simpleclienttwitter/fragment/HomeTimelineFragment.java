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
 * Created by bhaskarjaiswal on 8/10/16.
 */
public class HomeTimelineFragment extends TweetsListFragment {

    /*private TweetDetailDialog tweetDetailDialog;
    private final int REQUEST_CODE = 20;
    private final int RESULT_OK = 200;*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*tweetDetailDialog = new TweetDetailDialog();
        tweetAdapter.setOnChooseOptionsActionListener(new TweetAdapter.OnItemClickListener() {
            @Override
            public void onTweetClick(View itemView, int position) {
                FragmentManager fragment = getActivity().getFragmentManager();
                Tweet tweet = tweetsList.get(position);
                Bundle bundle = new Bundle();
                bundle.putParcelable("tweet", Parcels.wrap(tweet));
                tweetDetailDialog.setArguments(bundle);
                tweetDetailDialog.show(fragment,"Tweet Dialog");
            }

            @Override
            public void onViewUserProfile(View itemView, int position) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                intent.putExtra("screen_name", tweetsList.get(position).getUser().getScreenName());
                startActivity(intent);
            }

            @Override
            public void onReplyTweet(View itemView, int position) {
                Intent intent = new Intent(getActivity(), ReplyActivity.class);
                intent.putExtra("tweet", Parcels.wrap(tweetsList.get(position)));
                startActivityForResult(intent, REQUEST_CODE);
            }

            @Override
            public void onRetweetTweet(View itemView, int position) {

            }

            @Override
            public void onFavoriteTweet(View itemView, int position) {

            }
        });

        tweetDetailDialog.setOnReplyTweet(new TweetDetailDialog.OnReply() {
            @Override
            public void onReplyTweet(String tweet, String id) {
                TwitterClient.params_timeline.remove(Config.MAX_ID);
                postUserStatus(tweet, false, id);
            }
        });*/
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String reply = data.getExtras().getString("reply");
            String id = data.getExtras().getString("id");
            Toast.makeText(getActivity(), reply + " " + id, Toast.LENGTH_SHORT).show();
            postUserStatus(reply, false, id);
        }
    }*/

    protected void populateTimeline() {
        if (isConnectionAvailable()) {
            client.getHomeTimeline(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                    Log.d("Populate Timeline", response.toString());
                    ArrayList<Tweet> list = Tweet.fromJSONArray(response);

                    addTweets(list);

                    if (accountUser == null && isConnectionAvailable()) {
                        retrieveUserDetails();
                    }
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
