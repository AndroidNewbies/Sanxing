package io.github.celestialphineas.sanxing.UICalendarViews;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.celestialphineas.sanxing.R;

public class HabitCalendarFragment extends Fragment {

    public HabitCalendarFragment() {
        // Required empty public constructor
    }

    public static HabitCalendarFragment newInstance() {
        HabitCalendarFragment fragment = new HabitCalendarFragment();
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
        return inflater.inflate(R.layout.fragment_habit_calendar, container, false);
    }
}
