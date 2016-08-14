package com.codepath.apps.simpleclienttwitter.fragment;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.apps.simpleclienttwitter.R;
import com.codepath.apps.simpleclienttwitter.activity.ProfileActivity;
import com.codepath.apps.simpleclienttwitter.activity.ReplyActivity;
import com.codepath.apps.simpleclienttwitter.adapter.TweetAdapter;
import com.codepath.apps.simpleclienttwitter.constant.Config;
import com.codepath.apps.simpleclienttwitter.dialog.ComposeTweetsDialog;
import com.codepath.apps.simpleclienttwitter.dialog.TweetDetailDialog;
import com.codepath.apps.simpleclienttwitter.listener.EndlessRecyclerViewScrollListener;
import com.codepath.apps.simpleclienttwitter.model.Tweet;
import com.codepath.apps.simpleclienttwitter.model.User;
import com.codepath.apps.simpleclienttwitter.twitterapi.TwitterApplication;
import com.codepath.apps.simpleclienttwitter.twitterapi.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

/**
 * Created by bhaskarjaiswal on 8/9/16.
 */
public abstract class TweetsListFragment extends Fragment {

    protected TwitterClient client;
    protected LinearLayoutManager layoutManager;
    protected ArrayList<Tweet> tweetsList;
    protected TweetAdapter tweetAdapter;
    public static User accountUser;
    private ConnectivityManager connectivityManager;
    private NetworkInfo activeNetworkInfo;
    private ComposeTweetsDialog composeTweetsDialog;
    private TweetDetailDialog tweetDetailDialog;
    private final int REQUEST_CODE = 20;
    private final int RESULT_OK = 200;

    @BindView(R.id.rvTweets)
    RecyclerView rvTweets;

    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    protected abstract void populateTimeline();

    protected abstract void cleanupOnSwipe();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();

        tweetsList = new ArrayList<Tweet>();
        tweetAdapter = new TweetAdapter(getActivity(), tweetsList);

        connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        tweetDetailDialog = new TweetDetailDialog();
        tweetAdapter.setOnChooseOptionsActionListener(new TweetAdapter.OnItemClickListener() {
            @Override
            public void onTweetClick(View itemView, int position) {
                FragmentManager fragment = getActivity().getFragmentManager();
                Tweet tweet = tweetsList.get(position);
                Bundle bundle = new Bundle();
                bundle.putParcelable("tweet", Parcels.wrap(tweet));
                tweetDetailDialog.setArguments(bundle);
                tweetDetailDialog.show(fragment, "Tweet Dialog");
            }

            @Override
            public void onViewUserProfile(View itemView, int position) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                intent.putExtra("user", Parcels.wrap(tweetsList.get(position).getUser()));
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
                Tweet tweet = tweetsList.get(position);
//                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(60,60);
                ImageView ivRetweet = (ImageView) itemView.findViewById(R.id.ivReweet);
                String id = Long.toString(tweet.getUid());

                if (tweet.getRetweeted().equalsIgnoreCase("false")) {
//                    ivRetweet.setImageResource(R.drawable.ic_retweet_green);
                    Glide.with(getContext()).load(R.drawable.ic_action_av_repeat).into(ivRetweet);
                    tweet.setRetweeted("true");
                    postRetweet(tweet, position, id);
                } else {
//                    ivRetweet.setImageResource(R.drawable.ic_retweet);
                    Glide.with(getContext()).load(R.drawable.ic_retweet).into(ivRetweet);
                    tweet.setRetweeted("false");
                    postUntweet(tweet, position, id);
                }
//                ivRetweet.setLayoutParams(layoutParams);
            }

            @Override
            public void onFavoriteTweet(View itemView, int position) {
                Tweet tweet = tweetsList.get(position);
                ImageView ivFavorite = (ImageView) itemView.findViewById(R.id.ivFavorite);
                String id = Long.toString(tweet.getUid());

                if (tweet.getFavorited().equalsIgnoreCase("false")) {
//                    ivRetweet.setImageResource(R.drawable.ic_retweet_green);
                    Glide.with(getContext()).load(R.drawable.ic_favorite_red).into(ivFavorite);
                    tweet.setFavorited("true");
                    postFavoriteTweet(tweet, position, id);
                } else {
//                    ivRetweet.setImageResource(R.drawable.ic_retweet);
                    Glide.with(getContext()).load(R.drawable.ic_favorite).into(ivFavorite);
                    tweet.setFavorited("false");
                    postUnFavoriteTweet(tweet, position, id);
                }
            }
        });

        tweetDetailDialog.setOnReplyTweet(new TweetDetailDialog.OnReply() {
            @Override
            public void onReplyTweet(String tweet, String id) {
                TwitterClient.params_timeline.remove(Config.MAX_ID);
                postUserStatus(tweet, false, id);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String reply = data.getExtras().getString("reply");
            String id = data.getExtras().getString("id");
            Toast.makeText(getActivity(), reply + " " + id, Toast.LENGTH_SHORT).show();
            postUserStatus(reply, false, id);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
        ButterKnife.bind(this, v);

        layoutManager = new LinearLayoutManager(getActivity());
        rvTweets.setLayoutManager(layoutManager);

        rvTweets.setAdapter(tweetAdapter);

        if (isConnectionAvailable()) {
            populateTimeline();
            retrieveUserDetails();
        } else {
            tweetsList.clear();
            tweetsList.addAll(Tweet.getAllTweets());
            tweetAdapter.notifyDataSetChanged();
        }

//        ivRetweet
//        tvRetweetCount.setText(tweetsList);

        rvTweets.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            public void onLoadMore(int page, int totalItemsCount) {
                if (isConnectionAvailable()) {
                    populateTimeline();
                } else {
                    Toast.makeText(getActivity(), Config.NETWORK_UNAVAILABLE, Toast.LENGTH_SHORT).show();
                }
            }
        });

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (isConnectionAvailable()) {

                    // Delete database and update tables if network is available
                    swipeContainer.setRefreshing(true);
                    cleanupOnSwipe();
                } else {
                    Toast.makeText(getActivity(), Config.NETWORK_UNAVAILABLE, Toast.LENGTH_SHORT).show();
                    swipeContainer.setRefreshing(false);
                }
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        composeTweetsDialog = new ComposeTweetsDialog();
        composeTweetsDialog.setCallSubmitTweet(new ComposeTweetsDialog.OnTweetSubmission() {
            @Override
            public void onTweetsubmitted(String tweet, String id) {
                TwitterClient.params_timeline.remove(Config.MAX_ID);
                postUserStatus(tweet, true, id);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentManager fragment = getActivity().getSupportFragmentManager();
                Bundle bundle = new Bundle();
                if (accountUser != null && accountUser.getName() != null) {
                    bundle.putParcelable("accountUser", Parcels.wrap(accountUser));
                    composeTweetsDialog.setArguments(bundle);
                    composeTweetsDialog.show(fragment, "compose_tweets");
                } else {
                    Toast.makeText(getActivity(), Config.USER_INFORMATION_UNAVAILABLE, Toast.LENGTH_LONG).show();
                }
            }
        });

        return v;
    }

    private Boolean isNetworkAvailable() {
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isConnectionAvailable() {
        return isNetworkAvailable() && isOnline();
    }

    protected void addTweets(List<Tweet> list) {
        tweetsList.addAll(list);
        tweetAdapter.notifyDataSetChanged();
    }

    protected void retrieveUserDetails() {
        client.getUserDetails(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                Log.d("Account User",response.toString());
                accountUser = User.fromJSON(response);

//                Log.d("user",accountUser.getScreenname()+" "+accountUser.getUsername());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("retrieveUserDetails", errorResponse.toString());
            }
        });
    }

    protected void postUserStatus(String status, Boolean statusOrReply, String id) {

        if (isConnectionAvailable()) {
            client.postUserStatus(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    tweetsList.clear();
                    tweetAdapter.notifyDataSetChanged();
                    populateTimeline();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d("postUserStatus", errorResponse.toString());
                }
            }, status, statusOrReply, id);
        } else {
            Toast.makeText(getActivity(), Config.NETWORK_UNAVAILABLE, Toast.LENGTH_SHORT).show();
        }
    }

    private void postRetweet(final Tweet tweet, final int position, final String id) {

        if (isConnectionAvailable()) {
            client.postRetweet(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        Log.d("T response", response.toString());
                        tweet.setRetweetCount(response.getString("retweet_count"));
                        Log.d("Retweet count", response.getString("retweet_count") + " " + tweet.getUid());
                        tweetAdapter.notifyItemChanged(position);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d("postRetweet", errorResponse.toString());
                }
            }, id);
        } else {
            Toast.makeText(getActivity(), Config.NETWORK_UNAVAILABLE, Toast.LENGTH_SHORT).show();
        }
    }

    private void postUntweet(final Tweet tweet, final int position, final String id) {

        if (isConnectionAvailable()) {
            client.postUntweet(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        Log.d("UT response", response.toString());
                        tweet.setRetweetCount(response.getString("retweet_count"));
                        Log.d("Untweet count", response.getString("retweet_count") + " " + tweet.getUid());
                        tweetAdapter.notifyItemChanged(position);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d("postUntweet", errorResponse.toString());
                }
            }, id);
        } else {
            Toast.makeText(getActivity(), Config.NETWORK_UNAVAILABLE, Toast.LENGTH_SHORT).show();
        }
    }

    private void postFavoriteTweet(final Tweet tweet, final int position, final String id) {

        if (isConnectionAvailable()) {
            client.postFavorite(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        Log.d("F response", response.toString());
                        tweet.setFavoriteCount(response.getString("favorite_count"));
                        Log.d("Favorite count", response.getString("favorite_count") + " " + tweet.getUid());
                        tweetAdapter.notifyItemChanged(position);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d("postFavorite", errorResponse.toString());
                }
            }, id);
        } else {
            Toast.makeText(getActivity(), Config.NETWORK_UNAVAILABLE, Toast.LENGTH_SHORT).show();
        }
    }

    private void postUnFavoriteTweet(final Tweet tweet, final int position, final String id) {

        if (isConnectionAvailable()) {
            client.postUnFavorite(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        Log.d("UF response", response.toString());
                        tweet.setFavoriteCount(response.getString("favorite_count"));
                        Log.d("UnFavorite count", response.getString("favorite_count") + " " + tweet.getUid());
                        tweetAdapter.notifyItemChanged(position);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d("postUnFavoriteTweet", errorResponse.toString());
                }
            }, id);
        } else {
            Toast.makeText(getActivity(), Config.NETWORK_UNAVAILABLE, Toast.LENGTH_SHORT).show();
        }
    }

    protected void postFollowUnfollow(final User user) {
        if (isConnectionAvailable()) {
            if (user.getFollowing().equalsIgnoreCase(Config.FALSE)) {
                client.postFriendshipCreate(new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
//                            user.setFollowing(response.get("following") + "");
                            user.setFollowing(Config.TRUE);
                            Log.d("Create friendship ", response.get("following") + "");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.d("postFriendshipCreate", errorResponse.toString());
                    }
                }, user.getScreenName());
            } else if (user.getFollowing().equalsIgnoreCase(Config.TRUE)) {
                client.postFriendshipDestroy(new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
//                            user.setFollowing(response.get("following") + "");
                            user.setFollowing(Config.FALSE);
                            Log.d("Destroy friendship", response.get("following") + "");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.d("postFriendshipDestroy", errorResponse.toString());
                    }
                }, user.getScreenName());
            }
        } else {
            Toast.makeText(getActivity(), Config.NETWORK_UNAVAILABLE, Toast.LENGTH_SHORT).show();
        }
    }
}
