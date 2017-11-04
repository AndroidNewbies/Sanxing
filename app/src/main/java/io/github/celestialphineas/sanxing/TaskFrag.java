package io.github.celestialphineas.sanxing;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskFrag#newInstance} factory method to
 * create an instance of this fragment.
 */

@SuppressWarnings("WrongConstant")
public class TaskFrag extends Fragment {
    public TaskRecyclerAdapter mAdapter;
    private TaskManager _task_manager;
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
        _task_manager = new TaskManager(mylist);
        System.out.println(mArgs);
        TaskFrag fragment = new TaskFrag();
        Bundle args = new Bundle();
        //fragment.setArguments(args);
        fragment._task_manager = new TaskManager(mylist);

        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View mView = inflater.inflate(R.layout.fragment_task, container, false);
        RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.task_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);//可提高性能

        mAdapter = new TaskRecyclerAdapter(_task_manager.readObjectList());

        if (_task_manager != null) recyclerView.setAdapter(mAdapter);
        else recyclerView.setAdapter(new TaskRecyclerAdapter());

        //以下是为了实现点击一个task 弹出删除界面 进行删除写的尝试性代码 bug很多 不能及时刷新，不能对数据库进行操作等
        mAdapter.setOnItemClickListener(new TaskRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  //这个是设置确定按钮

                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        _task_manager.removeObject(position);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {  //取消按钮

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

                AlertDialog b=builder.create();
                b.setIcon(R.mipmap.ic_launcher);//设置图标
                b.setTitle("叮");//设置对话框的标题
                b.setMessage("要删除这个任务吗？");
                b.show();  //必须show一下才能看到对话框
            }
        });
        return mView;
    }


}
