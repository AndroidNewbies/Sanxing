package io.github.celestialphineas.sanxing.UIOperateItemActivities.EditItem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.MenuItem;

import org.threeten.bp.LocalDateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.celestialphineas.sanxing.R;
import io.github.celestialphineas.sanxing.UIOperateItemActivities.Base.OperateTaskActivityBase;
import io.github.celestialphineas.sanxing.timer.MyDuration;

public class EditTaskActivity extends OperateTaskActivityBase {

    @BindView(R.id.task_linear_layout)      LinearLayoutCompat linearLayout;
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
            if(!verifyForm(linearLayout)) return true;
            ////////
            // TODO: Write back the changes to the database
            //////// INSERT NECESSARY CODE HERE
            // Use "dueCalendar" for due date and time
            // Use "selectedImportance" for the the importance 0~4
            // Use inputTitle.getText().toString() to get the title
            // Use descriptionContent.getText().toString() to get the description

            // finish
            String begintime = LocalDateTime.now().toString().replace('T',' ').substring(0,19);
            SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String endtime = s.format(dueCalendar.getTime());
            Long dif = MyDuration.durationFromAtoB(begintime,endtime);
            String diftime = s.format(dif);
            Intent intent = new Intent();
            intent.putExtra("task_title",inputTitle.getText().toString());
            intent.putExtra("task_importance",selectedImportance);
            intent.putExtra("task_begin_time",begintime);
            intent.putExtra("task_end_time",endtime);
            intent.putExtra("task_description",descriptionContent.getText().toString());
            animationSubmit();
            setResult(Activity.RESULT_OK, intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
