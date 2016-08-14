package com.codepath.apps.simpleclienttwitter.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.codepath.apps.simpleclienttwitter.R;
import com.codepath.apps.simpleclienttwitter.constant.Config;
import com.codepath.apps.simpleclienttwitter.model.Tweet;
import com.codepath.apps.simpleclienttwitter.viewholder.ViewHolderTweet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by bjaiswal on 8/3/2016.
 */
public class TweetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Tweet> tweetsList;
    private Context context;
    private OnItemClickListener onItemClick;

    private SimpleDateFormat sf = new SimpleDateFormat(Config.TWITTER_TIMESTAMP, Locale.ENGLISH);
    private StringBuffer strBuf = new StringBuffer();
    private Date date = new Date();
    long secs = -1, mins = -1;

    public interface OnItemClickListener {
        void onTweetClick(View itemView, int position);

        void onViewUserProfile(View itemView, int position);

        void onReplyTweet(View itemView, int position);

        void onRetweetTweet(View itemView, int position);

        void onFavoriteTweet(View itemView, int position);
    }

    public void setOnChooseOptionsActionListener(OnItemClickListener itemClicked) {
        onItemClick = itemClicked;
    }

    public TweetAdapter(Context context, List<Tweet> tweetsList) {
        this.context = context;
        this.tweetsList = tweetsList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewholder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case Config.TWEET:
                View viewTweet = inflater.inflate(R.layout.item_tweet, parent, false);
                viewholder = new ViewHolderTweet(viewTweet, onItemClick);
                break;
            default:
                View viewDefault = inflater.inflate(R.layout.item_tweet, parent, false);
                viewholder = new ViewHolderTweet(viewDefault, onItemClick);
                break;
        }
        return viewholder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case Config.TWEET:
                configureViewHolderTweet((ViewHolderTweet) viewHolder, position);
                break;
        }
    }

    private void configureViewHolderTweet(final ViewHolderTweet viewTweet, int position) {
        Tweet tweet = (Tweet) tweetsList.get(position);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(80, 80);
        if (tweet != null) {
            viewTweet.getTvUsername().setText(tweet.getUser().getName());
            viewTweet.getTvScreenname().setText("@" + tweet.getUser().getScreenName());
            viewTweet.getTvBody().setText(tweet.getBody());
            tweet.setTimeline(getTwitterDate(tweet.getCreatedAt()));
            viewTweet.getTvHours().setText(tweet.getTimeline());
            viewTweet.getTvReweetCount().setText(tweet.getRetweetCount() == "0" ? "" : tweet.getRetweetCount());
            viewTweet.getTvFavoriteCount().setText(tweet.getFavoriteCount() == "0" ? "" : tweet.getFavoriteCount());


            if (tweet.getRetweeted().equalsIgnoreCase("true")) {
//                viewTweet.getIvReweet().setImageResource(R.drawable.ic_retweet_green);
                Glide.with(context).load(R.drawable.ic_action_av_repeat).into(viewTweet.getIvReweet());
//                viewTweet.getIvReweet().setLayoutParams(layoutParams);
            } else if (tweet.getRetweeted().equalsIgnoreCase("false")) {
//                viewTweet.getIvReweet().setImageResource(R.drawable.ic_retweet);
                Glide.with(context).load(R.drawable.ic_retweet).into(viewTweet.getIvReweet());
//                viewTweet.getIvReweet().setLayoutParams(layoutParams);
            }

            if (tweet.getFavorited().equalsIgnoreCase("true")) {
//                viewTweet.getIvReweet().setImageResource(R.drawable.ic_retweet_green);
                Glide.with(context).load(R.drawable.ic_favorite_red).into(viewTweet.getIvFavorite());
//                viewTweet.getIvReweet().setLayoutParams(layoutParams);
            } else if (tweet.getFavorited().equalsIgnoreCase("false")) {
//                viewTweet.getIvReweet().setImageResource(R.drawable.ic_retweet);
                Glide.with(context).load(R.drawable.ic_favorite).into(viewTweet.getIvFavorite());
//                viewTweet.getIvReweet().setLayoutParams(layoutParams);
            }
//            viewTweet.getTvSource().setText(tweet.getSource());

            Glide.with(context).load(tweet.getUser().getProfileImageUrl()).asBitmap()
                    .centerCrop().into(new BitmapImageViewTarget(viewTweet.getIvImage()) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(view.getContext().getResources(), resource);
                    circularBitmapDrawable.setCornerRadius(15);
                    viewTweet.getIvImage().setImageDrawable(circularBitmapDrawable);
                }
            });

            if (tweet.getTweetimage() != null && tweet.getTweetimage().length() != 0) {

                Glide.with(context).load(tweet.getTweetimage()).asBitmap()
                        .override(800, 400).centerCrop().into(new BitmapImageViewTarget(viewTweet.getIvTweetImage()) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(view.getContext().getResources(), resource);
                        circularBitmapDrawable.setCornerRadius(15);
                        Log.d("Tweetimage", viewTweet.getIvTweetImage().toString());
                        viewTweet.getIvTweetImage().setImageDrawable(circularBitmapDrawable);
                    }
                });
            } else {
                viewTweet.getIvTweetImage().setImageResource(0);
            }
        }
    }

    @Override
    public int getItemCount() {
        return tweetsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Tweet tweet = tweetsList.get(position);
        return Config.TWEET;
    }

    String getTwitterDate(String sDate) {

        sf.setLenient(true);
        strBuf.delete(0, strBuf.length());

        try {
            secs = (long) ((date.getTime() - sf.parse(sDate).getTime()) / 1000);
            if (secs < 60) {
                strBuf.append(secs + "s");
                return strBuf.toString();
            }

            mins = secs / 60;
            if (mins < 60) {
                strBuf.append(mins + "m");
                return strBuf.toString();
            }
            strBuf.append(mins / 60 + "h");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return strBuf.toString();
    }
}
