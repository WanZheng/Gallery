package me.wanzheng.gallery.detailPage;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

/**
 * Created on 2/2/14 by cos
 */
public class ImagePagerAdapter extends FragmentStatePagerAdapter {
    private int size;

    public ImagePagerAdapter(FragmentManager fm, int size) {
        super(fm);
        this.size = size;
    }

    @Override
    public Fragment getItem(int i) {
        return null;  // TODO:
    }

    @Override
    public int getCount() {
        return size;
    }
}
