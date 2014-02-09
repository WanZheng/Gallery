package me.wanzheng.gallery.detailPage;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import me.wanzheng.gallery.Gallery;
import me.wanzheng.gallery.R;

/**
 * Created on 2/2/14 by cos
 */
public class DetailPage extends Activity implements View.OnClickListener {
    private static final String TAG = "DetailPage";
    private String imageUrl;

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
            imageUrl = getIntent().getStringExtra(DetailFragment.IMAGE_DATA_EXTRA);
            args.putString(DetailFragment.IMAGE_DATA_EXTRA, imageUrl);
            fragment.setArguments(args);

            ft.add(android.R.id.content, fragment, TAG);
            ft.commit();
        }

        {
            final ActionBar actionBar = getActionBar();

            // Hide title text and set home as up
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);

            final View content = findViewById(android.R.id.content);
            // Hide and show the ActionBar as the visibility changes
            content.setOnSystemUiVisibilityChangeListener(
                    new View.OnSystemUiVisibilityChangeListener() {
                        @Override
                        public void onSystemUiVisibilityChange(int vis) {
                            if ((vis & View.SYSTEM_UI_FLAG_LOW_PROFILE) != 0) {
                                actionBar.hide();
                            } else {
                                actionBar.show();
                            }
                        }
                    });

            // Start low profile mode and hide ActionBar
            content.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

            case R.id.save:
                SavePhotoTask task = new SavePhotoTask(this);
                task.execute(imageUrl + "?size=1024x1024&scaleType=fit_xy");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        final View content = findViewById(android.R.id.content);
        final int vis = content.getSystemUiVisibility();
        if ((vis & View.SYSTEM_UI_FLAG_LOW_PROFILE) != 0) {
            content.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        } else {
            content.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
        }
    }
}