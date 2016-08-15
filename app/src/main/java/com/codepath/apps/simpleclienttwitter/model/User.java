package com.codepath.apps.simpleclienttwitter.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.codepath.apps.simpleclienttwitter.constant.Config;
import com.codepath.apps.simpleclienttwitter.twitterapi.TwitterClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by bjaiswal on 8/3/2016.
 */
@Table(name = "User")
@Parcel(analyze = {User.class})
public class User extends Model{

    @Column(name = "name")
    public String name;

    @Column(name = "uid")
    public String uid;

    @Column(name = "screenName")
    public String screenName;

    @Column(name = "profileImageUrl")
    public String profileImageUrl;

    @Column(name = "background_image")
    public String background_image;

    @Column(name = "tagline")
    public String tagline;

    @Column(name = "followersCount")
    public int followersCount;

    @Column(name = "friendsCount")
    public int friendsCount;

    @Column(name = "following")
    public String following;

    @Column(name = "notifications")
    public String notifications;

    public static User fromJSON(JSONObject jsonObject){
        User user = new User();

        try {
            user.screenName = jsonObject.getString("screen_name");
            user.name = jsonObject.getString("name");
            user.uid = jsonObject.getString("id");
            user.profileImageUrl = jsonObject.getString("profile_image_url");
            user.background_image=jsonObject.getString("profile_background_image_url");
            user.tagline=jsonObject.getString("description");
            user.followersCount=jsonObject.getInt("followers_count");
            user.friendsCount=jsonObject.getInt("friends_count");
            user.following = Boolean.toString(jsonObject.getBoolean("following"));
            user.notifications = Boolean.toString(jsonObject.getBoolean("notifications"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static ArrayList<User> getFollowList(JSONObject jsonObject){
        ArrayList<User> list = new ArrayList<User>();
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("users");
            TwitterClient.params_follow.put(Config.STRING_NEXT_CURSOR,jsonObject.get("next_cursor"));
            for(int i=0;i<jsonArray.length();i++){
                list.add(fromJSON(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public void setNotifications(String notifications) {
        this.notifications = notifications;
    }

    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }
    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getBackground_image() {
        return background_image;
    }

    public String getTagline() {
        return tagline;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFriendsCount() {
        return friendsCount;
    }

    public String getFollowing() {
        return following;
    }

    public String getNotifications() {
        return notifications;
    }
}
