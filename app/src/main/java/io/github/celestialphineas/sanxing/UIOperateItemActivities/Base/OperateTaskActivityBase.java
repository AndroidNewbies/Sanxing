package io.github.celestialphineas.sanxing.UIOperateItemActivities.Base;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.SeekBar;
import android.widget.TimePicker;

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

public abstract class OperateTaskActivityBase extends OperateItemActivityBase {
    // Selected importance 0, 1, 2, 3, 4
    protected int selectedImportance = 2;
    // The selected date/time will be stored here
    final protected Calendar dueCalendar = Calendar.getInstance();
    // Flags to show if the user has already set date and time
    protected boolean setDate = false, setTime = false;
    View rootLayout;
    @BindView(R.id.input_title)                     protected TextInputEditText inputTitle;
    @BindView(R.id.task_due_date_content)           protected TextInputEditText dueDateContent;
    @BindView(R.id.task_due_time_content)           protected TextInputEditText dueTimeContent;
    @BindView(R.id.task_description_content)        protected TextInputEditText descriptionContent;
    @BindView(R.id.task_importance_seek_bar)        protected AppCompatSeekBar taskImportance;
    @BindString(R.string.snack_title_not_set)       protected String titleNotSet;
    @BindString(R.string.snack_date_not_set)        protected String dateNotSet;
    @BindString(R.string.snack_time_not_set)        protected String timeNotSet;
    @BindString(R.string.snack_op_set)              protected String setSnack;
    @BindString(R.string.snack_time_passed)         protected String hasPassed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        rootLayout = getWindow().getDecorView().getRootView();
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_create_new_task);
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
        dueDateContent.setKeyListener(null);
        dueTimeContent.setKeyListener(null);

        taskImportance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                selectedImportance = seekBar.getProgress();
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    // Verify input
    protected boolean verifyForm() {
        Calendar now = Calendar.getInstance();
        if(inputTitle != null && inputTitle.getText() != null
                && inputTitle.getText().toString().trim().isEmpty()) {
            Snackbar.make(rootLayout, titleNotSet, snackBarTimeout)
                    .show();
            inputTitle.requestFocus();
            return false;
        } else if(!setDate) {
            Snackbar.make(rootLayout, dateNotSet, snackBarTimeout)
                    .setAction(setSnack, new View.OnClickListener() {
                        @Override public void onClick(View view)
                        { taskDueDateOnClickBehavior(); } })
                    .show();
            return false;
        } else if(!setTime) {
            Snackbar.make(rootLayout, timeNotSet, snackBarTimeout)
                    .setAction(setSnack, new View.OnClickListener() {
                        @Override public void onClick(View view)
                        { taskDueTimeOnClickBehavior(); } })
                    .show();
            return false;
        } else if(dueCalendar.before(now)) {
            DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getBaseContext());
            DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(getBaseContext());
            String timeString = dateFormat.format(dueCalendar.getTime())
                    + " " + timeFormat.format(dueCalendar.getTime()) + " ";
            Snackbar.make(rootLayout, timeString + hasPassed, snackBarTimeout)
                    .setAction(setSnack, new View.OnClickListener() {
                        @Override public void onClick(View view)
                        { taskDueDateOnClickBehavior(); } })
                    .show();
            return false;
        }
        return true;
    }

    //////////////// SELECT DATE ////////////////
    // Select date
    // This method pop up a date selector, and allow user to select date.
    // The selected date will be stored in the dueCalendar object.
    @OnFocusChange(R.id.task_due_date_content)
    void taskDueDateOnFocusBehavior(View view, boolean hasFocus) {
        if(hasFocus) {
            taskDueDateOnClickBehavior();
        }
    }
    @OnClick(R.id.task_due_date_content)
    void taskDueDateOnClickBehavior() {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dueCalendar.set(Calendar.YEAR, year);
                dueCalendar.set(Calendar.MONTH, monthOfYear);
                dueCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                DateFormat sdf = android.text.format.DateFormat.getDateFormat(getBaseContext());
                dueDateContent.setText(sdf.format(dueCalendar.getTime()));
                setDate = true;
            }
        };
        new DatePickerDialog(this, date,
                dueCalendar.get(Calendar.YEAR),
                dueCalendar.get(Calendar.MONTH),
                dueCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    //////////////// SELECT TIME ////////////////
    // Select time
    // This method pop up a time selector, and allow user to select time.
    // The selected time will be stored in the dueCalendar object.
    @OnFocusChange(R.id.task_due_time_content)
    void taskDueTimeOnFocusBehavior(View view, boolean hasFocus) {
        if(hasFocus) {
            taskDueTimeOnClickBehavior();
        }
    }
    @OnClick(R.id.task_due_time_content)
    void taskDueTimeOnClickBehavior() {
        TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minuteOfHour) {
                dueCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                dueCalendar.set(Calendar.MINUTE, minuteOfHour);
                DateFormat sdf = android.text.format.DateFormat.getTimeFormat(getBaseContext());
                dueTimeContent.setText(sdf.format(dueCalendar.getTime()));
                setTime = true;
            }
        };
        new TimePickerDialog(this, time,
                dueCalendar.get(Calendar.HOUR_OF_DAY),
                dueCalendar.get(Calendar.MINUTE), true).show();
    }
}
