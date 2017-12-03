package io.github.celestialphineas.sanxing.UINewItemCreationActivities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.SimpleTimeZone;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import io.github.celestialphineas.sanxing.R;

public class CreateNewTaskActivity extends AppCompatActivity {
    // The selected date/time will be stored here
    final Calendar dueCalendar = Calendar.getInstance();
    // Flags to show if the user has already set date and time
    boolean setDate = false, setTime = false;
    @BindView(R.id.create_new_item_toolbar)         Toolbar toolbar;
    @BindView(R.id.task_due_date_content)           TextInputEditText dueDateContent;
    @BindView(R.id.task_due_time_content)           TextInputEditText dueTimeContent;
    @BindString(R.string.create_new_task)           String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                finish();
            }
        });

        //////// Disallow keyboard events of the date and text editTexts
        dueDateContent.setKeyListener(null);
        dueTimeContent.setKeyListener(null);
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
            // TODO:
            // Verify the input first
            // Then register the changes in the data base
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Select date
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
            }
        };
        new DatePickerDialog(this, date,
                dueCalendar.get(Calendar.YEAR),
                dueCalendar.get(Calendar.MONTH),
                dueCalendar.get(Calendar.DAY_OF_MONTH)).show();
        setDate = true;
    }

    // Select time
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
            }
        };
        new TimePickerDialog(this, time,
                dueCalendar.get(Calendar.HOUR_OF_DAY),
                dueCalendar.get(Calendar.MINUTE), true).show();
        setTime = true;
    }
}
