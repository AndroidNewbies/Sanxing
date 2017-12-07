package io.github.celestialphineas.sanxing.UIOperateItemActivities.Base;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.celestialphineas.sanxing.R;

/**
 * Created by celestialphineas on 17-12-7.
 */

public abstract class OperateHabitActivityBase extends OperateItemActivityBase {
    // Frequency select value
    // 0 - Thrice a day
    // 1 - Twice a day
    // 2 - Once a day
    // 3 - Once two days
    // 4 - Once three days
    // 5 - Once a week
    // 6 - Once a fortnight
    // 7 - Once a month
    protected int selectedFreq = 2;
    // Selected importance 0, 1, 2, 3, 4
    protected int selectedImportance = 2;
    View rootLayout;
    @BindView(R.id.create_new_item_toolbar)
    Toolbar toolbar;
    @BindView(R.id.input_title)                     protected TextInputEditText inputTitle;
    @BindView(R.id.habit_description_content)       protected TextInputEditText descriptionContent;
    @BindView(R.id.freq_text)                       protected AppCompatTextView freqText;
    @BindView(R.id.habit_freq_seek_bar)             protected AppCompatSeekBar habitFreqSeekBar;
    @BindView(R.id.habit_importance_seek_bar)       protected AppCompatSeekBar habitImportance;
    @BindString(R.string.snack_title_not_set)       protected String titleNotSet;

    @BindString(R.string.thrice_a_day)              protected String thriceADay;
    @BindString(R.string.twice_a_day)               protected String twiceADay;
    @BindString(R.string.every_day)                 protected String everyDay;
    @BindString(R.string.once_two_days)             protected String onceTwoDays;
    @BindString(R.string.once_three_days)           protected String onceThreeDays;
    @BindString(R.string.once_a_week)               protected String onceAWeek;
    @BindString(R.string.once_a_fortnight)          protected String onceAFortnight;
    @BindString(R.string.once_a_month)              protected String onceAMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        rootLayout = getWindow().getDecorView().getRootView();
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_create_new_habit);
        ButterKnife.bind(this);

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

    public String freqIntToString(int freq) {
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

    // Verify input
    protected boolean verifyForm(View layout) {
        if(inputTitle != null && inputTitle.getText() != null
                && inputTitle.getText().toString().trim().isEmpty()) {
            Snackbar.make(layout, titleNotSet, snackBarTimeout)
                    .show();
            inputTitle.requestFocus();
            return false;
        }
        // TODO
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_new_item_menu, menu);
        return true;
    }
}
