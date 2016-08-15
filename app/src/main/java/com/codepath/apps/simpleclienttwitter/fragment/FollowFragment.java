package com.codepath.apps.simpleclienttwitter.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.codepath.apps.simpleclienttwitter.R;
import com.codepath.apps.simpleclienttwitter.activity.ProfileActivity;
import com.codepath.apps.simpleclienttwitter.adapter.FollowAdapter;
import com.codepath.apps.simpleclienttwitter.constant.Config;
import com.codepath.apps.simpleclienttwitter.model.User;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhaskarjaiswal on 8/13/16.
 */
public abstract class FollowFragment extends TweetsListFragment {

    protected ArrayList<User> followList;
    protected FollowAdapter followAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*if(getActivity().getActionBar() != null)
            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        else
            Log.d("Itsnull","its null");*/

        followList = new ArrayList<User>();
        followAdapter = new FollowAdapter(getActivity(), followList);

        followAdapter.setOnProfileImageClick(new FollowAdapter.OnProfileImageClick() {
            @Override
            public void onProfileClick(View itemView, int position) {
                User user = followList.get(position);
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                intent.putExtra("user", Parcels.wrap(user));
                startActivity(intent);
            }

            @Override
            public void onProfileFollowUnfollow(View itemView, int position) {
                User user = followList.get(position);
                String following = user.getFollowing();
                postFollowUnfollow(user, following);
//                if(!user.getFollowing().equalsIgnoreCase(following)) {
//                    Log.d("Updating ivFollow","same");
                ImageView ivFollow = (ImageView) itemView.findViewById(R.id.ivFollow);
                if (following.equalsIgnoreCase(Config.TRUE)) {
                    Glide.with(getContext()).load(R.mipmap.ic_follow).into(ivFollow);
                } else if (following.equalsIgnoreCase(Config.FALSE)) {
                    Glide.with(getContext()).load(R.mipmap.ic_following).into(ivFollow);
                }
//                followAdapter.notifyItemChanged(position);
//                }
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        progressBar_center.setVisibility(View.VISIBLE);
        rvTweets.setAdapter(followAdapter);
        return view;
    }

    protected void addUsers(List<User> list) {
        followList.addAll(list);
        followAdapter.notifyDataSetChanged();
    }
}
