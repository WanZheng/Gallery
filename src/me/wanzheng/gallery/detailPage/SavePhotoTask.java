package me.wanzheng.gallery.detailPage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssSSS");

    public SavePhotoTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        String url = params[0];

        String fileName = format.format(new Date()) + ".jpg";
        File dir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "gallery");
        if (!dir.isDirectory() && !dir.mkdirs()) {
            Log.e(Gallery.TAG, "Failed to create dir: " + dir.getPath());
            return null;
        }

        File file = new File(dir, fileName);

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
        if (path == null) {
            Toast.makeText(context, context.getString(R.string.saved_failed_toast), Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(new File(path));
        intent.setData(uri);
        context.sendBroadcast(intent);

        Toast.makeText(context, context.getString(R.string.saved_toast), Toast.LENGTH_LONG).show();
    }
}
