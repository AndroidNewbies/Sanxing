package io.github.celestialphineas.sanxing.UIOperateItemActivities.Base;

import android.animation.Animator;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;

import butterknife.BindInt;
import butterknife.BindView;
import io.github.celestialphineas.sanxing.R;

/**
 * Created by celestialphineas on 17-12-7.
 */

public abstract class OperateItemActivityBase extends AppCompatActivity {
    // Animation center
    protected int cx, cy;
    protected String title = "";
    @BindView(R.id.create_new_item_toolbar)
    protected Toolbar toolbar;
    @BindInt(R.integer.reveal_time)
    protected int revealTime;
    @BindInt(R.integer.snack_bar_timeout)
    protected int snackBarTimeout;

    //////////////// ANIMATIONS ////////////////
    // Reveal animation implementation
    protected void animationReveal(Bundle savedInstanceState) {
        cx = getResources().getDisplayMetrics().widthPixels - 120;
        cy = getResources().getDisplayMetrics().heightPixels - 180;
        final View rootLayout = getWindow().getDecorView().getRootView();
        if (savedInstanceState == null) {
            rootLayout.setVisibility(View.INVISIBLE);
            ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        float finalRadius = Math.max(rootLayout.getWidth(), rootLayout.getHeight());
                        // create the animator for this view (the start radius is zero)
                        Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout, cx, cy, 0, finalRadius);
                        circularReveal.setDuration(revealTime);

                        // make the view visible and start the animation
                        rootLayout.setVisibility(View.VISIBLE);
                        circularReveal.start();
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            rootLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        } else {
                            rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                    }
                });
            }
        }
    }

    // Exiting animation
    protected void animationExit() {
        final View rootLayout = getWindow().getDecorView().getRootView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            float finalRadius = Math.max(rootLayout.getWidth(), rootLayout.getHeight());
            Animator circularReveal
                    = ViewAnimationUtils.createCircularReveal(rootLayout, cx, cy, finalRadius, 0);
            circularReveal.setDuration(revealTime);
            circularReveal.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    rootLayout.setVisibility(View.INVISIBLE);
                    finish();
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                }
            });
            circularReveal.setDuration(400);
            circularReveal.start();
        } else {
            super.onBackPressed();
        }
    }

    protected void animationSubmit() {
        cx = getResources().getDisplayMetrics().widthPixels - 120;
        cy = 100;
        animationExit();
    }

    @Override
    public void onBackPressed() {
        cx = 120;
        cy = 100;
        animationExit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_new_item_menu, menu);
        return true;
    }

    // Confirm action
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
