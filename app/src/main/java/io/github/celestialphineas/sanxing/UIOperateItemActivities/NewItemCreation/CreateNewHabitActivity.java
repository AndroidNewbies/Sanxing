package io.github.celestialphineas.sanxing.UIOperateItemActivities.NewItemCreation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.MenuItem;

import org.threeten.bp.LocalDateTime;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import io.github.celestialphineas.sanxing.MyApplication;
import io.github.celestialphineas.sanxing.R;
import io.github.celestialphineas.sanxing.UIOperateItemActivities.Base.OperateHabitActivityBase;
import io.github.celestialphineas.sanxing.sxObject.Habit;

public class CreateNewHabitActivity extends OperateHabitActivityBase {
    private MyApplication myApplication;
    private Habit habit;
    @BindView(R.id.habit_linear_layout)
    LinearLayoutCompat linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        title = getString(R.string.create_new_habit);

        setContentView(R.layout.activity_create_new_habit);

        //////// Animation ////////
        overridePendingTransition(R.anim.none, R.anim.none);
        animationReveal(savedInstanceState);
        myApplication = (MyApplication) getApplication();
        habit = new Habit();
        super.onCreate(savedInstanceState);
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
            //Then register the changes in the database
            // Use "selectedFreq" for frequency index
            // Use "selectedImportance" for the the importance 0~4
            // Use inputTitle.getText().toString() to get the title
            // Use descriptionContent.getText().toString() to get the description
            LocalDateTime now = LocalDateTime.now();
            String s = now.toString().replace('T', ' ');
            if (s.length() == 16) s = s.concat(":00");
            if (s.length() > 19) s = s.substring(0, 19);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            habit.create_habit(inputTitle.getText().toString(), s, "2020-01-01 18:21:00",
                    descriptionContent.getText().toString(), selectedImportance, selectedFreq);
            myApplication.get_habit_manager().addObject(habit);
            // finish
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            animationSubmit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
