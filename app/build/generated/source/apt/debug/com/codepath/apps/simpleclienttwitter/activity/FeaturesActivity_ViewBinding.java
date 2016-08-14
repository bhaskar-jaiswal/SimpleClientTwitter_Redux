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

public class FeaturesActivity_ViewBinding<T extends FeaturesActivity> implements Unbinder {
  protected T target;

  public FeaturesActivity_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.tvFollow = finder.findRequiredViewAsType(source, R.id.tvFollow, "field 'tvFollow'", TextView.class);
    target.ivUpArrow = finder.findRequiredViewAsType(source, R.id.ivUpArrow, "field 'ivUpArrow'", ImageView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.tvFollow = null;
    target.ivUpArrow = null;

    this.target = null;
  }
}
