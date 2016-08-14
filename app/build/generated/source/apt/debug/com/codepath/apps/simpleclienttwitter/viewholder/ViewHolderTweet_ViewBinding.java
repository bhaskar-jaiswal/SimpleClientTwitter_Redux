// Generated code from Butter Knife. Do not modify!
package com.codepath.apps.simpleclienttwitter.viewholder;

import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import com.codepath.apps.simpleclienttwitter.R;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class ViewHolderTweet_ViewBinding<T extends ViewHolderTweet> implements Unbinder {
  protected T target;

  public ViewHolderTweet_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.ivImage = finder.findRequiredViewAsType(source, R.id.ivProfileImage, "field 'ivImage'", ImageView.class);
    target.tvUsername = finder.findRequiredViewAsType(source, R.id.tvUsername, "field 'tvUsername'", TextView.class);
    target.tvScreenname = finder.findRequiredViewAsType(source, R.id.tvScreenname, "field 'tvScreenname'", TextView.class);
    target.tvHours = finder.findRequiredViewAsType(source, R.id.tvHours, "field 'tvHours'", TextView.class);
    target.tvBody = finder.findRequiredViewAsType(source, R.id.tvBody, "field 'tvBody'", TextView.class);
    target.ivTweetImage = finder.findRequiredViewAsType(source, R.id.ivTweetImage, "field 'ivTweetImage'", ImageView.class);
    target.ivReply = finder.findRequiredViewAsType(source, R.id.ivReply, "field 'ivReply'", ImageView.class);
    target.ivReweet = finder.findRequiredViewAsType(source, R.id.ivReweet, "field 'ivReweet'", ImageView.class);
    target.tvReweetCount = finder.findRequiredViewAsType(source, R.id.tvReweetCount, "field 'tvReweetCount'", TextView.class);
    target.ivFavorite = finder.findRequiredViewAsType(source, R.id.ivFavorite, "field 'ivFavorite'", ImageView.class);
    target.tvFavoriteCount = finder.findRequiredViewAsType(source, R.id.tvFavoriteCount, "field 'tvFavoriteCount'", TextView.class);
    target.ivMessage = finder.findRequiredViewAsType(source, R.id.ivMessage, "field 'ivMessage'", ImageView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.ivImage = null;
    target.tvUsername = null;
    target.tvScreenname = null;
    target.tvHours = null;
    target.tvBody = null;
    target.ivTweetImage = null;
    target.ivReply = null;
    target.ivReweet = null;
    target.tvReweetCount = null;
    target.ivFavorite = null;
    target.tvFavoriteCount = null;
    target.ivMessage = null;

    this.target = null;
  }
}
