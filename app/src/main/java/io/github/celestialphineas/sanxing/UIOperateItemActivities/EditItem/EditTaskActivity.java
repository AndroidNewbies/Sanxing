package io.github.celestialphineas.sanxing.UIOperateItemActivities.EditItem;

import android.os.Bundle;
import android.view.MenuItem;

import java.text.DateFormat;
import java.util.Calendar;

import io.github.celestialphineas.sanxing.R;
import io.github.celestialphineas.sanxing.UIOperateItemActivities.Base.OperateTaskActivityBase;

public class EditTaskActivity extends OperateTaskActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        title = getString(R.string.edit_task);

        // TODO: Handle the intent
        // TODO: Change the lines below to synchronize data of the view and that of the model
        // You may modify the lines below to set the activity's UI state
        // Title
        String taskTitle = "Hello world!";
        // Change the "2017-12-25 14:28 below" to the model's value
        dueCalendar.set(Calendar.YEAR, 2017);
        dueCalendar.set(Calendar.MONTH, 12);
        dueCalendar.set(Calendar.DAY_OF_MONTH, 25);
        dueCalendar.set(Calendar.HOUR_OF_DAY, 14);
        dueCalendar.set(Calendar.MINUTE, 28);
        // Importance, edit the selectedImportance according to the model
        selectedImportance = 3;
        // Description
        String taskDescription = "To be implemented...";
        // End of the TODO

        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_in_up);
        super.onCreate(savedInstanceState);
        // Sync the view, this does not need to be changed
        setDate = true; setTime = true;
        inputTitle.setText(taskTitle);
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getBaseContext());
        DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(getBaseContext());
        dueDateContent.setText(dateFormat.format(dueCalendar.getTime()));
        dueTimeContent.setText(timeFormat.format(dueCalendar.getTime()));
        taskImportance.setProgress(selectedImportance);
        descriptionContent.setText(taskDescription);
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
            // TODO: Write back the changes to the database
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
