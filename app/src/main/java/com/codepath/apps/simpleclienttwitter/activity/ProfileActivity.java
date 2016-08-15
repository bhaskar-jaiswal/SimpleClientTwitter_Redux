package com.codepath.apps.simpleclienttwitter.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.astuetz.PagerSlidingTabStrip;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.codepath.apps.simpleclienttwitter.R;
import com.codepath.apps.simpleclienttwitter.constant.Config;
import com.codepath.apps.simpleclienttwitter.fragment.FavoritesTimelineFragment;
import com.codepath.apps.simpleclienttwitter.fragment.MediaTimelineFragment;
import com.codepath.apps.simpleclienttwitter.fragment.UserTimelineFragment;
import com.codepath.apps.simpleclienttwitter.model.User;
import com.codepath.apps.simpleclienttwitter.utility.PatternEditableBuilder;

import org.parceler.Parcels;

import java.util.regex.Pattern;

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
    private UserTimelineFragment fragmentUserTimeline;

    @BindView(R.id.viewpagerProfile)
    ViewPager vpPagerProfile;

    @BindView(R.id.tabsProfile)
    PagerSlidingTabStrip tabStripProfile;

    private UserTweetPagerAdapter tweetPagerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.bind(this);

        getSupportActionBar().hide();

        final User user = (User) Parcels.unwrap(getIntent().getParcelableExtra("user"));
        final String screen_name = user.getScreenName();

        tweetPagerAdapter = new UserTweetPagerAdapter(getSupportFragmentManager(), screen_name);

        vpPagerProfile.setAdapter(tweetPagerAdapter);
        vpPagerProfile.setPageTransformer(true,new RotateUpTransformer());
        vpPagerProfile.setOffscreenPageLimit(3);
        tabStripProfile.setViewPager(vpPagerProfile);

        if (savedInstanceState == null) {
            fragmentUserTimeline = UserTimelineFragment.newInstance(screen_name);
            ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.rlContainer, fragmentUserTimeline);
//            ft.commit();
        }
