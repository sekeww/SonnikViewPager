package aovddfhfpjtmqids.book.appmk.com.sonnikviewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by BEK on 18.02.2017.
 */

public class SonsAdapter extends FragmentStatePagerAdapter {

   // private ArrayList<Fragment> fragments;
    private String toFindText="";
    protected static final String[] CONTENT = new String[] { "А", "Ә", "Б", "В", "Д", "Е", "Ж","З", "И",
            "К", "Қ", "Л", "М", "Н", "О", "Ө", "П", "Р", "С", "Т", "У", "Ұ", "Ү", "Ф", "Х", "Ш", "І" };


    public SonsAdapter(FragmentManager fm) {
        super(fm);
        //this.fragments = fragments;
    }



    @Override
    public Fragment getItem(int position) {
        return FirstFragment.newInstance(position, CONTENT[position]);

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
