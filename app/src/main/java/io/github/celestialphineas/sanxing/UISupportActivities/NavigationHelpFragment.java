package io.github.celestialphineas.sanxing.UISupportActivities;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
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

public class NavigationHelpFragment extends Fragment implements ISlideBackgroundColorHolder {

    @BindView(R.id.navigation_help_root_layout)
    ViewGroup rootView;
    @BindView(R.id.nav_icon1)
    ImageView imageView1;
    @BindView(R.id.nav_icon2)
    ImageView imageView2;
    @BindView(R.id.nav_icon3)
    ImageView imageView3;
    @BindView(R.id.card_side_nav)
    CardView navigationCard;


    public NavigationHelpFragment() {
    }

    public static NavigationHelpFragment newInstance(String param1, String param2) {
        NavigationHelpFragment fragment = new NavigationHelpFragment();
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
        View view = inflater.inflate(R.layout.fragment_navigation_help, container, false);
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
        if (rootView != null) {
            rootView.setBackgroundColor(color);
        }
    }

    void showAnimation() {
        Animation slideDown = AnimationUtils.loadAnimation(this.getContext(), R.anim.slide_down);
        Animation slideRight = AnimationUtils.loadAnimation(this.getContext(), R.anim.slide_right);
        imageView1.startAnimation(slideDown);
        imageView2.startAnimation(slideDown);
        imageView3.startAnimation(slideDown);
        navigationCard.startAnimation(slideRight);
    }
}
