package aovddfhfpjtmqids.book.appmk.com.sonnikviewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by BEK on 18.02.2017.
 */

public class SonsAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> fragments;
    protected static final String[] CONTENT = new String[] { "A", "B", "C", "D", "E", "F", "G","H", "I",
            "J", "K" };


    public SonsAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return CONTENT.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return SonsAdapter.CONTENT[position % CONTENT.length];
    }
}
