package io.github.celestialphineas.sanxing.UIOperateItemActivities.EditItem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.MenuItem;

import butterknife.BindView;
import io.github.celestialphineas.sanxing.MyApplication;
import io.github.celestialphineas.sanxing.R;
import io.github.celestialphineas.sanxing.UIOperateItemActivities.Base.OperateHabitActivityBase;
import io.github.celestialphineas.sanxing.sxObject.Habit;

public class EditHabitActivity extends OperateHabitActivityBase {
    private MyApplication myApplication;
    private Habit habit;
    private int position;
    private int pos_habit_manager;//find the position in the manager
    @BindView(R.id.habit_linear_layout)
    LinearLayoutCompat linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        title = getString(R.string.edit_habit);

        // Handle the intent
        myApplication = (MyApplication) getApplication();
        Intent intent = getIntent();
        position = intent.getIntExtra("position", -1);
        habit = myApplication.get_habit_manager().getObjectList().get(position);
        int count = 0;
        pos_habit_manager = 0;
        for (Habit temp : myApplication.get_habit_manager().getObjectList()) {// neglect the finished habit
            pos_habit_manager++;
            if (temp.getState() != 1) continue;
            else {
                if (count == position) {
                    habit = temp;
                    break;
                }
                count++;
            }

        }
        pos_habit_manager--;
        // Change the lines below to synchronize data of the view and that of the model
        // You may modify the lines below to set the activity's UI state
        // Title
        String habitTitle = habit.getTitle();
        // Frequency, edit the selectedFreq according to the model
        selectedFreq = habit.getFrequency();
        // Times checked
        checkTimes = habit.getRecord().size();
        selectedImportance = habit.getImportance();
        // Description
        String habitDescription = habit.getContent();
        // End of the card content set

        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_in_up);
        super.onCreate(savedInstanceState);
        // Sync the view, this does not need to be changed
        inputTitle.setText(habitTitle);
        habitFreqSeekBar.setProgress(selectedFreq);
        habitImportance.setProgress(selectedImportance);
        descriptionContent.setText(habitDescription);
        timesContent.setText(Integer.toString(checkTimes));
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
            //////// INSERT NECESSARY CODE HERE
            // Use "selectedFreq" for frequency index
            // Use "selectedImportance" for the the importance 0~4
            // Use inputTitle.getText().toString() to get the title
            // Use descriptionContent.getText().toString() to get the description
            habit.edit_habit(inputTitle.getText().toString(), "2020-01-01 18:21:00",
                    descriptionContent.getText().toString(), selectedImportance, selectedFreq);
            Intent intent = new Intent();
            intent.putExtra("position", pos_habit_manager);
            intent.putExtra("ID", habit.ID);
            setResult(RESULT_OK, intent);
            animationSubmit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
