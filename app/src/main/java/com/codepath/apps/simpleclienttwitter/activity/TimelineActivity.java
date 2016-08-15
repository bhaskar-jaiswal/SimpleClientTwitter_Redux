package com.codepath.apps.simpleclienttwitter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.simpleclienttwitter.R;
import com.codepath.apps.simpleclienttwitter.fragment.HomeTimelineFragment;
import com.codepath.apps.simpleclienttwitter.fragment.MentionsTimelineFragment;
import com.codepath.apps.simpleclienttwitter.fragment.TweetsListFragment;
import com.codepath.apps.simpleclienttwitter.model.User;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimelineActivity extends AppCompatActivity {

    private TweetPagerAdapter tweetPagerAdapter;

    @BindView(R.id.viewpager)
    ViewPager vpPager;

    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        ButterKnife.bind(this);

        tweetPagerAdapter = new TweetPagerAdapter(getSupportFragmentManager());

        vpPager.setAdapter(tweetPagerAdapter);
        vpPager.setPageTransformer(true,new RotateUpTransformer());
        vpPager.setOffscreenPageLimit(3);
        tabStrip.setViewPager(vpPager);

//        setSupportActionBar(toolbar);

        /*SearchView searchView = (SearchView) findViewById(R.id.ivSearchView);
        int searchImgId = android.support.v7.appcompat.R.id.search_button;
        ImageView v = (ImageView) searchView.findViewById(searchImgId);
        v.setImageResource(R.drawable.ic_twitter_search);*/

    }

    public void onProfileView(MenuItem mItem){
        switch(mItem.getItemId()){
            case R.id.mi_profile:
                Intent intent = new Intent(this, ProfileActivity.class);
                User accountUser = TweetsListFragment.accountUser;
                intent.putExtra("user", Parcels.wrap(accountUser));
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.home_timeline, menu);
        return true;
    }

    public class TweetPagerAdapter extends FragmentPagerAdapter{

        private String [] tabsList = {"Home","Mentions"};

        // Adapter gets the manager to insert or remove fragment from the activity
        public TweetPagerAdapter(FragmentManager fm){
            super(fm);
        }

        // Controls the order and creation of the fragment within the manager
        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return new HomeTimelineFragment();
                case 1:
                    return new MentionsTimelineFragment();
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
    }
}
