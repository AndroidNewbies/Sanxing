package io.github.celestialphineas.sanxing.UISupportActivities;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.celestialphineas.sanxing.R;

public class AboutActivity extends AppCompatActivity {
    @BindView(R.id.about_toolbar)           Toolbar toolbar;
    @BindView(R.id.credits_card)            CardView creditsCard;
    @BindView(R.id.long_long_text)          AppCompatTextView longlongText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        try {
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("licenses")));
            String s;
            while((s = reader.readLine()) != null) {
                stringBuilder.append(s).append('\n');
            }
            reader.close();
            longlongText.setText(stringBuilder.toString());
        } catch (Exception e) {
            // e.printStackTrace();
            longlongText.setText("Something wrong.");
        }
        //////// Toolbar ////////
        // Set the toolbar as the default action bar of the window
        setSupportActionBar(toolbar);
        // Disable the title
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Enable the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick(R.id.feedback_card)
    void feedbackOnClickBehavior() {
        Uri uri=Uri.parse("http://github.com/AndroidNewbies/Sanxing/issues");
        Intent intent=new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }

    @OnClick(R.id.credits_card)
    void creditsOnClickBehavior() {
        TransitionManager.beginDelayedTransition(creditsCard);
        if(longlongText.getVisibility() == View.VISIBLE) {
            longlongText.setVisibility(View.GONE);
        } else {
            longlongText.setVisibility(View.VISIBLE);
        }
    }
}
