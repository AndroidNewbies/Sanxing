package io.github.celestialphineas.sanxing.UISupportActivities;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.celestialphineas.sanxing.R;

public class AddHelpFragment extends Fragment implements ISlideBackgroundColorHolder {

    @BindView(R.id.add_root_layout)     ViewGroup rootView;
    @BindView(R.id.fab1)                FloatingActionButton fab1;
    @BindView(R.id.fab2)                FloatingActionButton fab2;
    @BindView(R.id.fab3)                FloatingActionButton fab3;
    final int[] currentFAB = new int[1];


    public AddHelpFragment() { }

    public static AddHelpFragment newInstance(String param1, String param2) {
        AddHelpFragment fragment = new AddHelpFragment();
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
        View view = inflater.inflate(R.layout.fragment_add_help, container, false);
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
        final Handler animationHandler = new Handler();
        Runnable animationRunable = new Runnable() {
            @Override
            synchronized public void run() {
                try {
                    switch (currentFAB[0]) {
                        case 0: fab1.show(); currentFAB[0] = 1;
                            animationHandler.postDelayed(this, 2000); break;
                        case 1: fab2.show(); fab1.hide(); currentFAB[0] = 2;
                            animationHandler.postDelayed(this, 2000); break;
                        case 2: fab3.show(); fab2.hide(); currentFAB[0] = 3;
                            animationHandler.postDelayed(this, 2000); break;
                        case 3: default: fab1.show(); fab3.hide(); currentFAB[0] = 1;
                            animationHandler.postDelayed(this, 1000); break;
                    }

                } catch (Exception e) { }
            }
        };
        animationHandler.postDelayed(animationRunable, 100);
    }
}
