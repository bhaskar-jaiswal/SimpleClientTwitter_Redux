// Generated code from Butter Knife. Do not modify!
package com.codepath.apps.simpleclienttwitter.activity;

import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import com.codepath.apps.simpleclienttwitter.R;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class ProfileActivity_ViewBinding<T extends ProfileActivity> implements Unbinder {
  protected T target;

  public ProfileActivity_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.ivBackgroundImage = finder.findRequiredViewAsType(source, R.id.ivBackgroundImage, "field 'ivBackgroundImage'", ImageView.class);
    target.ivProfileImage = finder.findRequiredViewAsType(source, R.id.ivProfileImage, "field 'ivProfileImage'", ImageView.class);
    target.ivUpArrow = finder.findRequiredViewAsType(source, R.id.ivUpArrow, "field 'ivUpArrow'", ImageView.class);
    target.ivSearchGlass = finder.findRequiredViewAsType(source, R.id.ivSearchGlass, "field 'ivSearchGlass'", ImageView.class);
    target.ivTweetNotification = finder.findRequiredViewAsType(source, R.id.ivTweetNotification, "field 'ivTweetNotification'", ImageView.class);
    target.ivFollow = finder.findRequiredViewAsType(source, R.id.ivFollow, "field 'ivFollow'", ImageView.class);
    target.tvScreenname = finder.findRequiredViewAsType(source, R.id.tvScreenname, "field 'tvScreenname'", TextView.class);
    target.tvUsername = finder.findRequiredViewAsType(source, R.id.tvUsername, "field 'tvUsername'", TextView.class);
    target.tvTagline = finder.findRequiredViewAsType(source, R.id.tvTagline, "field 'tvTagline'", TextView.class);
    target.tvFollowers = finder.findRequiredViewAsType(source, R.id.tvFollowers, "field 'tvFollowers'", TextView.class);
    target.tvFollowing = finder.findRequiredViewAsType(source, R.id.tvFollowing, "field 'tvFollowing'", TextView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.ivBackgroundImage = null;
    target.ivProfileImage = null;
    target.ivUpArrow = null;
    target.ivSearchGlass = null;
    target.ivTweetNotification = null;
    target.ivFollow = null;
    target.tvScreenname = null;
    target.tvUsername = null;
    target.tvTagline = null;
    target.tvFollowers = null;
    target.tvFollowing = null;

    this.target = null;
  }
}
