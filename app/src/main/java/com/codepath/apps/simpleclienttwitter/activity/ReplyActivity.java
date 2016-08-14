package com.codepath.apps.simpleclienttwitter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.codepath.apps.simpleclienttwitter.R;
import com.codepath.apps.simpleclienttwitter.fragment.ReplyTweetFragment;

public class ReplyActivity extends AppCompatActivity {

    private ReplyTweetFragment fragmentReplyTweet;
    private final int RESULT_OK = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
        Bundle bundle = new Bundle();

//        Tweet tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra("tweet"));
        if(savedInstanceState == null){
            fragmentReplyTweet = new ReplyTweetFragment();
            bundle.putParcelable("tweet",getIntent().getParcelableExtra("tweet"));
            fragmentReplyTweet.setArguments(bundle);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.flReplyContainer, fragmentReplyTweet);
            ft.commit();
        }

        if(fragmentReplyTweet != null){
            fragmentReplyTweet.setOnReplyToUser(new ReplyTweetFragment.OnReplyToUser(){
                @Override
                public void onReplyToTweet(String reply, String id) {
                    Intent data = new Intent();
                    data.putExtra("reply",reply);
                    data.putExtra("id",id);
                    setResult(RESULT_OK, data);
                    finish();
                }
            });
        }
    }
}
