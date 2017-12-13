package io.github.celestialphineas.sanxing.UIStatistics;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.celestialphineas.sanxing.R;

public class TimelineActivity extends AppCompatActivity {
    @BindView(R.id.timeline_toolbar)            Toolbar toolbar;
    @BindView(R.id.timeline_recycler_view)      RecyclerView timelineRecyclerView;
    @BindView(R.id.timeline_place_holder)       ViewGroup placeHolder;
    static final int TASK = 0, HABIT = 1, TIME_LEFT = 2;
    List<TimelineItem> timelineItems = new ArrayList<>();

    class TimelineItem {
        int type; Calendar calendar; String description;
        TimelineItem(int type, Calendar calendar, String description) {
            this.type = type; this.calendar = calendar; this.description = description;
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
                case TIME_LEFT: default:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        return getBaseContext().getResources().getColor(R.color.colorTimeLeft, null);
                    } else return getBaseContext().getResources().getColor(R.color.colorTimeLeft);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        // TODO:
        // Constructor: Type, Time Calendar, Description
        timelineItems.add(new TimelineItem(TASK, Calendar.getInstance(), "Hello world"));
        timelineItems.add(new TimelineItem(HABIT, Calendar.getInstance(), "Blablabla"));
        timelineItems.add(new TimelineItem(TIME_LEFT, Calendar.getInstance(), "Ahahahahaaaa"));
        timelineItems.add(new TimelineItem(TASK, Calendar.getInstance(), "Hello world"));
        timelineItems.add(new TimelineItem(HABIT, Calendar.getInstance(), "Blablabla"));
        timelineItems.add(new TimelineItem(TIME_LEFT, Calendar.getInstance(), "Ahahahahaaaa"));
        timelineItems.add(new TimelineItem(TASK, Calendar.getInstance(), "Hello world"));
        timelineItems.add(new TimelineItem(HABIT, Calendar.getInstance(), "Blablabla"));
        timelineItems.add(new TimelineItem(TIME_LEFT, Calendar.getInstance(), "Ahahahahaaaa"));
        // End of TODO

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if(timelineItems == null || timelineItems.isEmpty()) {
            placeHolder.setVisibility(View.VISIBLE);
            timelineRecyclerView.setVisibility(View.GONE);
        } else {
            timelineRecyclerView.setLayoutManager(layoutManager);
            timelineRecyclerView.setAdapter(new TimelineAdapter(timelineItems));
        }
    }
}