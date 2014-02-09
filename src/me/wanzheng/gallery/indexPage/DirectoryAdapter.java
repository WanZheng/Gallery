package me.wanzheng.gallery.indexPage;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import me.wanzheng.gallery.AsyncDrawable;
import me.wanzheng.gallery.FileEntry;
import me.wanzheng.gallery.Gallery;
import me.wanzheng.gallery.R;

/**
 * Created on 18/1/14 by cos
 */
public class DirectoryAdapter extends ArrayAdapter<FileEntry> {
    // private final LayoutInflater inflater;

    public DirectoryAdapter(Context context) {
        super(context, R.layout.text_node);

        // inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(Gallery.TAG, "getView(position=" + position + ", convertView=" + convertView);

        /*
        View view;
        if (convertView == null) {
            view = inflater.inflate(R.layout.text_node, parent, false);
            view.setTag(R.id.text, view.findViewById(R.id.text));
            view.setTag(R.id.img, view.findViewById(R.id.img));
        }else{
            view = convertView;
        }

        FileEntry entry = getItem(position);
        if (entry.isDir) {
            TextView textView = (TextView)view.getTag(R.id.text);
            textView.setVisibility(View.VISIBLE);
            textView.setText(entry.filename);

            view.findViewById(R.id.img).setVisibility(View.GONE);
        }else{
            ImageView imageView = (ImageView)view.getTag(R.id.img);
            imageView.setVisibility(View.VISIBLE);
            AsyncDrawable.setupAsyncDrawable(entry.baseDir+entry.filename, imageView);

            view.findViewById(R.id.text).setVisibility(View.GONE);
        }

        return view;
        */

        ImageView imageView;
        if (convertView == null) { // if it's not recycled, initialize some attributes
            imageView = new CustomImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new GridView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        } else {
            imageView = (ImageView) convertView;
        }
        FileEntry entry = getItem(position);
        AsyncDrawable.setupAsyncDrawable(entry.baseDir + entry.filename, imageView, 300, 300);
        return imageView;
    }
}
