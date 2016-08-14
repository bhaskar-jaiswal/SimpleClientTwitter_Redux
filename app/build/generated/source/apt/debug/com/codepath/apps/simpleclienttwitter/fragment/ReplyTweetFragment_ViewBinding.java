// Generated code from Butter Knife. Do not modify!
package com.codepath.apps.simpleclienttwitter.fragment;

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

public class ReplyTweetFragment_ViewBinding<T extends ReplyTweetFragment> implements Unbinder {
  protected T target;

  public ReplyTweetFragment_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.ivCancel = finder.findRequiredViewAsType(source, R.id.ivCancel, "field 'ivCancel'", ImageView.class);
    target.ivProfileImage = finder.findRequiredViewAsType(source, R.id.ivProfileImage, "field 'ivProfileImage'", ImageView.class);
    target.tvInReplyTo = finder.findRequiredViewAsType(source, R.id.tvInReplyTo, "field 'tvInReplyTo'", TextView.class);
    target.etTextArea = finder.findRequiredViewAsType(source, R.id.etTextArea, "field 'etTextArea'", EditText.class);
    target.tvCharacters = finder.findRequiredViewAsType(source, R.id.tvCharacters, "field 'tvCharacters'", TextView.class);
    target.btnReply = finder.findRequiredViewAsType(source, R.id.btnReply, "field 'btnReply'", Button.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.ivCancel = null;
    target.ivProfileImage = null;
    target.tvInReplyTo = null;
    target.etTextArea = null;
    target.tvCharacters = null;
    target.btnReply = null;

    this.target = null;
  }
}
