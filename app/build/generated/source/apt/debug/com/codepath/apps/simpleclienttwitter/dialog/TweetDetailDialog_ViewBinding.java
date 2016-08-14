// Generated code from Butter Knife. Do not modify!
package com.codepath.apps.simpleclienttwitter.dialog;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import com.codepath.apps.simpleclienttwitter.R;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class TweetDetailDialog_ViewBinding<T extends TweetDetailDialog> implements Unbinder {
  protected T target;

  public TweetDetailDialog_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.ivImageView = finder.findRequiredViewAsType(source, R.id.ivImageView, "field 'ivImageView'", ImageView.class);
    target.tvUsername = finder.findRequiredViewAsType(source, R.id.tvUsername, "field 'tvUsername'", TextView.class);
    target.tvScreenname = finder.findRequiredViewAsType(source, R.id.tvScreenname, "field 'tvScreenname'", TextView.class);
    target.tvBody = finder.findRequiredViewAsType(source, R.id.tvBody, "field 'tvBody'", TextView.class);
    target.etEditReply = finder.findRequiredViewAsType(source, R.id.etEditReply, "field 'etEditReply'", EditText.class);
    target.tvCharacters = finder.findRequiredViewAsType(source, R.id.tvCharacters, "field 'tvCharacters'", TextView.class);
    target.tvReplyTo = finder.findRequiredViewAsType(source, R.id.tvReplyTo, "field 'tvReplyTo'", TextView.class);
    target.btnReply = finder.findRequiredViewAsType(source, R.id.btnReply, "field 'btnReply'", Button.class);
    target.ivTweetImage = finder.findRequiredViewAsType(source, R.id.ivTweetImage, "field 'ivTweetImage'", ImageView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.ivImageView = null;
    target.tvUsername = null;
    target.tvScreenname = null;
    target.tvBody = null;
    target.etEditReply = null;
    target.tvCharacters = null;
    target.tvReplyTo = null;
    target.btnReply = null;
    target.ivTweetImage = null;

    this.target = null;
  }
}
