package com.codepath.apps.simpleclienttwitter.constant;

/**
 * Created by bhaskarjaiswal on 8/2/16.
 */
public class Config {
    public static final String CONSUMER_KEY="u0wR0lRZQNQmnqZxvLwSmfKz7";
    public static final String CONSUMER_SECRET="2a9JmfAG4tXnckJBDMVugWesSYeLQSOzzo6qmJGXN3KBVKzRbI";
    public static final String REST_URL = "https://api.twitter.com/1.1";
    public static final Integer COUNT=25;
    public static final Integer CHARACTER_COUNT = 140;
    public static final Integer BLUE = 130;
    public static final Integer GRAY = 110;
    public static final Integer SINCE_ID = 1;
    public static final int TWEET = 0;
    public static final int FOLLOW = 0;

    //Strings
    public static final String STRING_COUNT="count";
    public static final String STRING_SINCEID="since_id";
    public static final String FALSE = "false";
    public static final String TRUE = "true";


    public static final String STRING_HOME_TIMELINE = "statuses/home_timeline.json";
    public static final String STRING_MENTIONS_TIMELINE = "statuses/mentions_timeline.json";
    public static final String STRING_USER_DETAIL = "account/verify_credentials.json";
    public static final String STRING_USER_TIMELINE = "statuses/user_timeline.json";
    public static final String STRING_POST_STATUS = "statuses/update.json";
    public static final String STRING_RETWEET = "statuses/retweet/:id.json";
    public static final String STRING_UNTWEET = "statuses/unretweet/:id.json";
    public static final String STRING_FAVORITE = "favorites/create.json";
    public static final String STRING_UNFAVORITE = "favorites/destroy.json";
    public static final String STRING_FOLLOWERS = "followers/list.json";
    public static final String STRING_FRIENDS = "friends/list.json";
    public static final String STRING_FRIENDS_CREATE = "friendships/create.json";
    public static final String STRING_FRIENDS_DESTROY = "friendships/destroy.json";

    public static final String STATUS = "status";
    public static final String CURSOR = "-1";
    public static final String STRING_INCLUDE_USER_ENTITIES = "include_user_entities";
    public static final String INCLUDE_USER_ENTITIES = "false";

    public static final String STRING_FOLLOW = "follow";
    public static final String FOLLOW_FALSE = "false";
    public static final String STRING_NEXT_CURSOR = "cursor";
    public static final String REPLY_STATUS_ID = "in_reply_to_status_id";
    public static final Boolean SKIP_STATUS = false ;
    public static final String STRING_SKIP_STATUS = "skip_status";
    public static final String STRING_INCLUDE_ENTITIES="include_entities";
    public static final String STRING_SCREEN_NAME="screen_name";
    public static final String MAX_ID = "max_id";
    public static final String DISCARD_MESSAGE = "Discard This Message ?";
    public static final String NETWORK_UNAVAILABLE= "Network Not Available";
    public static final String USER_INFORMATION_UNAVAILABLE="Unable to Retrieve User Information";
    public static final String STRING_TWEET = "TWEET";
    public static final String STRING_REPLY = "Reply";
    public static final String STRING_REPLY_TO = "Reply to ";
    public static final String STRING_IN_REPLY_TO = "In reply to ";
    public static final String YES = "Yes";
    public static final String NO = "No";
    public static final String ID = "id";

    public static final String STRING_FRAGMENTS= "fragments";
    public static final String FOLLOWING= "Following";
    public static final String FOLLOWERS= "Followers";

    public static final Boolean INCLUDE_ENTITIES=false;

    public static final String TWITTER_TIMESTAMP="EEE MMM dd HH:mm:ss ZZZZZ yyyy";
}
