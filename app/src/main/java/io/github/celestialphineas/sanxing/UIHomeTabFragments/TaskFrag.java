package io.github.celestialphineas.sanxing.UIHomeTabFragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.celestialphineas.sanxing.MyApplication;
import io.github.celestialphineas.sanxing.R;
import io.github.celestialphineas.sanxing.sxObject.Task;
import io.github.celestialphineas.sanxing.sxObjectManager.TaskManager;
import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskFrag#newInstance} factory method to
 * create an instance of this fragment.
 */

@SuppressWarnings("WrongConstant")
public class TaskFrag extends Fragment {
    @BindView(R.id.task_recycler_view)      RecyclerView recyclerView;

    public TaskRecyclerAdapter mAdapter;
    public TaskManager taskManager;
    private RecyclerView.LayoutManager layoutManager;

    //public ViewPager mViewPager;

    public TaskFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */


    public TaskFrag newInstance(TaskManager taskManager) {
        TaskFrag fragment = new TaskFrag();
        fragment.taskManager = taskManager;
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        //
        List<Task> need_add = new ArrayList<>();
        for (Task task : taskManager.getObjectList()){
            if (task.getState()==1){//unfinished
                need_add.add(task);
            }
        }
        mAdapter = new TaskRecyclerAdapter(need_add);
        if(taskManager != null) recyclerView.setAdapter(mAdapter);
        else recyclerView.setAdapter(new TaskRecyclerAdapter());
        return view;
    }
//    @Override public void onDestroyView() {
//
//        // uncomment the line below to fix the issue
//        recyclerView.setAdapter(null);
//
//        super.onDestroyView();
//    }


}
