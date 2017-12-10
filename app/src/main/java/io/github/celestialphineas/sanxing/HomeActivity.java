package io.github.celestialphineas.sanxing;

import android.app.FragmentManager;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.celestialphineas.sanxing.UISupportActivities.AboutActivity;
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
    
    private TaskRepo repo = new TaskRepo(this);//用于task的数据库操作

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

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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
        TaskFrag taskFrag = new TaskFrag().newInstance(_task_manager.getObjectList());
        adapter.addFrag(taskFrag, getString(R.string.tab_tasks));

        HabitFrag _habit =new HabitFrag().newInstance(_habit_manager.getObjectList());
        adapter.addFrag(_habit, getString(R.string.tab_habits));

        TimeLeftFrag _time_left = new TimeLeftFrag().newInstance(_time_left_manager.getObjectList());
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
                animateFab(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) { }
        });

        //////// TODO: to be refactored. /////////
        _habit_manager.addObject(new Habit("exercise"));
        _habit_manager.addObject(new Habit("eat breakfast"));
        _time_left_manager.addObject(new TimeLeft("University time"));
        _time_left_manager.addObject(new TimeLeft("Left time"));

        if (repo.getCount()==0){
            _task_manager.addObject(new Task("task test1"));
            _task_manager.addObject(new Task("task test2"));
            //Log.e("test","??");
        }
        else {
            _task_manager.addAll(repo.getTaskList());
            //repo.deleteAll();
            Log.w("test","??");
        }

        // End of the onCreate(Bundle) method
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
//                    if (data!=null) Log.e("??", "?11?"+data.getStringExtra("baba"));
                    String title = data.getStringExtra("task_title");
                    Log.w("return data:", title);
                    String begin = data.getStringExtra("task_begin_time");
                    String end = data.getStringExtra("task_end_time");
                    String n_description=data.getStringExtra("task_description");
                    int n_importance=data.getIntExtra("task_importance",3);

                    repo.insert(new Task(title,begin,end,n_description,n_importance));

                    _task_manager.updateTaskManager(repo.getTaskList());
                    //renew viewPager to display the new task in screen at once
                    adapter.replaceFrag(adapter.getItem(0),new TaskFrag().newInstance(_task_manager.getObjectList()));
                    viewPager.setAdapter(adapter);
                    viewPager.setCurrentItem(0);
                }
                break;

            default:
        }
    }

    @OnClick(R.id.fab_tasks)
    void fabTasksOnClickBehavior() {
        Log.w("repo.size:"+repo.getCount(),"??");
        Intent intent = new Intent(this, CreateNewTaskActivity.class);

        //startActivity(intent);
        startActivityForResult(intent, 0);



    }

    @OnClick(R.id.fab_habits)
    void fabHabitsOnClickBehavior() {
        Intent intent = new Intent(this, CreateNewHabitActivity.class);

        startActivity(intent);
    }

    @OnClick(R.id.fab_time_left)
    void fabTimeLeftOnClickBehavior() {
        Intent intent = new Intent(this, CreateNewTimeLeftActivity.class);
        startActivity(intent);
    }


    //通过AlertDialog实现了点击按钮在相应的fragment添加新item的功能 但实现方法比较拙劣 代码冗长 之后可以考虑将对话框单独提取出来
