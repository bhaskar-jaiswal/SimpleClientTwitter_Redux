package com.codepath.apps.simpleclienttwitter.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.codepath.apps.simpleclienttwitter.R;
import com.codepath.apps.simpleclienttwitter.constant.Config;
import com.codepath.apps.simpleclienttwitter.fragment.UserTimelineFragment;
import com.codepath.apps.simpleclienttwitter.model.User;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.ivBackgroundImage)
    ImageView ivBackgroundImage;

    @BindView(R.id.ivProfileImage)
    ImageView ivProfileImage;

    @BindView(R.id.ivUpArrow)
    ImageView ivUpArrow;

    @BindView(R.id.ivSearchGlass)
    ImageView ivSearchGlass;

    @BindView(R.id.ivTweetNotification)
    ImageView ivTweetNotification;

    @BindView(R.id.ivFollow)
    ImageView ivFollow;

    @BindView(R.id.tvScreenname)
    TextView tvScreenname;

    @BindView(R.id.tvUsername)
    TextView tvUsername;

    @BindView(R.id.tvTagline)
    TextView tvTagline;

    @BindView(R.id.tvFollowers)
    TextView tvFollowers;

    @BindView(R.id.tvFollowing)
    TextView tvFollowing;

    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.bind(this);

        getSupportActionBar().hide();

        User user = (User) Parcels.unwrap(getIntent().getParcelableExtra("user"));
        final String screen_name = user.getScreenName();
        if(savedInstanceState == null){
            UserTimelineFragment fragmentUserTimeline = UserTimelineFragment.newInstance(screen_name);
            ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, fragmentUserTimeline);
            ft.commit();
        }
//        Log.d("Account User Null?",(user == null)+"");
        if (user != null){

            getSupportActionBar().setTitle("@"+ user.getName());

            tvUsername.setText(user.getName());
            tvScreenname.setText("@"+user.getScreenName());
            tvFollowing.setText(Html.fromHtml("<b>" + user.getFriendsCount() + "</b>")+" Following");
            tvFollowers.setText(Html.fromHtml("<b>" + user.getFollowersCount()+ "</b>")+" Followers");
            tvTagline.setText(user.getTagline());

            Glide.with(getApplicationContext()).load(user.getProfileImageUrl()).asBitmap()
                    .centerCrop().into(new BitmapImageViewTarget(ivProfileImage) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(view.getContext().getResources(), resource);
                    circularBitmapDrawable.setCornerRadius(10);
                    ivProfileImage.setImageDrawable(circularBitmapDrawable);
                }
            });

//            Log.d("bk ground Image",(user==null)+" "+user.getBackground_image());

            if(user.getBackground_image() != null && user.getBackground_image().length() !=0){
                Glide.with(getApplicationContext()).load(user.getBackground_image()).asBitmap()
                        .centerCrop().into(new BitmapImageViewTarget(ivBackgroundImage) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(view.getContext().getResources(), resource);
                        circularBitmapDrawable.setCornerRadius(10);
                        ivBackgroundImage.setImageDrawable(circularBitmapDrawable);
                    }
                });
            }else{
                ivBackgroundImage.setImageResource(0);
            }

            if(user.getFollowing().equalsIgnoreCase(Config.TRUE)){
                Glide.with(getApplicationContext()).load(R.mipmap.ic_following).into(ivFollow);
            }else if (user.getFollowing().equalsIgnoreCase(Config.FALSE)){
                Glide.with(getApplicationContext()).load(R.mipmap.ic_follow).into(ivFollow);
            }

            tvFollowers.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ProfileActivity.this,FeaturesActivity.class);
                    intent.putExtra(Config.STRING_FRAGMENTS,Config.FOLLOWERS);
                    intent.putExtra(Config.STRING_SCREEN_NAME,screen_name);
                    startActivity(intent);
                }
            });

            tvFollowing.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ProfileActivity.this,FeaturesActivity.class);
                    intent.putExtra(Config.STRING_FRAGMENTS,Config.FOLLOWING);
                    intent.putExtra(Config.STRING_SCREEN_NAME,screen_name);
                    startActivity(intent);
                }
            });


        }
        ivUpArrow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
