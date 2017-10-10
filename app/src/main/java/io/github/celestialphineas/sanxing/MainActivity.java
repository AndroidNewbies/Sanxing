package io.github.celestialphineas.sanxing;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        // Get the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        // Set the toolbar as the default action bar of the window
        setSupportActionBar(toolbar);
        // Get the view pager
        ViewPager viewPager = (ViewPager) findViewById(R.id.main_view_pager);
        // Get the tab layout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.main_tab);
        // Set the adapter
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        // Add view pagers
        adapter.addFrag(new CountdownFrag(), getString(R.string.tab_tasks));
        adapter.addFrag(new CountdownFrag(), getString(R.string.tab_habits));
        adapter.addFrag(new CountdownFrag(), getString(R.string.tab_time_left));
        // Binding
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        super.onCreate(savedInstanceState);
    }
}
