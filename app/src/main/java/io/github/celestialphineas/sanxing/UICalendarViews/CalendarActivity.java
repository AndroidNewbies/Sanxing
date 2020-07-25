package io.github.celestialphineas.sanxing.UICalendarViews;

import android.os.Bundle;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;
import io.github.celestialphineas.sanxing.R;

//import com.konifar.fab_transformation.FabTransformation;

public class CalendarActivity extends AppCompatActivity {
    @BindView(R.id.calendar_toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_spinner)
    AppCompatSpinner spinner;
    //    @BindView(R.id.fab)                         FloatingActionButton fab;
//    @BindView(R.id.calendar_bottom_navigation)  BottomNavigationView bottomNav;
    @BindView(R.id.calendar_view_linear_layout)
    LinearLayoutCompat calendarViewLinearLayout;
    @BindView(R.id.task_calendar_fragment)
    View taskCalendarFrag;
    @BindView(R.id.habit_calendar_fragment)
    View habitCalendarFrag;
    @BindView(R.id.time_left_calendar_fragment)
    View timeLeftCalendarFrag;
    private int tabPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Object receivedObject = getIntent().getSerializableExtra("tab");
        if (receivedObject != null) tabPosition = (int) receivedObject;
        setContentView(R.layout.activity_calendar);
        ButterKnife.bind(this);

        //////// Toolbar ////////
        // Set the toolbar as the default action bar of the window
        setSupportActionBar(toolbar);
        // Disable the title
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Enable the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //////// Spinner ////////
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.calendar_spinner_array,
                R.layout.calendar_spinner);
        adapter.setDropDownViewResource(R.layout.spinner_items);
        spinner.setAdapter(adapter);
        spinner.setSelection(tabPosition);


        habitCalendarFrag.setVisibility(View.GONE);
        taskCalendarFrag.setVisibility(View.GONE);
        timeLeftCalendarFrag.setVisibility(View.GONE);
    }

    @OnItemSelected(R.id.toolbar_spinner)
    void spinnerSelectedBehavior(AdapterView<?> parent, View view, int position, long id) {
        TransitionManager.beginDelayedTransition(calendarViewLinearLayout);
        switch (position) {
            case 0:
                taskCalendarFrag.setVisibility(View.VISIBLE);
                habitCalendarFrag.setVisibility(View.GONE);
                timeLeftCalendarFrag.setVisibility(View.GONE);
                break;
            case 1:
                habitCalendarFrag.setVisibility(View.VISIBLE);
                taskCalendarFrag.setVisibility(View.GONE);
                timeLeftCalendarFrag.setVisibility(View.GONE);
                break;
            case 2:
            default:
                timeLeftCalendarFrag.setVisibility(View.VISIBLE);
                taskCalendarFrag.setVisibility(View.GONE);
                habitCalendarFrag.setVisibility(View.GONE);
                break;
        }
    }

//    @OnClick(R.id.fab)
//    public void calendarFabOnClickBehavior() {
//        FabTransformation.with(fab).transformTo(bottomNav);
//        // Automatic close after a few seconds
//        TimerTask closeBottomNav = new TimerTask() {
//            @Override
//            public void run() {
//                try {
//                    if(fab.getVisibility() != View.VISIBLE)
//                        FabTransformation.with(fab).transformFrom(bottomNav);
//                } catch(Exception e) {
//                    // Do nothing
//                }
//            }
//        };
//        Timer timer = new Timer();
//        // Navigation toggle timeout
//        timer.schedule(closeBottomNav, 5000);
//    }
}
