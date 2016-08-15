package com.codepath.apps.simpleclienttwitter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.simpleclienttwitter.R;
import com.codepath.apps.simpleclienttwitter.adapter.MessageAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bhaskarjaiswal on 8/14/16.
 */
public class ViewHolderMessage extends RecyclerView.ViewHolder{

    private MessageAdapter.OnProfileImageClick itemClicked;

    @BindView(R.id.ivProfileImage)
    ImageView ivImage;

    @BindView(R.id.tvUsername)
    TextView tvUsername;

    @BindView(R.id.tvScreenname)
    TextView tvScreenname;

    public ViewHolderMessage(final View itemView, final MessageAdapter.OnProfileImageClick itemClicked) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        this.itemClicked = itemClicked;

        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClicked != null) {
                    itemClicked.onSendDirectMessage(itemView, getLayoutPosition());
                }
            }
        });
    }

    public ImageView getIvImage() {
        return ivImage;
    }

    public TextView getTvUsername() {
        return tvUsername;
    }

    public TextView getTvScreenname() {
        return tvScreenname;
    }
}
