package com.codepath.apps.simpleclienttwitter.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.codepath.apps.simpleclienttwitter.R;
import com.codepath.apps.simpleclienttwitter.constant.Config;
import com.codepath.apps.simpleclienttwitter.model.User;
import com.codepath.apps.simpleclienttwitter.utility.PatternEditableBuilder;
import com.codepath.apps.simpleclienttwitter.viewholder.ViewHolderMessage;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by bhaskarjaiswal on 8/14/16.
 */
public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<User> messageList;
    private Context context;
    private OnProfileImageClick onProfileImageClick;

    public interface OnProfileImageClick {
        public void onSendDirectMessage(View itemView, int position);
    }

    public MessageAdapter(Context context, List<User> followList) {
        this.context = context;
        this.messageList = followList;
    }

    public void setOnProfileImageClick(OnProfileImageClick onProfileImageClick) {
        this.onProfileImageClick = onProfileImageClick;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewholder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case Config.MESSAGE:
                View viewMessage = inflater.inflate(R.layout.friends_list_layout, parent, false);
                viewholder = new ViewHolderMessage(viewMessage, onProfileImageClick);
                break;
            default:
                View viewDefault = inflater.inflate(R.layout.friends_list_layout, parent, false);
                viewholder = new ViewHolderMessage(viewDefault, onProfileImageClick);
                break;
        }

        return viewholder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case Config.MESSAGE:
                configureViewHolderMessage((ViewHolderMessage) viewHolder, position);
                break;
        }
    }

    private void configureViewHolderMessage(final ViewHolderMessage viewUserProfile, int position) {
        User user = messageList.get(position);

        viewUserProfile.getTvUsername().setText(user.getName());
        patternEditableBuilder(viewUserProfile.getTvUsername());

        viewUserProfile.getTvScreenname().setText("@"+user.getScreenName());
        patternEditableBuilder(viewUserProfile.getTvScreenname());


        Glide.with(context).load(user.getProfileImageUrl()).asBitmap()
                .centerCrop().into(new BitmapImageViewTarget(viewUserProfile.getIvImage()) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(view.getContext().getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                viewUserProfile.getIvImage().setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        User user = messageList.get(position);
        return Config.MESSAGE;
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    protected static void patternEditableBuilder(TextView textView) {
        new PatternEditableBuilder().
                addPattern(Pattern.compile("\\@(\\w+)"), Color.parseColor("#0084b4"),
                        new PatternEditableBuilder.SpannableClickedListener() {
                            @Override
                            public void onSpanClicked(String text) {

                            }
                        }).into(textView);
    }
}
