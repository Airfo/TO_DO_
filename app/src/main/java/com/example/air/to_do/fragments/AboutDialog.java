package com.example.air.to_do.fragments;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.air.to_do.activity.MainActivity;
import com.example.air.to_do.R;

public class AboutDialog extends AlertDialog.Builder {
    TextView about_tv;
    ImageView icon_app_iv;
    MainActivity mainActivity;

    public AboutDialog(final Context context, MainActivity mainActivity) {
        super(context);
        this.mainActivity = mainActivity;
        about_tv = new TextView(context);
        icon_app_iv = new ImageView(context);
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        icon_app_iv.setImageDrawable(getContext().getDrawable(R.mipmap.ic_launcher));
        about_tv.setText(R.string.version);
        about_tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        icon_app_iv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        linearLayout.addView(icon_app_iv);
        linearLayout.addView(about_tv);
        setTitle(R.string.about)
                .setView(linearLayout)
                .setNegativeButton(R.string.exit_about_button, null);
    }

    @Override
    public AlertDialog show() {
        return super.show();
    }
}
