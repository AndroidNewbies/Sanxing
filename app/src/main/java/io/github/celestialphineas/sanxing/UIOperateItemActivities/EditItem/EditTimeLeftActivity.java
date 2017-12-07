package io.github.celestialphineas.sanxing.UIOperateItemActivities.EditItem;

import android.animation.Animator;
import android.app.DatePickerDialog;
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

import java.text.DateFormat;
import java.util.Calendar;

import butterknife.BindInt;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import io.github.celestialphineas.sanxing.R;
import io.github.celestialphineas.sanxing.UIOperateItemActivities.Base.OperateItemActivityBase;
import io.github.celestialphineas.sanxing.UIOperateItemActivities.Base.OperateTimeLeftActivityBase;

public class EditTimeLeftActivity extends OperateTimeLeftActivityBase {

    @BindView(R.id.time_left_linear_layout)     LinearLayoutCompat linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        title = getString(R.string.edit_time_left);

        // TODO: Handle the intent
        // TODO: Change the lines below to synchronize data of the view and that of the model
        // You may modify the lines below to set the activity's UI state
        // Title
        String timeLeftTitle = "Hello world!";
        // Change the "2017-12-25 14:28 below" to the model's value
        startCalendar.set(Calendar.YEAR, 2017);
        startCalendar.set(Calendar.MONTH, Calendar.DECEMBER);
        startCalendar.set(Calendar.DAY_OF_MONTH, 23);
        startCalendar.set(Calendar.HOUR_OF_DAY, 14);
        startCalendar.set(Calendar.MINUTE, 28);
        // Change the "2017-12-25 14:28 below" to the model's value
        dueCalendar.set(Calendar.YEAR, 2017);
        dueCalendar.set(Calendar.MONTH, Calendar.DECEMBER);
        dueCalendar.set(Calendar.DAY_OF_MONTH, 25);
        dueCalendar.set(Calendar.HOUR_OF_DAY, 14);
        dueCalendar.set(Calendar.MINUTE, 28);
        // Importance, edit the selectedImportance according to the model
        selectedImportance = 3;
        // Description
        String timeLeftDescription = "To be implemented...";
        // End of the TODO

        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_in_up);
        super.onCreate(savedInstanceState);
        // Sync the view, this does not need to be changed
        setDate1 = true; setDate2 = true;
        inputTitle.setText(timeLeftTitle);
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getBaseContext());
        startDateContent.setText(dateFormat.format(startCalendar.getTime()));
        dueDateContent.setText(dateFormat.format(dueCalendar.getTime()));
        timeLeftImportance.setProgress(selectedImportance);
        descriptionContent.setText(timeLeftDescription);
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
            if(!verifyForm(linearLayout)) return true;
            ////////
            // TODO: Then register the changes in the database
            //////// INSERT NECESSARY CODE HERE
            // Use "dueCalendar" for due date and time
            // Use "selectedImportance" for the the importance 0~4
            // Use inputTitle.getText().toString() to get the title
            // Use descriptionContent.getText().toString() to get the description

            // finish
            animationSubmit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
