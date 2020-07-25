package io.github.celestialphineas.sanxing.UIStatistics;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZoneOffset;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.celestialphineas.sanxing.MyApplication;
import io.github.celestialphineas.sanxing.R;
import io.github.celestialphineas.sanxing.sxObject.Habit;
import io.github.celestialphineas.sanxing.sxObject.Task;
import io.github.celestialphineas.sanxing.sxObject.TimeLeft;
import io.github.celestialphineas.sanxing.sxObjectManager.HabitManager;
import io.github.celestialphineas.sanxing.sxObjectManager.TaskManager;
import io.github.celestialphineas.sanxing.sxObjectManager.TimeLeftManager;

public class TimelineActivity extends AppCompatActivity {
    @BindView(R.id.timeline_toolbar)
    Toolbar toolbar;
    @BindView(R.id.timeline_recycler_view)
    RecyclerView timelineRecyclerView;
    @BindView(R.id.timeline_place_holder)
    ViewGroup placeHolder;
    @BindString(R.string.time_out)
    String timeout;
    static final int TASK = 0, HABIT = 1, TIME_LEFT = 2;
    List<TimelineItem> timelineItems = new ArrayList<>();

    private MyApplication myApplication;
    private TaskManager mTaskManager;
    private HabitManager mHabitManager;
    private TimeLeftManager mTimeLeftManager;

    class TimelineItem implements Comparable<TimelineItem> {
        int type;
        Calendar calendar;
        String description;

        TimelineItem(int type, Calendar calendar, String description) {
            this.type = type;
            this.calendar = calendar;
            this.description = description;
        }

        int getColor() {
            switch (type) {
                case TASK:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        return getBaseContext().getResources().getColor(R.color.colorTasks, null);
                    } else return getBaseContext().getResources().getColor(R.color.colorTasks);
                case HABIT:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        return getBaseContext().getResources().getColor(R.color.colorHabits, null);
                    } else return getBaseContext().getResources().getColor(R.color.colorHabits);
                case TIME_LEFT:
                default:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        return getBaseContext().getResources().getColor(R.color.colorTimeLeft, null);
                    } else return getBaseContext().getResources().getColor(R.color.colorTimeLeft);
            }
        }

        @Override
        public int compareTo(@NonNull TimelineItem o) {
            if (this.calendar.getTimeInMillis() < o.calendar.getTimeInMillis()) {
                return -1;
            } else if (this.calendar.getTimeInMillis() == o.calendar.getTimeInMillis()) {
                return this.description.compareTo(o.description);
            } else return 1;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        myApplication = (MyApplication) getApplication();
        mTaskManager = myApplication.get_task_manager();
        mHabitManager = myApplication.get_habit_manager();
        mTimeLeftManager = myApplication.get_time_left_manager();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        ButterKnife.bind(this);

        //////// Toolbar ////////
        // Set the toolbar as the default action bar of the window
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.timeline));
        // Enable the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Constructor: Type, Time Calendar, Description

        for (Task task : mTaskManager.getObjectList()) {
            if (task.getState() == 2 || task.getState() == 0) {//task is finished or deleted ,no show
                continue;
            }
            long millionSeconds = 0;
            ZoneOffset zoneoffset = OffsetDateTime.now(ZoneId.systemDefault()).getOffset();
            millionSeconds = task.getEndLocalDate().toEpochSecond(zoneoffset) * 1000;
            Calendar ca = Calendar.getInstance();
            ca.setTimeInMillis(millionSeconds);
            if (millionSeconds < LocalDateTime.now().toEpochSecond(zoneoffset) * 1000) {// if has exceeded the deadline

                timelineItems.add(new TimelineItem(TASK, ca, task.getTitle() + timeout));
            } else timelineItems.add(new TimelineItem(TASK, ca, task.getTitle()));
        }
        for (Habit habit : mHabitManager.getObjectList()) {
            if (habit.getState() == 2 || habit.getState() == 0) {//task is finished or deleted ,no show
                continue;
            }
            long millionSeconds = 0;
            ZoneOffset zoneoffset = OffsetDateTime.now(ZoneId.systemDefault()).getOffset();
            millionSeconds = habit.getLocalNextddl().toEpochSecond(zoneoffset) * 1000;
            Calendar ca = Calendar.getInstance();
            ca.setTimeInMillis(millionSeconds);
            //for nextddl is always set to be after now, time out shouldn't happen
            timelineItems.add(new TimelineItem(HABIT, ca, habit.getTitle()));
        }
        for (TimeLeft timeLeft : mTimeLeftManager.getObjectList()) {
            if (timeLeft.getState() == 2 || timeLeft.getState() == 0) {//task is finished or deleted ,no show
                continue;
            }
            long millionSeconds = 0;
            ZoneOffset zoneoffset = OffsetDateTime.now(ZoneId.systemDefault()).getOffset();
            millionSeconds = timeLeft.getEndLocalDate().toEpochSecond(zoneoffset) * 1000;
            Calendar ca = Calendar.getInstance();
            ca.setTimeInMillis(millionSeconds);
            if (millionSeconds < LocalDateTime.now().toEpochSecond(zoneoffset) * 1000) {// if has exceeded the deadline
                timelineItems.add(new TimelineItem(TIME_LEFT, ca, timeLeft.getTitle() + timeout));
            } else timelineItems.add(new TimelineItem(TIME_LEFT, ca, timeLeft.getTitle()));
        }

        Collections.sort(timelineItems);
//        timelineItems.add(new TimelineItem(TASK, Calendar.getInstance(), "Hello world"));
//        timelineItems.add(new TimelineItem(HABIT, Calendar.getInstance(), "Blablabla"));
//        timelineItems.add(new TimelineItem(TIME_LEFT, Calendar.getInstance(), "Ahahahahaaaa"));
//        timelineItems.add(new TimelineItem(TASK, Calendar.getInstance(), "Hello world"));
//        timelineItems.add(new TimelineItem(HABIT, Calendar.getInstance(), "Blablabla"));
//        timelineItems.add(new TimelineItem(TIME_LEFT, Calendar.getInstance(), "Ahahahahaaaa"));
//        timelineItems.add(new TimelineItem(TASK, Calendar.getInstance(), "Hello world"));
//        timelineItems.add(new TimelineItem(HABIT, Calendar.getInstance(), "Blablabla"));
//        timelineItems.add(new TimelineItem(TIME_LEFT, Calendar.getInstance(), "Ahahahahaaaa"));
        // End

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if (timelineItems == null || timelineItems.isEmpty()) {
            placeHolder.setVisibility(View.VISIBLE);
            timelineRecyclerView.setVisibility(View.GONE);
        } else {
            timelineRecyclerView.setLayoutManager(layoutManager);
            timelineRecyclerView.setAdapter(new TimelineAdapter(timelineItems));
        }
    }
}