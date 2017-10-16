package io.github.celestialphineas.sanxing;

import android.app.ActionBar;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.konifar.fab_transformation.FabTransformation;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        // Get the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.calendar_toolbar);
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
        // Spinner
        AppCompatSpinner spinner = (AppCompatSpinner) findViewById(R.id.toolbar_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.calendar_spinner_array,
                R.layout.calendar_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final Toolbar toolbarFooter = (Toolbar) findViewById(R.id.toolbar_footer);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FabTransformation.with(fab).transformTo(toolbarFooter);
            }
        });
    }
}
