package io.github.celestialphineas.sanxing.UIHomeTabFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.celestialphineas.sanxing.R;
import io.github.celestialphineas.sanxing.SanxingBackend.TaskManager;
import io.github.celestialphineas.sanxing.SanxingBackend.Task;
import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskFrag#newInstance} factory method to
 * create an instance of this fragment.
 */

@SuppressWarnings("WrongConstant")
public class TaskFrag extends Fragment {
    @BindView(R.id.task_recycler_view)      RecyclerView recyclerView;
    public TaskRecyclerAdapter adapter;
    private TaskManager taskManager;
    private RecyclerView.LayoutManager layoutManager;
    private String mArgs="l";
    public TaskFrag() {
        // Required empty public constructor
    }
//    public TaskFrag(String args) {
//        mArgs = "r";
//        // Required empty public constructor
//    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskFrag newInstance() {
        TaskFrag fragment = new TaskFrag();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    public TaskFrag newInstance(String arguments) {
        TaskFrag fragment = new TaskFrag();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.mArgs = arguments;
        return fragment;
    }
    public TaskFrag newInstance(List<Task> mylist) {
        mArgs = "m";
        taskManager = new TaskManager(mylist);
        System.out.println(mArgs);
        TaskFrag fragment = new TaskFrag();
        Bundle args = new Bundle();
        //fragment.setArguments(args);
        fragment.taskManager = new TaskManager(mylist);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) { }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_task, container, false);
        ButterKnife.bind(this, view);

        // Set layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        ((LinearLayoutManager)layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        // Set animator
        recyclerView.setItemAnimator(new FadeInLeftAnimator());
        // Set recycler
        adapter = new TaskRecyclerAdapter(taskManager.readObjectList());
        if(taskManager != null) recyclerView.setAdapter(adapter);
        else recyclerView.setAdapter(new TaskRecyclerAdapter());

//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setHasFixedSize(true);//可提高性能
//
//        adapter = new TaskRecyclerAdapter(taskManager.readObjectList());
//
//        if (taskManager != null) recyclerView.setAdapter(adapter);
//        else recyclerView.setAdapter(new TaskRecyclerAdapter());
//
//        //以下是为了实现点击一个task 弹出删除界面 进行删除写的尝试性代码 bug很多 不能及时刷新，不能对数据库进行操作等
//        adapter.setOnItemClickListener(new TaskRecyclerAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, final int position) {
//                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
//                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  //这个是设置确定按钮
//
//                    @RequiresApi(api = Build.VERSION_CODES.O)
//                    @Override
//                    public void onClick(DialogInterface arg0, int arg1) {
//                        taskManager.removeObject(position);
//                    }
//                });
//                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {  //取消按钮
//
//                    @Override
//                    public void onClick(DialogInterface arg0, int arg1) {
//
//                    }
//                });
//
//                AlertDialog b=builder.create();
//                b.setIcon(R.mipmap.ic_launcher);//设置图标
//                b.setTitle("叮");//设置对话框的标题
//                b.setMessage("要删除这个任务吗？");
//                b.show();  //必须show一下才能看到对话框
//            }
//        });
        return view;
    }


}
