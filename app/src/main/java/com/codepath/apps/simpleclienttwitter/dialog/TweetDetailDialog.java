package com.codepath.apps.simpleclienttwitter.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.codepath.apps.simpleclienttwitter.R;
import com.codepath.apps.simpleclienttwitter.constant.Config;
import com.codepath.apps.simpleclienttwitter.model.Tweet;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bhaskarjaiswal on 8/7/16.
 */
public class TweetDetailDialog extends DialogFragment {

    @BindView(R.id.ivImageView)
    ImageView ivImageView;

    @BindView(R.id.tvUsername)
    TextView tvUsername;

    @BindView(R.id.tvScreenname)
    TextView tvScreenname;

    @BindView(R.id.tvBody)
    TextView tvBody;

    @BindView(R.id.etEditReply)
    EditText etEditReply;

    @BindView(R.id.tvCharacters)
    TextView tvCharacters;

    @BindView(R.id.tvReplyTo)
    TextView tvReplyTo;

    @BindView(R.id.btnReply)
    Button btnReply;

    @BindView(R.id.ivTweetImage)
    ImageView ivTweetImage;

    private OnReply onReply;

    public TweetDetailDialog() {
    }

    public interface OnReply{
        void onReplyTweet(String tweet, String id);
    }

    public void setOnReplyTweet(OnReply onReply){
        this.onReply = onReply;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_detailed, container);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        /*if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }*/
        dialog.getWindow().setLayout(500, 650);

        return dialog;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Tweet tweet = (Tweet) Parcels.unwrap(getArguments().getParcelable("tweet"));
        tvUsername.setText(tweet.getUser().getName());
        tvScreenname.setText("@"+tweet.getUser().getScreenName());
        tvBody.setText(tweet.getBody());
        tvReplyTo.setText(Config.STRING_REPLY_TO+tweet.getUser().getName());

        Glide.with(getActivity().getApplicationContext()).load(tweet.getUser().getProfileImageUrl()).asBitmap()
                .centerCrop().into(new BitmapImageViewTarget(ivImageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(view.getContext().getResources(), resource);
                circularBitmapDrawable.setCornerRadius(10);
                ivImageView.setImageDrawable(circularBitmapDrawable);
            }
        });

        Glide.with(getActivity().getApplicationContext()).load(tweet.getTweetimage()).asBitmap()
                .override(750,400).centerCrop().into(new BitmapImageViewTarget(ivTweetImage) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(view.getContext().getResources(), resource);
                circularBitmapDrawable.setCornerRadius(15);
                ivTweetImage.setImageDrawable(circularBitmapDrawable);
            }
        });

        tvReplyTo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                etEditReply.setText("@"+tweet.getUser().getScreenName()+" ");
                int textLength = etEditReply.getText().length();
                tvReplyTo.setVisibility(View.INVISIBLE);
                tvCharacters.setVisibility(View.VISIBLE);
                btnReply.setVisibility(View.VISIBLE);


                etEditReply.setText(tvScreenname.getText()+" ");
//                etEditReply.setFocusable(true);
                etEditReply.setSelection(textLength);
                tvCharacters.setText((Config.CHARACTER_COUNT-textLength)+"");
            }
        });

        etEditReply.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                ComposeTweetsDialog.characterCount(tvCharacters,etEditReply,Config.STRING_REPLY,btnReply);
            }
        });

        btnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReply.onReplyTweet(etEditReply.getText().toString(), Long.toString(tweet.getUid()));
                etEditReply.setText("");
                tvReplyTo.setVisibility(View.VISIBLE);
                tvCharacters.setVisibility(View.INVISIBLE);
                btnReply.setVisibility(View.INVISIBLE);
            }
        });
    }
}
