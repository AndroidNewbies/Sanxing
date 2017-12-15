package io.github.celestialphineas.sanxing.UIStatistics;

import android.os.Build;
import android.os.Bundle;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.celestialphineas.sanxing.MyApplication;
import io.github.celestialphineas.sanxing.R;
import io.github.celestialphineas.sanxing.sxObject.Habit;
import io.github.celestialphineas.sanxing.sxObject.Task;
import io.github.celestialphineas.sanxing.sxObject.TimeLeft;
import io.github.celestialphineas.sanxing.sxObjectManager.HabitManager;
import io.github.celestialphineas.sanxing.sxObjectManager.TaskManager;
import io.github.celestialphineas.sanxing.sxObjectManager.TimeLeftManager;

//import com.konifar.fab_transformation.FabTransformation;

public class StatisticsActivity extends AppCompatActivity {
    @BindView(R.id.statistics_toolbar)              Toolbar toolbar;
    @BindView(R.id.statistics_root_linear_layout)   ViewGroup rootLinearLayout;
    @BindView(R.id.statistics_total_relative_layout)ViewGroup totalRelativeLayout;
    @BindView(R.id.total_grid)                      ViewGroup totalGrid;
    @BindView(R.id.statistics_place_holder)         ViewGroup placeHolder;
    @BindView(R.id.achievement_card)                ViewGroup achievementCard;
    @BindView(R.id.total_description)               AppCompatTextView totalDescriptionView;
    @BindView(R.id.task_count)                      AppCompatTextView taskCountView;
    @BindView(R.id.habit_count)                     AppCompatTextView habitCountView;
    @BindView(R.id.time_left_count)                 AppCompatTextView timeLeftCountView;
    @BindView(R.id.achievement_list)                ListView achievementListView;
    @BindString(R.string.statistics_total_description)      String currentTotalString;
    @BindString(R.string.statistics_finished_description)   String finishedTotalString;
    int nTasks = 0, nHabits = 0, nTimeLefts = 0;
    int nFinishedTasks = 0, nFinishedHabits = 0, nFinishedTimeLefts = 0;
    boolean showCurrent = true;
    static final int TASK = 0, HABIT = 1, TIME_LEFT = 2;
    static final int BRONZE = 0, SILVER = 1, GOLD = 2;
    List<Achievement> achievements = new ArrayList<>();
    //backend
    private MyApplication myApplication;
    private TaskManager taskManager;
    private HabitManager habitManager;
    private TimeLeftManager timeLeftManager;
    //
    // Achievement
    class Achievement {
        int type, prize; String title, description;
        Achievement(int type, int prize, String title, String description) {
            this.type = type; this.prize = prize; this.title = title; this.description = description;
        }
        int getBandColor() {
            switch (type) {
                case TASK:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        return getBaseContext().getResources().getColor(R.color.colorTasks, null);
                    } else return getBaseContext().getResources().getColor(R.color.colorTasks);
                case HABIT:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        return getBaseContext().getResources().getColor(R.color.colorHabits, null);
                    } else return getBaseContext().getResources().getColor(R.color.colorHabits);
                case TIME_LEFT: default:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        return getBaseContext().getResources().getColor(R.color.colorTimeLeft, null);
                    } else return getBaseContext().getResources().getColor(R.color.colorTimeLeft);
            }
        }
        int getMedalBrightColor() {
            switch (prize) {
                case BRONZE:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        return getBaseContext().getResources().getColor(R.color.bronze_bright, null);
                    } else return getBaseContext().getResources().getColor(R.color.bronze_bright);
                case SILVER:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        return getBaseContext().getResources().getColor(R.color.silver_bright, null);
                    } else return getBaseContext().getResources().getColor(R.color.silver_bright);
                case GOLD: default:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        return getBaseContext().getResources().getColor(R.color.gold_bright, null);
                    } else return getBaseContext().getResources().getColor(R.color.gold_bright);
            }
        }
        int getMedalDarkColor() {
            switch (prize) {
                case BRONZE:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        return getBaseContext().getResources().getColor(R.color.bronze_dark, null);
                    } else return getBaseContext().getResources().getColor(R.color.bronze_dark);
                case SILVER:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        return getBaseContext().getResources().getColor(R.color.silver_dark, null);
                    } else return getBaseContext().getResources().getColor(R.color.silver_dark);
                case GOLD: default:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        return getBaseContext().getResources().getColor(R.color.gold_dark, null);
                    } else return getBaseContext().getResources().getColor(R.color.gold_dark);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        ButterKnife.bind(this);
        myApplication=(MyApplication)getApplication();
        taskManager=myApplication.get_task_manager();
        habitManager=myApplication.get_habit_manager();
        timeLeftManager=myApplication.get_time_left_manager();
        //////// Toolbar ////////
        // Set the toolbar as the default action bar of the window
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.statistics_and_achievements));
        // Enable the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // TODO:
        // Set the number of items
        nTasks=taskManager.getNumberOfTasks();
        nHabits=habitManager.getNumberOfHabits();
        nTimeLefts=timeLeftManager.getNumberOfTimeLefts();
        nFinishedTasks=taskManager.getNumberOfFinishedTasks();
        nFinishedHabits=habitManager.getNumberOfFinishedHabits();
        nFinishedTimeLefts=timeLeftManager.getNumberOfFinishedTimeLefts();
        // Set up achievements
        achievements.add(new Achievement(HABIT, GOLD, "Hello", "world, blablabla"));
        achievements.add(new Achievement(TASK, BRONZE, "Task bla", "You've won a lot"));
        achievements.add(new Achievement(TIME_LEFT, SILVER, "What?", "What the heck!!!"));
        // End of TODO

        //////// ListAdapter ////////
        if(achievements == null || achievements.isEmpty()) {
            placeHolder.setVisibility(View.VISIBLE);
            achievementCard.setVisibility(View.GONE);
        } else {
            achievementListView.setAdapter(new AchievementsAdapter(this, achievements));
        }
        showCurrentNumbers();
    }

    @OnClick(R.id.toggle_summary_button)//切换按钮
    void toggleSummaryButtonOnClickBehavior() {
        TransitionManager.beginDelayedTransition(rootLinearLayout);
        TransitionManager.beginDelayedTransition(totalRelativeLayout);
        TransitionManager.beginDelayedTransition(totalGrid);
        if(!showCurrent) {
            showCurrentNumbers();
            showCurrent = true;
        } else {
            showFinishedNumbers();
            showCurrent = false;
        }
    }

    private void showCurrentNumbers() {
        totalDescriptionView.setText(currentTotalString);
        taskCountView.setText(Integer.toString(nTasks));
        habitCountView.setText(Integer.toString(nHabits));
        timeLeftCountView.setText(Integer.toString(nTimeLefts));
    }
    private void showFinishedNumbers() {
        totalDescriptionView.setText(finishedTotalString);
        taskCountView.setText(Integer.toString(nFinishedTasks));
        habitCountView.setText(Integer.toString(nFinishedHabits));
        timeLeftCountView.setText(Integer.toString(nFinishedTimeLefts));
    }
}
