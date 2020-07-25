package io.github.celestialphineas.sanxing.UIOperateItemActivities.Base;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.SeekBar;

import java.text.DateFormat;
import java.util.Calendar;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import io.github.celestialphineas.sanxing.R;

/**
 * Created by celestialphineas on 17-12-7.
 */

public abstract class OperateTimeLeftActivityBase extends OperateItemActivityBase {
    /// Selected importance 0, 1, 2, 3, 4
    protected int selectedImportance = 2;
    // Root view of the activity
    View rootLayout;
    // The selected date/time will be stored here
    protected final Calendar startCalendar = Calendar.getInstance();
    protected final Calendar dueCalendar = Calendar.getInstance();
    // Flags to show if the user has already set date and time
    protected boolean setDate1 = false, setDate2 = false;
    @BindView(R.id.create_new_item_toolbar)
    Toolbar toolbar;
    @BindView(R.id.input_title)
    protected TextInputEditText inputTitle;
    @BindView(R.id.time_left_from_date_content)
    protected TextInputEditText startDateContent;
    @BindView(R.id.time_left_due_date_content)
    protected TextInputEditText dueDateContent;
    @BindView(R.id.time_left_description_content)
    protected TextInputEditText descriptionContent;
    @BindView(R.id.time_left_importance_seek_bar)
    protected AppCompatSeekBar timeLeftImportance;
    @BindString(R.string.snack_title_not_set)
    protected String titleNotSet;
    @BindString(R.string.snack_date_not_set)
    protected String dateNotSet;
    @BindString(R.string.snack_time_not_set)
    protected String timeNotSet;
    @BindString(R.string.snack_op_set)
    protected String setSnack;
    @BindString(R.string.snack_time_passed)
    protected String hasPassed;
    @BindString(R.string.snack_start_before_due)
    protected String startBeforeDue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        rootLayout = getWindow().getDecorView().getRootView();
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_create_new_time_left);
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

        //////// Disable keyboard events of the date and text editTexts
        startDateContent.setKeyListener(null);
        dueDateContent.setKeyListener(null);

        timeLeftImportance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                selectedImportance = seekBar.getProgress();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    // Verify input
    protected boolean verifyForm(View layout) {
        Calendar now = Calendar.getInstance();
        if (inputTitle != null && inputTitle.getText() != null
                && inputTitle.getText().toString().trim().isEmpty()) {
            Snackbar.make(layout, titleNotSet, snackBarTimeout)
                    .show();
            inputTitle.requestFocus();
            return false;
        } else if (!setDate1) {
            Snackbar.make(layout, dateNotSet, snackBarTimeout)
                    .setAction(setSnack, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            timeLeftStartDateOnClickBehavior();
                        }
                    })
                    .show();
            return false;
        } else if (!setDate2) {
            Snackbar.make(layout, dateNotSet, snackBarTimeout)
                    .setAction(setSnack, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            timeLeftDueDateOnClickBehavior();
                        }
                    })
                    .show();
            return false;
        } else if (dueCalendar.before(startCalendar)) {
            Snackbar.make(layout, startBeforeDue, snackBarTimeout)
                    .setAction(setSnack, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            timeLeftDueDateOnClickBehavior();
                        }
                    })
                    .show();
            return false;
        } else if (dueCalendar.before(now)) {
            DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getBaseContext());
            String timeString = dateFormat.format(dueCalendar.getTime()) + " ";
            Snackbar.make(layout, timeString + hasPassed, snackBarTimeout)
                    .setAction(setSnack, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            timeLeftDueDateOnClickBehavior();
                        }
                    })
                    .show();
            return false;
        }
        return true;
    }

    //////////////// SELECT DATE ////////////////
    // Select date
    // This method pop up a date selector, and allow user to select date.
    // The selected date will be stored in the dueCalendar object.
    @OnFocusChange(R.id.time_left_from_date_content)
    void timeLeftFromDateOnFocusBehavior(View view, boolean hasFocus) {
        if (hasFocus) {
            timeLeftStartDateOnClickBehavior();
        }
    }

    @OnClick(R.id.time_left_from_date_content)
    void timeLeftStartDateOnClickBehavior() {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                startCalendar.set(Calendar.YEAR, year);
                startCalendar.set(Calendar.MONTH, monthOfYear);
                startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                DateFormat sdf = android.text.format.DateFormat.getDateFormat(getBaseContext());
                startDateContent.setText(sdf.format(startCalendar.getTime()));
                setDate1 = true;
            }
        };
        new DatePickerDialog(this, date,
                startCalendar.get(Calendar.YEAR),
                startCalendar.get(Calendar.MONTH),
                startCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @OnFocusChange(R.id.time_left_due_date_content)
    void timeLeftDueDateOnFocusBehavior(View view, boolean hasFocus) {
        if (hasFocus) {
            timeLeftDueDateOnClickBehavior();
        }
    }

    @OnClick(R.id.time_left_due_date_content)
    void timeLeftDueDateOnClickBehavior() {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dueCalendar.set(Calendar.YEAR, year);
                dueCalendar.set(Calendar.MONTH, monthOfYear);
                dueCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                DateFormat sdf = android.text.format.DateFormat.getDateFormat(getBaseContext());
                dueDateContent.setText(sdf.format(dueCalendar.getTime()));
                setDate2 = true;
            }
        };
        new DatePickerDialog(this, date,
                dueCalendar.get(Calendar.YEAR),
                dueCalendar.get(Calendar.MONTH),
                dueCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}
