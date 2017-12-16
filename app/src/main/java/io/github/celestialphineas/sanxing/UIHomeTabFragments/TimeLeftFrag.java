package io.github.celestialphineas.sanxing.UIHomeTabFragments;

/**
 * Created by apple on 2017/11/3.
 */
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.github.celestialphineas.sanxing.R;
import io.github.celestialphineas.sanxing.sxObject.Task;
import io.github.celestialphineas.sanxing.sxObject.TimeLeft;
import io.github.celestialphineas.sanxing.sxObjectManager.TimeLeftManager;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimeLeftFrag#newInstance} factory method to
 * create an instance of this fragment.
 */

public class TimeLeftFrag extends Fragment {
    @BindView(R.id.task_recycler_view)      RecyclerView recyclerView;
    private TimeLeftManager timeLeftManager;

    public TimeLeftFrag() {
        // Required empty public constructor
    }
//    public TimeLeftFrag(String args) {
//        mArgs = "r";
//        // Required empty public constructor
//    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */

    public TimeLeftFrag newInstance(TimeLeftManager timeLeftManager) {
        TimeLeftFrag fragment = new TimeLeftFrag();
        fragment.timeLeftManager = timeLeftManager;
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.task_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        //todo: get unfinished list  task是不是引用？ 不是的话 在application里加一下need_add

        List<TimeLeft> need_add = new ArrayList<>();
        for (TimeLeft timeLeft : timeLeftManager.getObjectList()){
            if (timeLeft.getState()==1){//unfinished
                need_add.add(timeLeft);
            }
        }
        if (need_add !=null) recyclerView.setAdapter(new TimeLeftRecyclerAdapter(need_add));
        if (timeLeftManager !=null) recyclerView.setAdapter(new TimeLeftRecyclerAdapter(need_add));
        else recyclerView.setAdapter(new TaskRecyclerAdapter());
        return view;
    }

}
