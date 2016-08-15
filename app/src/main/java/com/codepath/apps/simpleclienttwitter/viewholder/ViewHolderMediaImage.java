package com.codepath.apps.simpleclienttwitter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.codepath.apps.simpleclienttwitter.R;
import com.codepath.apps.simpleclienttwitter.adapter.TweetAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bhaskarjaiswal on 8/14/16.
 */
public class ViewHolderMediaImage extends RecyclerView.ViewHolder {

    @BindView(R.id.ivMediaImage)
    ImageView ivMediaImage;

    public ViewHolderMediaImage(final View itemView, final TweetAdapter.OnItemClickListener itemClicked) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public ImageView getIvMediaImage() {
        return ivMediaImage;
    }
}
