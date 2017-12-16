package io.github.celestialphineas.sanxing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.celestialphineas.sanxing.UICalendarViews.CalendarActivity;

import java.util.Timer;

import io.github.celestialphineas.sanxing.SanxingBackend.HabitRepo;
import io.github.celestialphineas.sanxing.SanxingBackend.TimeLeftRepo;
import io.github.celestialphineas.sanxing.UIStatistics.StatisticsActivity;
import io.github.celestialphineas.sanxing.UIStatistics.TimelineActivity;
import io.github.celestialphineas.sanxing.UISupportActivities.AboutActivity;
import io.github.celestialphineas.sanxing.UISupportActivities.IntroActivity;
import io.github.celestialphineas.sanxing.UISupportActivities.SettingsActivity;
import io.github.celestialphineas.sanxing.sxObject.Habit;
import io.github.celestialphineas.sanxing.sxObject.Task;
import io.github.celestialphineas.sanxing.SanxingBackend.TaskRepo;
import io.github.celestialphineas.sanxing.sxObject.TimeLeft;
import io.github.celestialphineas.sanxing.UIHomeTabFragments.HabitFrag;
import io.github.celestialphineas.sanxing.sxObjectManager.HabitManager;
import io.github.celestialphineas.sanxing.UIHomeTabFragments.TaskFrag;
import io.github.celestialphineas.sanxing.sxObjectManager.TaskManager;
import io.github.celestialphineas.sanxing.UIHomeTabFragments.TimeLeftFrag;
import io.github.celestialphineas.sanxing.sxObjectManager.TimeLeftManager;
import io.github.celestialphineas.sanxing.UIOperateItemActivities.NewItemCreation.CreateNewHabitActivity;
import io.github.celestialphineas.sanxing.UIOperateItemActivities.NewItemCreation.CreateNewTaskActivity;
import io.github.celestialphineas.sanxing.UIOperateItemActivities.NewItemCreation.CreateNewTimeLeftActivity;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //分别管理三种item
    private MyApplication myApplication;
    private TaskManager _task_manager;
    private HabitManager _habit_manager;
    private TimeLeftManager _time_left_manager;
    private final int[] posPosition = new int[1];

    private TaskRepo taskRepo = new TaskRepo(this);//用于task的数据库操作
    private HabitRepo habitRepo = new HabitRepo(this);//用于habit的数据库操作
    private TimeLeftRepo timeLeftRepo = new TimeLeftRepo(this);//用于timeleft的数据库操作

    private Timer timer = new Timer(true);
    // Set the mAdapter
    ViewPagerAdapter adapter;
    // the view pager
    ViewPager viewPager;

    // the tab layout
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        myApplication= (MyApplication) getApplication();
        _task_manager=myApplication.get_task_manager();
        _habit_manager=myApplication.get_habit_manager();
        _time_left_manager=myApplication.get_time_left_manager();
        taskRepo =myApplication.getTaskRepo();
        habitRepo = myApplication.getHabitRepo();
        timeLeftRepo = myApplication.getTimeLeftRepo();

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ///////// Only display one time/////////
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        boolean first=sharedPreferences.getBoolean("iffirst",true);
        if (first)
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            //存入数据
            editor.putBoolean("iffirst",false);//已安装
            editor.commit();
            Intent navigateHelpIntent = new Intent(this, IntroActivity.class);
            startActivity(navigateHelpIntent);
        }
        ///////// Toolbar ////////
        // Get the toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        // Set the toolbar as the default action bar of the window
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);


        //////// Navigation drawer ////////
        // Set drawer layout
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle (
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //////// Tab layout ////////
        // Set the tab layout
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        // Set the tab layout
        viewPager = (ViewPager) findViewById(R.id.main_view_pager);
        // Set the tab layout
        tabLayout = (TabLayout) findViewById(R.id.main_tab);

        //////// Tab pager fragments ////////
        TaskFrag taskFrag = new TaskFrag().newInstance(_task_manager);
        adapter.addFrag(taskFrag, getString(R.string.tab_tasks));

        HabitFrag _habit =new HabitFrag().newInstance(_habit_manager);
        adapter.addFrag(_habit, getString(R.string.tab_habits));

        TimeLeftFrag _time_left = new TimeLeftFrag().newInstance(_time_left_manager);
        adapter.addFrag(_time_left, getString(R.string.tab_time_left));

        // Binding the view pager with the layout, as well as the fab button
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(
                    int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                posPosition[0] = position;
                animateFab(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) { }
        });


        handler.sendEmptyMessage(1);

        // End of the onCreate(Bundle) method
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode) {
            case 0://create task
                if (resultCode == RESULT_OK) {
                    int position=_task_manager.getObjectList().size()-1;
                    Task task=_task_manager.getObjectList().get(position);

                    //insert task to database
                    int new_ID = taskRepo.insert(task);
                    //assign the new ID (primary  key) to the new task
                    task.ID=new_ID;

                    _task_manager.order();
                    adapter.replaceFrag(adapter.getItem(0),new TaskFrag().newInstance(_task_manager));
                    viewPager.setAdapter(adapter);
                    viewPager.setCurrentItem(0);
                }
                break;
            case 1://create habit
                if (resultCode == RESULT_OK) {
                    int position=_habit_manager.getObjectList().size()-1;
                    Habit habit=_habit_manager.getObjectList().get(position);
                    //insert habit to database
                    Log.e("baba home ","?"+habit.getRecordInString());
                    int new_ID = habitRepo.insert(habit);
                    //assign the new ID (primary  key) to the new habit
                    _habit_manager.getObjectList().get(position).ID = new_ID;
                    _habit_manager.order();
                    adapter.replaceFrag(adapter.getItem(1),new HabitFrag().newInstance(_habit_manager));
                    viewPager.setAdapter(adapter);
                    viewPager.setCurrentItem(1);
                }
                break;
            case 2://create left
                if (resultCode == RESULT_OK) {
                    int position=_time_left_manager.getObjectList().size()-1;
                    TimeLeft timeLeft=_time_left_manager.getObjectList().get(position);
                    //insert habit to database
                    int new_ID = timeLeftRepo.insert(timeLeft);
                    //assign the new ID (primary  key) to the new habit
                    _time_left_manager.getObjectList().get(position).ID = new_ID;
                    _time_left_manager.order();
                    adapter.replaceFrag(adapter.getItem(2),new TimeLeftFrag().newInstance(_time_left_manager));
                    viewPager.setAdapter(adapter);
                    viewPager.setCurrentItem(2);
                }
                break;
            case 3://edit task
                if (resultCode == RESULT_OK) {
                    int position=data.getIntExtra("position",-1);
                    if (position==-1) break;
                    int taskID=data.getIntExtra("ID",-1);
                    if (taskID==-1) break;
                    Task task=_task_manager.getObjectList().get(position);
                    task.ID = taskID;
                    //update the task in database
                    taskRepo.update(task);

                    _task_manager.order();
                    adapter.replaceFrag(adapter.getItem(0),new TaskFrag().newInstance(_task_manager));
                    viewPager.setAdapter(adapter);
                    viewPager.setCurrentItem(0);
                }
                break;
            case 4://edit habit
                if (resultCode == RESULT_OK) {
                    int position=data.getIntExtra("position",-1);
                    if (position==-1) break;
                    int habitID=data.getIntExtra("ID",-1);
                    if (habitID==-1) break;
                    Habit habit=_habit_manager.getObjectList().get(position);
                    //update the habit in database
                    habit.ID = habitID;
                    Log.e("aahome list ",habit.getRecordInString());
                    habitRepo.update(habit);

                    _habit_manager.order();
                    adapter.replaceFrag(adapter.getItem(1),new HabitFrag().newInstance(_habit_manager));
                    viewPager.setAdapter(adapter);
                    viewPager.setCurrentItem(1);
                }
                break;
            case 5://edit left
                if (resultCode == RESULT_OK) {
                    int position=data.getIntExtra("position",-1);
                    if (position==-1) break;
                    int timeleftID=data.getIntExtra("ID",-1);
                    if (timeleftID==-1) break;
                    TimeLeft timeLeft=_time_left_manager.getObjectList().get(position);
                    timeLeft.ID = timeleftID;
                    Log.w("time left id", String.valueOf(timeLeft.ID));
                    //update the habit in database
                    timeLeftRepo.update(timeLeft);
                    _time_left_manager.order();
                    adapter.replaceFrag(adapter.getItem(2),new TimeLeftFrag().newInstance(_time_left_manager));
                    viewPager.setAdapter(adapter);
                    viewPager.setCurrentItem(2);
                }
                break;
            default:
        }
    }
    @OnClick(R.id.fab_tasks)
    void fabTasksOnClickBehavior() {
        Log.w("taskRepo.size:"+ taskRepo.getCount(),"??");
        Intent intent = new Intent(this, CreateNewTaskActivity.class);

        //startActivity(intent);
        startActivityForResult(intent, 0);
    }

    @OnClick(R.id.fab_habits)
    void fabHabitsOnClickBehavior() {
        Intent intent = new Intent(this, CreateNewHabitActivity.class);

        startActivityForResult(intent, 1);
    }

    @OnClick(R.id.fab_time_left)
    void fabTimeLeftOnClickBehavior() {
        Intent intent = new Intent(this, CreateNewTimeLeftActivity.class);
        startActivityForResult(intent, 2);
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
            Bundle bundle = new Bundle();
            bundle.putInt("tab", posPosition[0]);
            navigateCalendarIntent.putExtras(bundle);
            startActivity(navigateCalendarIntent);
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

        } else if (id == R.id.nav_statistics) {
            Intent navigateStatisticsIntent = new Intent(this, StatisticsActivity.class);
            startActivity(navigateStatisticsIntent);
        } else if (id == R.id.nav_achievements) {
            Intent navigateAchievementsIntent = new Intent(this, StatisticsActivity.class);
            startActivity(navigateAchievementsIntent);
        } else if (id == R.id.nav_timeline) {
            Intent navigateTimelineIntent = new Intent(this, TimelineActivity.class);
            startActivity(navigateTimelineIntent);
        } else if (id == R.id.nav_help) {
            Intent navigateHelpIntent = new Intent(this, IntroActivity.class);
            startActivity(navigateHelpIntent);
        } else if (id == R.id.nav_settings) {
            Intent navigateSettings = new Intent(this, SettingsActivity.class);
            startActivity(navigateSettings);
        } else if (id == R.id.nav_about) {
            Intent navigateAboutIntent = new Intent(this, AboutActivity.class);
            startActivity(navigateAboutIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    int count=0;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            if (msg.what == 1) {
                int current = viewPager.getCurrentItem();
                viewPager.setAdapter(adapter);
                viewPager.setCurrentItem(current);
                count++;
                Log.e("bbb", String.valueOf(count));
                handler.sendEmptyMessageDelayed(1,
                        60*1000);
            }
            return false;
        }
    });
//    @SuppressLint("HandlerLeak")
//    private Handler handler  = new Handler(){
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if(msg.what == 1){
//                //redraw the adapter per 30sec to change the left time
//                    int current = viewPager.getCurrentItem();
//                    viewPager.setAdapter(adapter);
//
//                    viewPager.setCurrentItem(current);
//                }
//            }
//
//    };
//
//    //任务
//    private TimerTask timerTask = new TimerTask() {
//        public void run() {
//            Message msg = new Message();
//            msg.what = 1;
//            handler.sendMessage(msg);
//        }
//    };
//private MyHandler handler = new MyHandler(this);
//
//    static class MyHandler extends Handler {
//
//        WeakReference weakReference;
//
//        public MyHandler(SecondActivity activity) {
//            weakReference = new WeakReference(activity);
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//
//        }
//    }


    @Override
    public void finish() {
        Log.e("finish!!!", String.valueOf(count));
        if (handler.hasMessages(1)){
            handler.removeMessages(1);
        }
        super.finish();
    }
}
