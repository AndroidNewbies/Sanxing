package io.github.celestialphineas.sanxing.UINewItemCreationActivities;

import android.animation.Animator;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.SeekBar;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

import butterknife.BindInt;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import io.github.celestialphineas.sanxing.R;

public class CreateNewTimeLeftActivity extends AppCompatActivity {
    // Selected importance 0, 1, 2, 3, 4
    int selectedImportance = 2;
    // The selected date/time will be stored here
    final Calendar dueCalendar = Calendar.getInstance();
    // Flags to show if the user has already set date and time
    boolean setDate = false, setTime = false;
    @BindView(R.id.create_new_item_toolbar)         Toolbar toolbar;
    @BindView(R.id.input_title)                     TextInputEditText inputTitle;
    @BindView(R.id.time_left_due_date_content)      TextInputEditText dueDateContent;
    @BindView(R.id.time_left_description_content)   TextInputEditText descriptionContent;
    @BindView(R.id.time_left_importance_seek_bar)   AppCompatSeekBar timeLeftImportance;
    @BindView(R.id.time_left_linear_layout)         LinearLayoutCompat linearLayout;
    @BindString(R.string.snack_title_not_set)       String titleNotSet;
    @BindString(R.string.snack_date_not_set)        String dateNotSet;
    @BindString(R.string.snack_time_not_set)        String timeNotSet;
    @BindString(R.string.snack_op_set)              String setSnack;
    @BindString(R.string.create_new_time_left)      String title;
    @BindString(R.string.snack_time_passed)         String hasPassed;
    @BindInt(R.integer.snack_bar_timeout)           int snackBarTimeout;
    @BindInt(R.integer.reveal_time)                 int revealTime;
    // Animation center
    int cx, cy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_time_left);
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

        //////// Disable keyboard events of the date and text editTexts
        dueDateContent.setKeyListener(null);

        timeLeftImportance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                selectedImportance = seekBar.getProgress();
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override public void onStopTrackingTouch(SeekBar seekBar) { }
        });
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
            // Use "dueCalendar" for due date and time
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
        Calendar now = Calendar.getInstance();
        if(inputTitle != null && inputTitle.getText() != null
                && inputTitle.getText().toString().trim().isEmpty()) {
            Snackbar.make(linearLayout, titleNotSet, snackBarTimeout)
                    .show();
            inputTitle.requestFocus();
            return false;
        } else if(!setDate) {
            Snackbar.make(linearLayout, dateNotSet, snackBarTimeout)
                    .setAction(setSnack, new View.OnClickListener() {
                        @Override public void onClick(View view)
                        { timeLeftDueDateOnClickBehavior(); } })
                    .show();
            return false;
        } else if(dueCalendar.before(now)) {
            DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getBaseContext());
            String timeString = dateFormat.format(dueCalendar.getTime()) +  " ";
            Snackbar.make(linearLayout, timeString + hasPassed, snackBarTimeout)
                    .setAction(setSnack, new View.OnClickListener() {
                        @Override public void onClick(View view)
                        { timeLeftDueDateOnClickBehavior(); } })
                    .show();
            return false;
        }
        return true;
    }

    //////////////// SELECT DATE ////////////////
    // Select date
    // This method pop up a date selector, and allow user to select date.
    // The selected date will be stored in the dueCalendar object.
    @OnFocusChange(R.id.time_left_due_date_content)
    void timeLeftDueDateOnFocusBehavior(View view, boolean hasFocus) {
        if(hasFocus) {
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
                setDate = true;
            }
        };
        new DatePickerDialog(this, date,
                dueCalendar.get(Calendar.YEAR),
                dueCalendar.get(Calendar.MONTH),
                dueCalendar.get(Calendar.DAY_OF_MONTH)).show();
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
