package com.codepath.apps.simpleclienttwitter.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.codepath.apps.simpleclienttwitter.R;
import com.codepath.apps.simpleclienttwitter.constant.Config;
import com.codepath.apps.simpleclienttwitter.dialog.ComposeTweetsDialog;
import com.codepath.apps.simpleclienttwitter.model.Tweet;
import com.codepath.apps.simpleclienttwitter.model.User;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bhaskarjaiswal on 8/12/16.
 */
public class ReplyTweetFragment extends Fragment {

    private Tweet tweet;
    private User user;
    private OnReplyToUser onReplyToUser;

    @BindView(R.id.ivCancel)
    ImageView ivCancel;

    @BindView(R.id.ivProfileImage)
    ImageView ivProfileImage;

    @BindView(R.id.rlayout)
    RelativeLayout rlayout;

    @BindView(R.id.tvSendingTo)
    TextView tvSendingTo;

    @BindView(R.id.ivArrowDownwards)
    ImageView ivArrowDownwards;

    @BindView(R.id.tvInReplyTo)
    TextView tvInReplyTo;

    @BindView(R.id.etTextArea)
    EditText etTextArea;

    @BindView(R.id.tvCharacters)
    TextView tvCharacters;

    @BindView(R.id.btnReply)
    Button btnReply;

    public interface OnReplyToUser{
        void onReplyToTweet(String reply, String id);
    }

    public void setOnReplyToUser(OnReplyToUser onReplyToUser){
        this.onReplyToUser = onReplyToUser;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String directMessage = getArguments().getString("DirectMessage");
        if(directMessage.equalsIgnoreCase("true")){
            user = (User) Parcels.unwrap(getArguments().getParcelable("user"));
        }
        tweet = (Tweet) Parcels.unwrap(getArguments().getParcelable("tweet"));

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tweet_reply,container,false);
        ButterKnife.bind(this,view);

        ivCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(ReplyTweetFragment.this).commit();
                getActivity().finish();

            }
        });

        Glide.with(getContext()).load(TweetsListFragment.accountUser.getProfileImageUrl()).asBitmap()
                .centerCrop().into(new BitmapImageViewTarget(ivProfileImage) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(view.getContext().getResources(), resource);
                circularBitmapDrawable.setCornerRadius(15);
                ivProfileImage.setImageDrawable(circularBitmapDrawable);
            }
        });

        if(user != null){
            rlayout.setVisibility(View.VISIBLE);
            tvSendingTo.setVisibility(View.VISIBLE);
            tvSendingTo.setText(user.getName());
            ivArrowDownwards.setVisibility(View.GONE);
            tvInReplyTo.setVisibility(View.GONE);
            etTextArea.setText("@"+user.getScreenName()+": "+tweet.getBody()+" ");
        }else{
            tvInReplyTo.setText(Config.STRING_IN_REPLY_TO+" "+tweet.getUser().getName());
            etTextArea.setText("@"+tweet.getUser().getScreenName()+" ");
        }
        etTextArea.setSelection(etTextArea.length());

        etTextArea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                ComposeTweetsDialog.characterCount(tvCharacters, etTextArea, Config.STRING_REPLY, btnReply);
            }
        });

        ComposeTweetsDialog.characterCount(tvCharacters,etTextArea, Config.STRING_REPLY, btnReply);

        btnReply.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(user == null){
                    onReplyToUser.onReplyToTweet(etTextArea.getText().toString(), Long.toString(tweet.getUid()));
                }else if(user != null){
                    Log.d("info",etTextArea.getText().toString()+" "+user.getScreenName());
//                    onDirectMessages.onDirectMessages(etTextArea.getText().toString(), user.getScreenName());

                    new FriendsFragment().postDirectMessage(etTextArea.getText().toString(), user.getScreenName());

                    getActivity().finish();
                    user=null;
                }
                getActivity().getSupportFragmentManager().beginTransaction().remove(ReplyTweetFragment.this).commit();
            }
        });

        return view;
    }
}
