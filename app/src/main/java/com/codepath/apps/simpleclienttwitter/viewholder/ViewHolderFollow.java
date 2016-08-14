package com.codepath.apps.simpleclienttwitter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.simpleclienttwitter.R;
import com.codepath.apps.simpleclienttwitter.adapter.FollowAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bhaskarjaiswal on 8/13/16.
 */
public class ViewHolderFollow extends RecyclerView.ViewHolder {

    @BindView(R.id.ivProfileImage)
    ImageView ivImage;

    @BindView(R.id.ivFollow)
    ImageView ivFollow;

    @BindView(R.id.tvUsername)
    TextView tvUsername;

    @BindView(R.id.tvScreenname)
    TextView tvScreenname;

    @BindView(R.id.tvTagline)
    TextView tvTagline;

    private FollowAdapter.OnProfileImageClick onProfileImageClick;


    public ViewHolderFollow(final View itemView, final FollowAdapter.OnProfileImageClick onProfileImageClick) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        this.onProfileImageClick = onProfileImageClick;

        ivImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (onProfileImageClick != null){
                    onProfileImageClick.onProfileClick(itemView, getLayoutPosition());
                }
            }
        });

        ivFollow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (onProfileImageClick != null){
                    onProfileImageClick.onProfileFollowUnfollow(itemView, getLayoutPosition());
                }
            }
        });

    }

    public ImageView getIvImage() {
        return ivImage;
    }

    public ImageView getIvFollow() {
        return ivFollow;
    }

    public TextView getTvUsername() {
        return tvUsername;
    }

    public TextView getTvScreenname() {
        return tvScreenname;
    }

    public TextView getTvTagline() {
        return tvTagline;
    }
}
