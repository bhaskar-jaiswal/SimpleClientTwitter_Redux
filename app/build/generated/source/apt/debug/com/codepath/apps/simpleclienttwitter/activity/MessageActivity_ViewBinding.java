// Generated code from Butter Knife. Do not modify!
package com.codepath.apps.simpleclienttwitter.activity;

import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import com.codepath.apps.simpleclienttwitter.R;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class MessageActivity_ViewBinding<T extends MessageActivity> implements Unbinder {
  protected T target;

  public MessageActivity_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.ivUpArrow = finder.findRequiredViewAsType(source, R.id.ivUpArrow, "field 'ivUpArrow'", ImageView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.ivUpArrow = null;

    this.target = null;
  }
}
