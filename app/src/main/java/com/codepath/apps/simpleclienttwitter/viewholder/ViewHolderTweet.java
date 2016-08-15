package com.codepath.apps.simpleclienttwitter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.simpleclienttwitter.R;
import com.codepath.apps.simpleclienttwitter.adapter.TweetAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bhaskarjaiswal on 8/4/16.
 */
public class ViewHolderTweet extends RecyclerView.ViewHolder {

    @BindView(R.id.ivProfileImage)
    ImageView ivImage;

    @BindView(R.id.tvUsername)
    TextView tvUsername;

    @BindView(R.id.tvScreenname)
    TextView tvScreenname;

    @BindView(R.id.tvHours)
    TextView tvHours;

    @BindView(R.id.tvBody)
    TextView tvBody;

    @BindView(R.id.ivTweetImage)
    ImageView ivTweetImage;

    @BindView(R.id.ivReply)
    ImageView ivReply;

    @BindView(R.id.ivReweet)
    ImageView ivReweet;

    @BindView(R.id.tvReweetCount)
    TextView tvReweetCount;

    @BindView(R.id.ivFavorite)
    ImageView ivFavorite;

    @BindView(R.id.tvFavoriteCount)
    TextView tvFavoriteCount;

    @BindView(R.id.ivMessage)
    ImageView ivMessage;

    private TweetAdapter.OnItemClickListener itemClicked;

    public ViewHolderTweet(final View itemView, final TweetAdapter.OnItemClickListener itemClicked){
        super(itemView);
        ButterKnife.bind(this, itemView);

        this.itemClicked = itemClicked;

        ivImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (itemClicked != null){
                    itemClicked.onViewUserProfile(itemView, getLayoutPosition());
                }
            }
        });

        tvBody.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (itemClicked != null){
                    itemClicked.onTweetClick(itemView, getLayoutPosition());
                }
            }
        });

        ivReply.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (itemClicked != null){
                    itemClicked.onReplyTweet(itemView, getLayoutPosition());
                }
            }
        });

        ivReweet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (itemClicked != null){
                    itemClicked.onRetweetTweet(itemView, getLayoutPosition());
                }
            }
        });

        ivFavorite.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (itemClicked != null){
                    itemClicked.onFavoriteTweet(itemView, getLayoutPosition());
                }
            }
        });

        ivMessage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (itemClicked != null){
                    itemClicked.onDirectMessage(itemView, getLayoutPosition());
                }
            }
        });
    }

    public ImageView getIvImage() {
        return ivImage;
    }

    public void setIvImage(ImageView ivImage) {
        this.ivImage = ivImage;
    }

    public TextView getTvUsername() {
        return tvUsername;
    }

    public void setTvUsername(TextView tvUsername) {
        this.tvUsername = tvUsername;
    }

    public TextView getTvBody() {
        return tvBody;
    }

    public void setTvBody(TextView tvBody) {
        this.tvBody = tvBody;
    }

    public TextView getTvScreenname() {
        return tvScreenname;
    }

    public TextView getTvHours() {
        return tvHours;
    }

    public ImageView getIvTweetImage() {
        return ivTweetImage;
    }

    public ImageView getIvReweet() {
        return ivReweet;
    }

    public ImageView getIvFavorite() {
        return ivFavorite;
    }

    public TextView getTvReweetCount() {
        return tvReweetCount;
    }

    public TextView getTvFavoriteCount() {
        return tvFavoriteCount;
    }
}
