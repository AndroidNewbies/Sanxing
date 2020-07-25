package io.github.celestialphineas.sanxing.UIOperateItemActivities.EditItem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.MenuItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import io.github.celestialphineas.sanxing.MyApplication;
import io.github.celestialphineas.sanxing.R;
import io.github.celestialphineas.sanxing.UIOperateItemActivities.Base.OperateTaskActivityBase;
import io.github.celestialphineas.sanxing.sxObject.Task;

public class EditTaskActivity extends OperateTaskActivityBase {
    private MyApplication myApplication;
    private Task task;
    private int position;
    private int pos_task_manager;
    @BindView(R.id.task_linear_layout)
    LinearLayoutCompat linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        title = getString(R.string.edit_task);
        myApplication = (MyApplication) getApplication();
        // Handle the intent
        Intent intent = getIntent();
        position = intent.getIntExtra("position", -1);
        int count = 0;
        pos_task_manager = 0;
        for (Task temp : myApplication.get_task_manager().getObjectList()) {// neglect the finished task
            pos_task_manager++;//find the position in the manager
            if (temp.getState() != 1) continue;
            else {
                if (count == position) {
                    task = temp;
                    break;
                }
                count++;
            }


        }
        pos_task_manager--;


        //  Change the lines below to synchronize data of the view and that of the model
        // You may modify the lines below to set the activity's UI state
        // Title
        String taskTitle = task.getTitle();
        // Change the "2017-12-25 14:28 below" to the model's value
        dueCalendar.set(Calendar.YEAR, task.getEndLocalDate().getYear());
        dueCalendar.set(Calendar.MONTH, task.getEndLocalDate().getMonthValue() - 1);
        dueCalendar.set(Calendar.DAY_OF_MONTH, task.getEndLocalDate().getDayOfMonth());
        dueCalendar.set(Calendar.HOUR_OF_DAY, task.getEndLocalDate().getHour());
        dueCalendar.set(Calendar.MINUTE, task.getEndLocalDate().getMinute());
        // Importance, edit the selectedImportance according to the model
        selectedImportance = task.getImportance();
        // Description
        String taskDescription = task.getContent();
        // End of the task content set

        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_in_up);
        super.onCreate(savedInstanceState);
        // Sync the view, this does not need to be changed
        setDate = true;
        setTime = true;
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
            // The verifyForm() method can be modified if necessary
            if (!verifyForm(linearLayout)) return true;
            // Store the changes in the intent and write back the changes to the database
            // Use "dueCalendar" for due date and time
            // Use "selectedImportance" for the the importance 0~4
            // Use inputTitle.getText().toString() to get the title
            // Use descriptionContent.getText().toString() to get the description
            Log.d("where are you", "itemselect");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            task.edit_task(inputTitle.getText().toString(), sdf.format(dueCalendar.getTime()).substring(0, 16).concat(":00")
                    , descriptionContent.getText().toString(), selectedImportance);

            Intent intent = new Intent();
            intent.putExtra("position", pos_task_manager);
            intent.putExtra("ID", task.ID);
            setResult(RESULT_OK, intent);
            animationSubmit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
