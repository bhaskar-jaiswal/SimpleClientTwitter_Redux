package com.codepath.apps.simpleclienttwitter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.simpleclienttwitter.adapter.MessageAdapter;
import com.codepath.apps.simpleclienttwitter.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhaskarjaiswal on 8/14/16.
 */
public abstract class MessageFragment extends TweetsListFragment {

    protected ArrayList<User> messageList;
    protected MessageAdapter messageAdapter;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*if(getActivity().getActionBar() != null)
            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        else
            Log.d("Itsnull","its null");*/

        messageList = new ArrayList<User>();
        messageAdapter = new MessageAdapter(getActivity(), messageList);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        rvTweets.setAdapter(messageAdapter);
        return view;
    }

    protected void addUsers(List<User> list) {
        messageList.addAll(list);
        messageAdapter.notifyDataSetChanged();
    }
}
