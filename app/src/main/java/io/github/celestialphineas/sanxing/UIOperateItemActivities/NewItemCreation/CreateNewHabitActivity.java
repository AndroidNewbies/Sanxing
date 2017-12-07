package io.github.celestialphineas.sanxing.UIOperateItemActivities.NewItemCreation;

import android.animation.Animator;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.SeekBar;

import butterknife.BindInt;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.celestialphineas.sanxing.R;

public class CreateNewHabitActivity extends AppCompatActivity {
    // Frequency select value
    // 0 - Thrice a day
    // 1 - Twice a day
    // 2 - Once a day
    // 3 - Once two days
    // 4 - Once three days
    // 5 - Once a week
    // 6 - Once a fortnight
    // 7 - Once a month
    int selectedFreq = 2;
    // Selected importance 0, 1, 2, 3, 4
    int selectedImportance = 2;
    @BindView(R.id.create_new_item_toolbar)         Toolbar toolbar;
    @BindView(R.id.input_title)                     TextInputEditText inputTitle;
    @BindView(R.id.habit_description_content)       TextInputEditText descriptionContent;
    @BindView(R.id.freq_text)                       AppCompatTextView freqText;
    @BindView(R.id.habit_freq_seek_bar)             AppCompatSeekBar habitFreqSeekBar;
    @BindView(R.id.habit_importance_seek_bar)       AppCompatSeekBar habitImportance;
    @BindView(R.id.habit_linear_layout)             LinearLayoutCompat linearLayout;
    @BindString(R.string.snack_title_not_set)       String titleNotSet;
    @BindString(R.string.create_new_habit)          String title;

    @BindString(R.string.thrice_a_day)              String thriceADay;
    @BindString(R.string.twice_a_day)               String twiceADay;
    @BindString(R.string.every_day)                 String everyDay;
    @BindString(R.string.once_two_days)             String onceTwoDays;
    @BindString(R.string.once_three_days)           String onceThreeDays;
    @BindString(R.string.once_a_week)               String onceAWeek;
    @BindString(R.string.once_a_fortnight)          String onceAFortnight;
    @BindString(R.string.once_a_month)              String onceAMonth;

    @BindInt(R.integer.snack_bar_timeout)           int snackBarTimeout;
    @BindInt(R.integer.reveal_time)                 int revealTime;
    // Animation center
    int cx, cy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_habit);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ButterKnife.bind(this);
        cx = getResources().getDisplayMetrics().widthPixels - 120;
        cy = getResources().getDisplayMetrics().heightPixels - 180;

        //////// Animation ////////
        animationReveal(savedInstanceState);

        //////// Toolbar ////////
        // Set the toolbar as the default action bar of the window
        setSupportActionBar(toolbar);
        // Disable the title
        getSupportActionBar().setTitle(title);
        // Enable the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Single line input of the title
        inputTitle.setSingleLine();

        // Seekbar on change
        freqText.setText(freqIntToString(selectedFreq));
        habitFreqSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                selectedFreq = seekBar.getProgress();
                freqText.setText(freqIntToString(selectedFreq));
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        habitImportance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                selectedImportance = seekBar.getProgress();
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    private String freqIntToString(int freq) {
        switch (freq) {
            case 0: return thriceADay;
            case 1: return twiceADay;
            case 2: return everyDay;
            case 3: return onceTwoDays;
            case 4: return onceThreeDays;
            case 5: return onceAWeek;
            case 6: return onceAFortnight;
            case 7: default: return onceAMonth;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_new_item_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_confirm_new_item) {
            // TODO: The verifyForm() method can be modified if necessary
            if(!verifyForm()) return true;
            ////////
            // TODO: Then register the changes in the database
            //////// INSERT NECESSARY CODE HERE
            // Use "selectedFreq" for frequency index
            // Use "selectedImportance" for the the importance 0~4
            // Use inputTitle.getText().toString() to get the title
            // Use descriptionContent.getText().toString() to get the description

            // finish
            cx = getResources().getDisplayMetrics().widthPixels - 120;
            cy = 100;
            animationExit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Verify input
    private boolean verifyForm() {
        if(inputTitle != null && inputTitle.getText() != null
                && inputTitle.getText().toString().trim().isEmpty()) {
            Snackbar.make(linearLayout, titleNotSet, snackBarTimeout)
                    .show();
            inputTitle.requestFocus();
            return false;
        }
        // TODO
        return true;
    }


    //////////////// ANIMATIONS ////////////////
    // Reveal animation implementation
    private void animationReveal(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            linearLayout.setVisibility(View.INVISIBLE);
            ViewTreeObserver viewTreeObserver = linearLayout.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        float finalRadius = Math.max(linearLayout.getWidth(), linearLayout.getHeight());
                        // create the animator for this view (the start radius is zero)
                        Animator circularReveal = ViewAnimationUtils.createCircularReveal(linearLayout, cx, cy, 0, finalRadius);
                        circularReveal.setDuration(revealTime);

                        // make the view visible and start the animation
                        linearLayout.setVisibility(View.VISIBLE);
                        circularReveal.start();
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            linearLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        } else {
                            linearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                    }
                });
            }
        }
    }
    // Exiting animation
    private void animationExit() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            float finalRadius = Math.max(linearLayout.getWidth(), linearLayout.getHeight());
            Animator circularReveal
                    = ViewAnimationUtils.createCircularReveal(linearLayout, cx, cy, finalRadius, 0);
            circularReveal.setDuration(revealTime);
            circularReveal.addListener(new Animator.AnimatorListener() {
                @Override public void onAnimationStart(Animator animator) { }
                @Override
                public void onAnimationEnd(Animator animator) {
                    linearLayout.setVisibility(View.INVISIBLE);
                    finish();
                }
                @Override public void onAnimationCancel(Animator animator) { }
                @Override public void onAnimationRepeat(Animator animator) { }
            });
            circularReveal.setDuration(400);
            circularReveal.start();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        cx = 120;
        cy = 100;
        animationExit();
    }
}
