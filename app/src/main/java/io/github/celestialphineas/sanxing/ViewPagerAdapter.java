package io.github.celestialphineas.sanxing;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by celestialphineas on 17-10-10.
 * This file is an mAdapter to the ViewPager
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFrag(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    public void removeFrag(Fragment fragment) {
        mFragmentList.remove(fragment);
        //mFragmentTitleList.remove(title);
    }

    public void replaceFrag(Fragment old_fragment, Fragment new_fragment) {
        int position = mFragmentList.indexOf(old_fragment);
        mFragmentList.remove(position);
        mFragmentList.add(position, new_fragment);
        //mFragmentTitleList.remove(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    // Called when the host view is attempting to determine if an item's position has changed. Returns POSITION_UNCHANGED if the position of the given item has not changed or POSITION_NONE if the item is no longer present in the adapter.
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
