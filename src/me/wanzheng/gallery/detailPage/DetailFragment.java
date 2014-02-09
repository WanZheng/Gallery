package me.wanzheng.gallery.detailPage;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import me.wanzheng.gallery.AsyncDrawable;
import me.wanzheng.gallery.Gallery;
import me.wanzheng.gallery.R;

/**
 * Created on 2/2/14 by cos
 */
public class DetailFragment extends Fragment {
    public static final String IMAGE_DATA_EXTRA = "extra_image_data";
    private ImageView imageView;
    private String imageUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(Gallery.TAG, "DetailFragment.onCreate()");

        super.onCreate(savedInstanceState);
        imageUrl = getArguments() != null ? getArguments().getString(IMAGE_DATA_EXTRA) : null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(Gallery.TAG, "DetailFragment.onCreateView()");

        final View view = inflater.inflate(R.layout.detail_fragment, container, false);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d(Gallery.TAG, "DetailFragment.onActivityCreated()");

        super.onActivityCreated(savedInstanceState);

        AsyncDrawable.setupAsyncDrawable(imageUrl, imageView, 800, 800);
    }

    @Override
    public void onDestroy() {
        Log.d(Gallery.TAG, "DetailFragment.onDestroy()");

        super.onDestroy();

        if (imageView != null) {
            AsyncDrawable.cancelDownloadTask(imageView);
            imageView.setImageDrawable(null);
        }
    }
}
