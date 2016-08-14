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

public class ComposeTweetsDialog_ViewBinding<T extends ComposeTweetsDialog> implements Unbinder {
  protected T target;

  public ComposeTweetsDialog_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.ivProfileImage = finder.findRequiredViewAsType(source, R.id.ivProfileImage, "field 'ivProfileImage'", ImageView.class);
    target.ivCancel = finder.findRequiredViewAsType(source, R.id.ivCancel, "field 'ivCancel'", ImageView.class);
    target.tvScreenname = finder.findRequiredViewAsType(source, R.id.tvScreenname, "field 'tvScreenname'", TextView.class);
    target.tvUsername = finder.findRequiredViewAsType(source, R.id.tvUsername, "field 'tvUsername'", TextView.class);
    target.etTextArea = finder.findRequiredViewAsType(source, R.id.etTextArea, "field 'etTextArea'", EditText.class);
    target.button = finder.findRequiredViewAsType(source, R.id.button, "field 'button'", Button.class);
    target.tvCharacters = finder.findRequiredViewAsType(source, R.id.tvCharacters, "field 'tvCharacters'", TextView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.ivProfileImage = null;
    target.ivCancel = null;
    target.tvScreenname = null;
    target.tvUsername = null;
    target.etTextArea = null;
    target.button = null;
    target.tvCharacters = null;

    this.target = null;
  }
}