//    public void onClick(View v){
//
//        if (v.getId()==R.id.fab_tasks){
//            final EditText taskcontent = new EditText(this);
//            AlertDialog.Builder builder=new Builder(this);
//            builder.setPositiveButton("确定", new OnClickListener() {  //这个是设置确定按钮
//
//                @RequiresApi(api = Build.VERSION_CODES.O)
//                @Override
//                public void onClick(DialogInterface arg0, int arg1) {
//                    Toast.makeText(HomeActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
//                    Task need_add = new Task(taskcontent.getText().toString());
//                    _task_manager.addObject(need_add);
//                    repo.insert(need_add);
//                }
//            });
//            builder.setNegativeButton("取消", new OnClickListener() {  //取消按钮
//
//                @Override
//                public void onClick(DialogInterface arg0, int arg1) {
//                    Toast.makeText(HomeActivity.this, "取消添加",Toast.LENGTH_SHORT).show();
//
//                }
//            });
//
//            AlertDialog b=builder.create();
//            b.setIcon(R.mipmap.ic_launcher);//设置图标
//            b.setTitle("叮");//设置对话框的标题
//            b.setView (taskcontent);//设置对话框的内容
//            b.show();  //必须show一下才能看到对话框
//
//            mAdapter.replaceFrag(mAdapter.getItem(0),new TaskFrag().newInstance(_task_manager.getObjectList()));
//            viewPager.removeAllViewsInLayout();
//            viewPager.setAdapter(mAdapter);
//            viewPager.setCurrentItem(0);
//            tabLayout.setupWithViewPager(viewPager);
//
//
//        }else if (v.getId()==R.id.fab_habits){
//            final EditText habitcontent = new EditText(this);
//            AlertDialog.Builder builder=new Builder(this);
//            builder.setPositiveButton("确定", new OnClickListener() {  //这个是设置确定按钮
//
//                @RequiresApi(api = Build.VERSION_CODES.O)
//                @Override
//                public void onClick(DialogInterface arg0, int arg1) {
//                    Toast.makeText(HomeActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
//                    _habit_manager.addObject(new Habit(habitcontent.getText().toString()));
//                }
//            });
//            builder.setNegativeButton("取消", new OnClickListener() {  //取消按钮
//
//                @Override
//                public void onClick(DialogInterface arg0, int arg1) {
//                    Toast.makeText(HomeActivity.this, "取消添加",Toast.LENGTH_SHORT).show();
//
//                }
//            });
//
//            AlertDialog b=builder.create();
//            b.setIcon(R.mipmap.ic_launcher);//设置图标
//            b.setTitle("叮");//设置对话框的标题
//            b.setView (habitcontent);//设置对话框的内容
//            b.show();
//
//            mAdapter.replaceFrag(mAdapter.getItem(1),new HabitFrag().newInstance(_habit_manager.getObjectList()));
//            viewPager.setAdapter(mAdapter);
//            viewPager.setCurrentItem(1);
//            //tabLayout.setupWithViewPager(viewPager);
//        }else if (v.getId()==R.id.fab_time_left){
//            final EditText timeleftcontent = new EditText(this);
//            AlertDialog.Builder builder=new Builder(this);
//            builder.setPositiveButton("确定", new OnClickListener() {  //这个是设置确定按钮
//
//                @RequiresApi(api = Build.VERSION_CODES.O)
//                @Override
//                public void onClick(DialogInterface arg0, int arg1) {
//                    Toast.makeText(HomeActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
//                    _time_left_manager.addObject(new TimeLeft(timeleftcontent.getText().toString()));
//                }
//            });
//            builder.setNegativeButton("取消", new OnClickListener() {  //取消按钮
//
//                @Override
//                public void onClick(DialogInterface arg0, int arg1) {
//                    Toast.makeText(HomeActivity.this, "取消添加",Toast.LENGTH_SHORT).show();
//
//                }
//            });
//
//            AlertDialog b=builder.create();
//            b.setIcon(R.mipmap.ic_launcher);//设置图标
//            b.setTitle("叮");//设置对话框的标题
//            b.setView (timeleftcontent);//设置对话框的内容
//            b.show();
//
//            mAdapter.replaceFrag(mAdapter.getItem(2),new TimeLeftFrag().newInstance(_time_left_manager.getObjectList()));
//            viewPager.removeAllViewsInLayout();
//            viewPager.setAdapter(mAdapter);
//            viewPager.setCurrentItem(2);
//        }
//    }


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
}
