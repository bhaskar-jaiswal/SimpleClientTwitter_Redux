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
import com.codepath.apps.simpleclienttwitter.model.User;
import com.codepath.apps.simpleclienttwitter.viewholder.ViewHolderFollow;

import java.util.List;

/**
 * Created by bhaskarjaiswal on 8/13/16.
 */
public class FollowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<User> followList;
    private Context context;
    private OnProfileImageClick onProfileImageClick;

    public interface OnProfileImageClick {
        public void onProfileClick(View itemView, int position);
        public void onProfileFollowUnfollow(View itemView, int position);
    }

    public FollowAdapter(Context context, List<User> followList) {
        this.context = context;
        this.followList = followList;
    }

    public void setOnProfileImageClick(OnProfileImageClick onProfileImageClick) {
        this.onProfileImageClick = onProfileImageClick;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewholder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case Config.FOLLOW:
                View viewFollow = inflater.inflate(R.layout.follow_fragment_list, parent, false);
                viewholder = new ViewHolderFollow(viewFollow, onProfileImageClick);
                break;
            default:
                View viewDefault = inflater.inflate(R.layout.follow_fragment_list, parent, false);
                viewholder = new ViewHolderFollow(viewDefault, onProfileImageClick);
                break;
        }

        return viewholder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case Config.FOLLOW:
                configureViewHolderTweet((ViewHolderFollow) viewHolder, position);
                break;
        }
    }

    private void configureViewHolderTweet(final ViewHolderFollow viewUserProfile, int position) {
        User user = followList.get(position);

        viewUserProfile.getTvUsername().setText(user.getName());
        viewUserProfile.getTvScreenname().setText("@"+user.getScreenName());
        viewUserProfile.getTvTagline().setText(user.getTagline());

        if(user.getFollowing().equalsIgnoreCase(Config.TRUE)){
            Log.d("Updating ic_following","following=true");
            Glide.with(context).load(R.mipmap.ic_following).into(viewUserProfile.getIvFollow());
        }else if (user.getFollowing().equalsIgnoreCase(Config.FALSE)){
            Log.d("Updating ic_follow","following=false");
            Glide.with(context).load(R.mipmap.ic_follow).into(viewUserProfile.getIvFollow());
        }

        Glide.with(context).load(user.getProfileImageUrl()).asBitmap()
                .centerCrop().into(new BitmapImageViewTarget(viewUserProfile.getIvImage()) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(view.getContext().getResources(), resource);
                circularBitmapDrawable.setCornerRadius(15);
                viewUserProfile.getIvImage().setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        User user = followList.get(position);
        return Config.FOLLOW;
    }

    @Override
    public int getItemCount() {
        return followList.size();
    }
}
