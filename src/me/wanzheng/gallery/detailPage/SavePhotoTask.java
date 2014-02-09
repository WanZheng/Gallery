package me.wanzheng.gallery.detailPage;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import me.wanzheng.gallery.Gallery;
import me.wanzheng.gallery.R;
import me.wanzheng.gallery.util.UrlDownloader;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created on 9/2/14 by cos
 */
public class SavePhotoTask extends AsyncTask<String, Integer, String> {
    private Context context;
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public SavePhotoTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        String url = params[0];

        String fileName = format.format(new Date()) + ".jpg";
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), fileName);

        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            UrlDownloader.downloadUrlToStream(url, out);
        } catch (FileNotFoundException e) {
            Log.e(Gallery.TAG, "Failed to open file " + file + ": " + e);
            return null;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }

        return file.getPath();
    }

    @Override
    protected void onPostExecute(String path) {
        Toast.makeText(context, context.getString(R.string.saved_toast), Toast.LENGTH_LONG).show();
    }
}
