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

public class ViewHolderMessage_ViewBinding<T extends ViewHolderMessage> implements Unbinder {
  protected T target;

  public ViewHolderMessage_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.ivImage = finder.findRequiredViewAsType(source, R.id.ivProfileImage, "field 'ivImage'", ImageView.class);
    target.tvUsername = finder.findRequiredViewAsType(source, R.id.tvUsername, "field 'tvUsername'", TextView.class);
    target.tvScreenname = finder.findRequiredViewAsType(source, R.id.tvScreenname, "field 'tvScreenname'", TextView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.ivImage = null;
    target.tvUsername = null;
    target.tvScreenname = null;

    this.target = null;
  }
}
