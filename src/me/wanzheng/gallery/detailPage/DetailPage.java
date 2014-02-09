package me.wanzheng.gallery.detailPage;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created on 2/2/14 by cos
 */
public class DetailPage extends Activity {
    private static final String TAG = "DetailPage";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.detail_page);
//        final ImagePagerAdapter adapter = new ImagePagerAdapter(getFragmentManager(), 1);
//        final ViewPager pager = (ViewPager) findViewById(R.id.pager);
//        pager.setAdapter(adapter);

        final DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int screenWidth = displayMetrics.widthPixels;
        final int screenHeight = displayMetrics.heightPixels;

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (getFragmentManager().findFragmentByTag(TAG) == null) {
            final FragmentTransaction ft = getFragmentManager().beginTransaction();

            DetailFragment fragment = new DetailFragment();
            final Bundle args = new Bundle();
            final String imageUrl = getIntent().getStringExtra(DetailFragment.IMAGE_DATA_EXTRA);
            args.putString(DetailFragment.IMAGE_DATA_EXTRA, imageUrl);
            fragment.setArguments(args);

            ft.add(android.R.id.content, fragment, TAG);
            ft.commit();
        }
    }
}