package io.github.celestialphineas.sanxing;

import java.util.Timer;

import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.konifar.fab_transformation.FabTransformation;

import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CalendarActivity extends AppCompatActivity {
    @BindView(R.id.calendar_toolbar)            Toolbar toolbar;
    @BindView(R.id.toolbar_spinner)             AppCompatSpinner spinner;
    @BindView(R.id.fab)                         FloatingActionButton fab;
    @BindView(R.id.calendar_bottom_navigation)  BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }

    @OnClick(R.id.fab)
    void calendarFabOnClickBehavior() {
        FabTransformation.with(fab).transformTo(bottomNav);
        // Automatic close after a few seconds
        TimerTask closeBottomNav = new TimerTask() {
            @Override
            public void run() {
                try {
                    if(fab.getVisibility() != View.VISIBLE)
                        FabTransformation.with(fab).transformFrom(bottomNav);
                } catch(Exception e) {
                    // Do nothing
                }
            }
        };
        Timer timer = new Timer();
        // Navigation toggle timeout
        timer.schedule(closeBottomNav, 5000);
    }
}
