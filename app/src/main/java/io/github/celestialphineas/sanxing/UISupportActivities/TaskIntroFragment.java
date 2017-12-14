package io.github.celestialphineas.sanxing.UISupportActivities;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.celestialphineas.sanxing.R;

public class TaskIntroFragment extends Fragment implements ISlideBackgroundColorHolder {

    @BindView(R.id.task_intro_root_layout)      ViewGroup rootView;
    final int[] currentFAB = new int[1];


    public TaskIntroFragment() { }

    public static TaskIntroFragment newInstance(String param1, String param2) {
        TaskIntroFragment fragment = new TaskIntroFragment();
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
        View view = inflater.inflate(R.layout.fragment_task_intro, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public int getDefaultBackgroundColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getContext().getResources().getColor(R.color.colorTasks, null);
        } else {
            return getContext().getResources().getColor(R.color.colorTasks);
        }
    }

    @Override
    public void setBackgroundColor(@ColorInt int color) {
        if(rootView != null) {
            rootView.setBackgroundColor(color);
        }
    }
}
