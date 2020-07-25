package io.github.celestialphineas.sanxing.UISupportActivities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro2;

import butterknife.ButterKnife;

/**
 * Created by celestialphineas on 17-12-14.
 */

public class IntroActivity extends AppIntro2 {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        // Add your slide's fragments here
        // AppIntro will automatically generate the dots indicator and buttons.
//        SliderPage sliderPage1 = new SliderPage();
        addSlide(new QuoteFragment());
        addSlide(new TaskIntroFragment());
        addSlide(new HabitIntroFragment());
        addSlide(new TimeLeftIntroFragment());
        addSlide(new AddHelpFragment());
        addSlide(new CalendarHelpFragment());
        addSlide(new NavigationHelpFragment());
        addSlide(new StartSanxingFragment());

        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest
//        addSlide(AppIntroFragment.newInstance(title, description, image, background_colour));

        // OPTIONAL METHODS

        // SHOW or HIDE the statusbar
        showStatusBar(false);
        setColorTransitionsEnabled(true);

        // Turn vibration on and set intensity
        // NOTE: you will need to ask VIBRATE permission in Manifest if you haven't already
//        setVibrate(true);
//        setVibrateIntensity(30);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        // Do something when users tap on Done button.
        super.onDonePressed(currentFragment);
        this.finish();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        if (newFragment instanceof AddHelpFragment) {
            if (((AddHelpFragment) newFragment).currentFAB[0] == 0) {
                ((AddHelpFragment) newFragment).showAnimation();
            }
        } else if (newFragment instanceof CalendarHelpFragment) {
            ((CalendarHelpFragment) newFragment).showAnimation();
        } else if (newFragment instanceof NavigationHelpFragment) {
            ((NavigationHelpFragment) newFragment).showAnimation();
        }
    }
}
