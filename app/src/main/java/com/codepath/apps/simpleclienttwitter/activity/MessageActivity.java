package com.codepath.apps.simpleclienttwitter.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.codepath.apps.simpleclienttwitter.R;
import com.codepath.apps.simpleclienttwitter.fragment.FriendsFragment;
import com.codepath.apps.simpleclienttwitter.fragment.TweetsListFragment;
import com.codepath.apps.simpleclienttwitter.model.Tweet;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageActivity extends AppCompatActivity {

    @BindView(R.id.ivUpArrow)
    ImageView ivUpArrow;

    private FriendsFragment friendsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);

        getSupportActionBar().hide();

        Tweet tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra("tweet"));

        if(savedInstanceState == null){
//            FriendsFragment friendsFragment = new FriendsFragment().newInstance(TweetsListFragment.accountUser.getScreenName(), tweet);
            FriendsFragment friendsFragment = new FriendsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("screen_name", TweetsListFragment.accountUser.getScreenName());
            bundle.putParcelable("tweet",getIntent().getParcelableExtra("tweet"));
            friendsFragment.setArguments(bundle);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flMessageContainer, friendsFragment);
            ft.commit();
        }

//        User user = friendsFragment.getUserSelected();
//        getSupportFragmentManager().beginTransaction().remove(friendsFragment).commit();

//        Log.d("User and tweet",user.getScreenName()+" "+tweet.body);

        ivUpArrow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
//                getSupportFragmentManager().beginTransaction().remove(followFragment).commit();
            }
        });
    }
}
