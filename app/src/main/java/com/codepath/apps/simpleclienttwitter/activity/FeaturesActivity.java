package com.codepath.apps.simpleclienttwitter.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.simpleclienttwitter.R;
import com.codepath.apps.simpleclienttwitter.constant.Config;
import com.codepath.apps.simpleclienttwitter.fragment.FollowFragment;
import com.codepath.apps.simpleclienttwitter.fragment.FollowersFragment;
import com.codepath.apps.simpleclienttwitter.fragment.FollowingFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeaturesActivity extends AppCompatActivity {

    private FollowFragment followFragment;
    private FragmentTransaction ft;

    @BindView(R.id.tvFollow)
    TextView tvFollow;

    @BindView(R.id.ivUpArrow)
    ImageView ivUpArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features);
        ButterKnife.bind(this);

        getSupportActionBar().hide();

        String screen_name = getIntent().getStringExtra(Config.STRING_SCREEN_NAME);
        String fragment = getIntent().getStringExtra(Config.STRING_FRAGMENTS);

        switch(fragment){
            case Config.FOLLOWING:
                followFragment = FollowingFragment.newInstance(screen_name);
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.flFeaturesContainer, followFragment);
                ft.commit();
                tvFollow.setText(Config.FOLLOWING);
                break;
            case Config.FOLLOWERS:
                followFragment = FollowersFragment.newInstance(screen_name);
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.flFeaturesContainer, followFragment);
                ft.commit();
                tvFollow.setText(Config.FOLLOWERS);
                break;
        }
        ivUpArrow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
//                getSupportFragmentManager().beginTransaction().remove(followFragment).commit();
            }
        });
    }
}
