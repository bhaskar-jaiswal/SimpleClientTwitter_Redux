package com.codepath.apps.simpleclienttwitter.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.codepath.apps.simpleclienttwitter.R;
import com.codepath.apps.simpleclienttwitter.constant.Config;
import com.codepath.apps.simpleclienttwitter.model.User;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bhaskarjaiswal on 8/6/16.
 */
public class ComposeTweetsDialog extends DialogFragment {

    @BindView(R.id.ivProfileImage)
    ImageView ivProfileImage;

    @BindView(R.id.ivCancel)
    ImageView ivCancel;

    @BindView(R.id.tvScreenname)
    TextView tvScreenname;

    @BindView(R.id.tvUsername)
    TextView tvUsername;

    @BindView(R.id.etTextArea)
    EditText etTextArea;

    @BindView(R.id.button)
    Button button;

    @BindView(R.id.tvCharacters)
    TextView tvCharacters;

    private OnTweetSubmission onTweetSubmission;
    private static Integer textLength;

    public ComposeTweetsDialog() {
    }

    public interface OnTweetSubmission{
        public void onTweetsubmitted(String tweet, String id);
    }

    public void setCallSubmitTweet(OnTweetSubmission onTweetSubmission) {
        this.onTweetSubmission = onTweetSubmission;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.compose_tweets, container);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        User user = (User) Parcels.unwrap(getArguments().getParcelable("accountUser"));
        tvScreenname.setText("@"+user.getScreenName());
        tvUsername.setText(user.getName());

        Glide.with(getActivity().getApplicationContext()).load(user.getProfileImageUrl()).asBitmap()
                .centerCrop().into(new BitmapImageViewTarget(ivProfileImage) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(view.getContext().getResources(), resource);
                circularBitmapDrawable.setCornerRadius(15);
                ivProfileImage.setImageDrawable(circularBitmapDrawable);
            }
        });

        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etTextArea.getText().toString().length() != 0) {
                    showAlertDialog();
                }else{
                    getDialog().dismiss();
                }
            }
        });

        etTextArea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                characterCount(tvCharacters, etTextArea, Config.STRING_TWEET, button);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etTextArea.getText().toString().trim().length() != 0) {
                    onTweetSubmission.onTweetsubmitted(etTextArea.getText().toString(),Integer.toString(0));
                    etTextArea.setText("");
                    getDialog().dismiss();
                }
            }
        });
    }

    private void showAlertDialog(){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());


//        alertDialogBuilder.setTitle(task.getTaskName());

        alertDialogBuilder
                .setMessage(Config.DISCARD_MESSAGE)
                .setCancelable(false)
                .setPositiveButton(Config.YES, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        etTextArea.setText("");
                        getDialog().dismiss();
                    }
                })
                .setNegativeButton(Config.NO, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }

    public static void characterCount(TextView tvCharacters, EditText etTextArea, String buttonName, Button button){
        textLength = etTextArea.getText().length();
        tvCharacters.setText((Config.CHARACTER_COUNT-textLength)+"");

        if(textLength <= Config.CHARACTER_COUNT){
            button.setClickable(true);
            button.setTextColor(Color.WHITE);
            button.setBackground(new ColorDrawable(Color.parseColor("#00ACED")));
            button.setText(buttonName);
        }else{
            button.setClickable(false);
            button.setTextColor(Color.RED);
            button.setBackground(new ColorDrawable(Color.parseColor("#E2EFF3")));
            button.setText((Config.CHARACTER_COUNT-textLength)+"");
            tvCharacters.setText("");
        }

        if(textLength < Config.GRAY){
            tvCharacters.setTextColor(Color.DKGRAY);
        }else if (textLength < Config.BLUE){
            tvCharacters.setTextColor(Color.BLUE);
        }else{
            tvCharacters.setTextColor(Color.RED);
        }
    }
}
