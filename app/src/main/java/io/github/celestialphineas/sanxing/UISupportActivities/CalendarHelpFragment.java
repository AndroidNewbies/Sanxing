package io.github.celestialphineas.sanxing.UISupportActivities;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.celestialphineas.sanxing.R;

public class CalendarHelpFragment extends Fragment implements ISlideBackgroundColorHolder {

    @BindView(R.id.calendar_help_root_layout)       ViewGroup rootView;
    @BindView(R.id.calendar_icon)                   ImageView imageView;
    @BindView(R.id.bar_holder)                      CardView barHolder;


    public CalendarHelpFragment() { }

    public static CalendarHelpFragment newInstance(String param1, String param2) {
        CalendarHelpFragment fragment = new CalendarHelpFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
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
        View view = inflater.inflate(R.layout.fragment_calendar_help, container, false);
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
            return getContext().getResources().getColor(R.color.background_grey, null);
        } else {
            return getContext().getResources().getColor(R.color.background_grey);
        }
    }

    @Override
    public void setBackgroundColor(@ColorInt int color) {
        if(rootView != null) {
            rootView.setBackgroundColor(color);
        }
    }

    void showAnimation() {
        Animation shake = AnimationUtils.loadAnimation(this.getContext(), R.anim.shake);
        Animation rightEnter = AnimationUtils.loadAnimation(this.getContext(), R.anim.right_enter);
        imageView.startAnimation(shake);
        barHolder.startAnimation(rightEnter);
    }
}
