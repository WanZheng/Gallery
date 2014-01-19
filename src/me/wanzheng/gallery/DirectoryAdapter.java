package me.wanzheng.gallery;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created on 18/1/14 by cos
 */
public class DirectoryAdapter extends ArrayAdapter<FileEntry> {
    private final LayoutInflater inflater;

    public DirectoryAdapter(Context context) {
        super(context, R.layout.text_node);

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(Gallery.TAG, "getView(position=" + position + ", convertView=" + convertView);

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
    }
}
