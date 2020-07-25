package io.github.celestialphineas.sanxing.UIOperateItemActivities.EditItem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Menu;
import android.view.MenuItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import io.github.celestialphineas.sanxing.MyApplication;
import io.github.celestialphineas.sanxing.R;
import io.github.celestialphineas.sanxing.UIOperateItemActivities.Base.OperateTimeLeftActivityBase;
import io.github.celestialphineas.sanxing.sxObject.TimeLeft;

public class EditTimeLeftActivity extends OperateTimeLeftActivityBase {
    private MyApplication myApplication;
    private TimeLeft timeLeft;
    private int position;
    private int pos_timeleft_manager;
    @BindView(R.id.time_left_linear_layout)
    LinearLayoutCompat linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        title = getString(R.string.edit_time_left);

        myApplication = (MyApplication) getApplication();
        //Handle the intent
        Intent intent = getIntent();
        position = intent.getIntExtra("position", -1);
        int count = 0;
        pos_timeleft_manager = 0;
        for (TimeLeft temp : myApplication.get_time_left_manager().getObjectList()) {// neglect the finished time left item
            pos_timeleft_manager++;//find the position in the manager
            if (temp.getState() != 1) continue;
            else {
                if (count == position) {
                    timeLeft = temp;
                    break;
                }
                count++;
            }

        }
        pos_timeleft_manager--;
        timeLeft = myApplication.get_time_left_manager().getObjectList().get(position);

        // Change the lines below to synchronize data of the view and that of the model
        // You may modify the lines below to set the activity's UI state
        // Title
        String timeLeftTitle = timeLeft.getTitle();
        // Change the "2017-12-25 14:28 below" to the model's value
        startCalendar.set(Calendar.YEAR, timeLeft.getBeginLocalDate().getYear());
        startCalendar.set(Calendar.MONTH, timeLeft.getBeginLocalDate().getMonthValue() - 1);
        startCalendar.set(Calendar.DAY_OF_MONTH, timeLeft.getBeginLocalDate().getDayOfMonth());
        startCalendar.set(Calendar.HOUR_OF_DAY, timeLeft.getBeginLocalDate().getHour());
        startCalendar.set(Calendar.MINUTE, timeLeft.getBeginLocalDate().getMinute());
        // Change the "2017-12-25 14:28 below" to the model's value
        dueCalendar.set(Calendar.YEAR, timeLeft.getEndLocalDate().getYear());
        dueCalendar.set(Calendar.MONTH, timeLeft.getEndLocalDate().getMonthValue() - 1);
        dueCalendar.set(Calendar.DAY_OF_MONTH, timeLeft.getEndLocalDate().getDayOfMonth());
        dueCalendar.set(Calendar.HOUR_OF_DAY, timeLeft.getEndLocalDate().getHour());
        dueCalendar.set(Calendar.MINUTE, timeLeft.getEndLocalDate().getMinute());
        // Importance, edit the selectedImportance according to the model
        selectedImportance = timeLeft.getImportance();
        // Description
        String timeLeftDescription = timeLeft.getContent();
        // End of the timeleft set

        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_in_up);
        super.onCreate(savedInstanceState);
        // Sync the view, this does not need to be changed
        setDate1 = true;
        setDate2 = true;
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
            // The verifyForm() method can be modified if necessary
            if (!verifyForm(linearLayout)) return true;
            ////////
            // Then register the changes in the database
            // Use "dueCalendar" for due date and time
            // Use "selectedImportance" for the the importance 0~4
            // Use inputTitle.getText().toString() to get the title
            // Use descriptionContent.getText().toString() to get the description
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            timeLeft.create_timeleft(inputTitle.getText().toString(), sdf.format(startCalendar.getTime()).substring(0, 16).concat(":00"),
                    sdf.format(dueCalendar.getTime()).substring(0, 16).concat(":00"), descriptionContent.getText().toString(),
                    selectedImportance);
            Intent intent = new Intent();
            intent.putExtra("position", pos_timeleft_manager);
            intent.putExtra("ID", timeLeft.ID);
            setResult(RESULT_OK, intent);
            animationSubmit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
