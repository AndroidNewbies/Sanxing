package io.github.celestialphineas.sanxing;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get the toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        // Set the toolbar as the default action bar of the window
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);
        // Set drawer layout
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle (
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // Get the view pager
        final ViewPager viewPager = (ViewPager) findViewById(R.id.main_view_pager);
        // Get the tab layout
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.main_tab);
        // Set the adapter
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        // Add view pagers
        adapter.addFrag(new TaskFrag(), getString(R.string.tab_tasks));
        adapter.addFrag(new TaskFrag(), getString(R.string.tab_habits));
        adapter.addFrag(new TaskFrag(), getString(R.string.tab_time_left));
        // Binding the view pager with the layout, as well as the fab button
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(
                    int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                animateFab(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) { }
        });
    }

    private void animateFab(int tabPos) {
        FloatingActionButton fabTasks = (FloatingActionButton) findViewById(R.id.fab_tasks);
        FloatingActionButton fabHabits = (FloatingActionButton) findViewById(R.id.fab_habits);
        FloatingActionButton fabTimeLeft = (FloatingActionButton) findViewById(R.id.fab_time_left);
        switch (tabPos) {
            case 0:
                fabTasks.show();
                fabHabits.hide();
                fabTimeLeft.hide();
                break;
            case 1:
                fabTasks.hide();
                fabHabits.show();
                fabTimeLeft.hide();
                break;
            case 2:
                fabTasks.hide();
                fabHabits.hide();
                fabTimeLeft.show();
                break;
            default:
                fabTasks.hide();
                fabHabits.hide();
                fabTimeLeft.hide();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_calendar) {
            // Intent to navigate to the calendar activity
            Intent navigateCalendarIntent = new Intent(this, CalendarActivity.class);
            startActivity(navigateCalendarIntent);
            // TODO: Pass message to calendar activity through intent here
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Navigation drawer implementation
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // Handle the navigation actions
        if (id == R.id.nav_calendar) {
            // Intent to navigate to the calendar activity
            Intent navigateCalendarIntent = new Intent(this, CalendarActivity.class);
            startActivity(navigateCalendarIntent);
            // TODO: Pass message to calendar activity through intent here
        } else if (id == R.id.nav_statistics) {

        } else if (id == R.id.nav_achievements) {

        } else if (id == R.id.nav_timeline) {

        } else if (id == R.id.nav_help) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_about) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
