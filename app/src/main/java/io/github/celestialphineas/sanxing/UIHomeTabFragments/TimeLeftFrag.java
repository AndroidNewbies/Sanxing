package io.github.celestialphineas.sanxing.UIHomeTabFragments;

/**
 * Created by apple on 2017/11/3.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.github.celestialphineas.sanxing.R;
import io.github.celestialphineas.sanxing.sxObject.TimeLeft;
import io.github.celestialphineas.sanxing.sxObjectManager.TimeLeftManager;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimeLeftFrag#newInstance} factory method to
 * create an instance of this fragment.
 */

public class TimeLeftFrag extends Fragment {

    private TimeLeftManager _time_left_manager;
    private String mArgs;
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
    // TODO: Rename and change types and number of parameters
    public static TimeLeftFrag newInstance() {
        TimeLeftFrag fragment = new TimeLeftFrag();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    public TimeLeftFrag newInstance(String arguments) {

        TimeLeftFrag fragment = new TimeLeftFrag();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.mArgs = arguments;
        return fragment;
    }
    public TimeLeftFrag newInstance(List<TimeLeft> mylist) {
        mArgs = "list";
        _time_left_manager = new TimeLeftManager(mylist);

        TimeLeftFrag fragment = new TimeLeftFrag();
        Bundle args = new Bundle();
        ArrayList<String> test_args = new ArrayList<String>();
        for (int i=0;i<mylist.size();i++){
            test_args.add(mylist.get(i).getContent());
        }
        //args.putStringArrayList("data",test_args);
        //fragment.setArguments(args);
        fragment._time_left_manager = new TimeLeftManager(mylist);
        fragment.mArgs = "list";
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
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.task_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        if (_time_left_manager !=null) recyclerView.setAdapter(new TimeLeftRecyclerAdapter(_time_left_manager.readObjectList()));
        else recyclerView.setAdapter(new TaskRecyclerAdapter());
        return view;
    }

}