//        Log.d("Account User Null?",(user == null)+"");

        if (user != null) {

            getSupportActionBar().setTitle("@" + user.getName());

            tvUsername.setText(user.getName());
            patternEditableBuilder(tvUsername);

            tvScreenname.setText("@" + user.getScreenName());
            patternEditableBuilder(tvScreenname);

            tvFollowing.setText(Html.fromHtml("<b>" + user.getFriendsCount()+"" + "</b>") + " Following");
            tvFollowers.setText(Html.fromHtml("<b>" + user.getFollowersCount()+"" + "</b>") + " Followers");
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

            if (user.getBackground_image() != null && user.getBackground_image().length() != 0) {
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
            } else {
                ivBackgroundImage.setImageResource(0);
            }

            if(user.getFollowing().equalsIgnoreCase(Config.TRUE)) {
                Glide.with(getApplicationContext()).load(R.mipmap.ic_following1).into(ivFollow);
                ivTweetNotification.setVisibility(View.VISIBLE);
                if (user.getNotifications().equalsIgnoreCase(Config.TRUE)) {
                    Glide.with(getApplicationContext()).load(R.drawable.ic_follow_tweet).into(ivTweetNotification);
                } else if (user.getNotifications().equalsIgnoreCase(Config.FALSE)) {
                    Glide.with(getApplicationContext()).load(R.drawable.ic_unfollow_tweet).into(ivTweetNotification);
                }
            }else{
                Glide.with(getApplicationContext()).load(R.mipmap.ic_follow1).into(ivFollow);
                ivTweetNotification.setVisibility(View.INVISIBLE);
            }

            /*if (user.getNotifications().equalsIgnoreCase(Config.TRUE)) {
                Glide.with(getApplicationContext()).load(R.drawable.ic_follow_tweet).into(ivTweetNotification);
            } else if (user.getNotifications().equalsIgnoreCase(Config.FALSE)) {
                Glide.with(getApplicationContext()).load(R.drawable.ic_unfollow_tweet).into(ivTweetNotification);
            }

            if (user.getFollowing().equalsIgnoreCase(Config.TRUE)) {
                Glide.with(getApplicationContext()).load(R.mipmap.ic_following1).into(ivFollow);
            } else if (user.getFollowing().equalsIgnoreCase(Config.FALSE)) {
                Glide.with(getApplicationContext()).load(R.mipmap.ic_follow1).into(ivFollow);
            }*/

            tvFollowers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ProfileActivity.this, FeaturesActivity.class);
                    intent.putExtra(Config.STRING_FRAGMENTS, Config.FOLLOWERS);
                    intent.putExtra(Config.STRING_SCREEN_NAME, screen_name);
                    startActivity(intent);
                }
            });

            tvFollowing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ProfileActivity.this, FeaturesActivity.class);
                    intent.putExtra(Config.STRING_FRAGMENTS, Config.FOLLOWING);
                    intent.putExtra(Config.STRING_SCREEN_NAME, screen_name);
                    startActivity(intent);
                }
            });

            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProfileActivity.this);

            ivTweetNotification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String notifications = user.getNotifications();
                    Log.d("Notifications", notifications);
                    if (fragmentUserTimeline != null) {
                        if (notifications.equalsIgnoreCase(Config.TRUE)) {
                            alertDialogBuilder
                                    .setMessage(Config.NOTIFICATION_ALERT + user.getName() + " Tweets.")
                                    .setCancelable(false)
                                    .setTitle(Config.TURN_OFF_NOTIFICATION)
                                    .setPositiveButton(Config.OK, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            user.setNotifications(Config.FALSE);
                                            fragmentUserTimeline.postFrienshipNotification(screen_name, Config.FALSE);
                                            Glide.with(getApplicationContext()).load(R.drawable.ic_unfollow_tweet).into(ivTweetNotification);
                                            dialog.cancel();
                                        }
                                    })
                                    .setNegativeButton(Config.CANCEL, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // if this button is clicked, just close
                                            // the dialog box and do nothing
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alertDialog = alertDialogBuilder.create();

                            alertDialog.show();
                        } else if (notifications.equalsIgnoreCase(Config.FALSE)) {
                            Toast.makeText(ProfileActivity.this, "You will now receive notifications when " + user.getName() + " Tweets.", Toast.LENGTH_LONG).show();
                            user.setNotifications(Config.TRUE);
                            fragmentUserTimeline.postFrienshipNotification(screen_name, Config.TRUE);
                            Glide.with(getApplicationContext()).load(R.drawable.ic_follow_tweet).into(ivTweetNotification);
                        }
                    }
                }
            });

            ivFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String following = user.getFollowing();
                    if (fragmentUserTimeline != null) {
                        if (following.equalsIgnoreCase(Config.TRUE)) {

                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProfileActivity.this);
                            alertDialogBuilder
                                    .setMessage(Config.STOP_FOLLOWING_MESSAGE + user.getName() + " ?")
                                    .setCancelable(false)
                                    .setTitle(Config.STOP_FOLLOWING_TITLE)
                                    .setPositiveButton(Config.YES, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            user.setFollowing(Config.FALSE);
                                            fragmentUserTimeline.postFollowUnfollowUser(user, Config.TRUE);
                                            Glide.with(getApplicationContext()).load(R.mipmap.ic_follow1).into(ivFollow);

                                            // Once Unfollow, turn off tweet notifications too.
                                            user.setNotifications(Config.FALSE);
                                            fragmentUserTimeline.postFrienshipNotification(screen_name, Config.FALSE);
                                            ivTweetNotification.setVisibility(View.INVISIBLE);
                                            dialog.cancel();
                                        }
                                    })
                                    .setNegativeButton(Config.NO, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // if this button is clicked, just close
                                            // the dialog box and do nothing
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alertDialog = alertDialogBuilder.create();

                            alertDialog.show();
                        } else if (following.equalsIgnoreCase(Config.FALSE)) {
                            ivTweetNotification.setVisibility(View.VISIBLE);
                            Glide.with(getApplicationContext()).load(R.drawable.ic_unfollow_tweet).into(ivTweetNotification);
                            user.setFollowing(Config.TRUE);
                            fragmentUserTimeline.postFollowUnfollowUser(user, Config.FALSE);
                            Glide.with(getApplicationContext()).load(R.mipmap.ic_following1).into(ivFollow);
                        }
                    }
                }
            });

        }

        ivSearchGlass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        ivUpArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public class UserTweetPagerAdapter extends FragmentStatePagerAdapter {

        private String [] tabsList = {"Tweets","Media","Favorites"};
        // Sparse array to keep track of registered fragments in memory
        private SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

        private String screen_name;

        // Adapter gets the manager to insert or remove fragment from the activity
        public UserTweetPagerAdapter(FragmentManager fm, String screen_name){
            super(fm);
            this.screen_name = screen_name;
        }

        // Controls the order and creation of the fragment within the manager
        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return new UserTimelineFragment().newInstance(screen_name);
                case 1:
                    return new MediaTimelineFragment().newInstance(screen_name);
                case 2:
                    return new FavoritesTimelineFragment().newInstance(screen_name);
            }
            return null;
        }

        public CharSequence getPageTitle (int position){
            return tabsList[position];
        }

        @Override
        public int getCount() {
            return tabsList.length;
        }

        // Register the fragment when the item is instantiated
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        // Unregister when the item is inactive
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        // Returns the fragment for the position (if instantiated)
        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }
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
