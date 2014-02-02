package me.wanzheng.gallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import me.wanzheng.gallery.indexPage.IndexPage;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import java.io.InputStream;
import java.lang.ref.WeakReference;

/**
 * Created on 19/1/14 by cos
 */
public class AsyncDrawable extends ColorDrawable{
    private final WeakReference<BitmapDownloadTask> taskWeakReference;

    public static void setupAsyncDrawable(String url, ImageView imageView) {
        if (! cancelPotentialDownload(url, imageView)) {
            return;
        }

        BitmapDownloadTask task = new BitmapDownloadTask(imageView);
        task.url = url;
        AsyncDrawable drawable = new AsyncDrawable(task);
        imageView.setImageDrawable(drawable);

        task.execute();
    }

    public AsyncDrawable(BitmapDownloadTask downloadTask) {
        taskWeakReference = new WeakReference<BitmapDownloadTask>(downloadTask);
    }

    public BitmapDownloadTask getDownloadTask() {
        return taskWeakReference.get();
    }

    private static boolean cancelPotentialDownload(String url, ImageView imageView) {
        BitmapDownloadTask task = getDownloadTask(imageView);
        if (task == null) {
            return true;
        }

        if (url.equals(task.url)) {
            return false;
        }

        task.cancel(true);
        return true;
    }

    private static BitmapDownloadTask getDownloadTask(ImageView imageView) {
        if (imageView == null) {
            return null;
        }
        Drawable drawable = imageView.getDrawable();
        if (! (drawable instanceof AsyncDrawable)) {
            return null;
        }
        AsyncDrawable asyncDrawable = (AsyncDrawable)drawable;
        return asyncDrawable.getDownloadTask();
    }


    private static class BitmapDownloadTask extends AsyncTask<Void, Void, Bitmap> {
        public String url;
        private final WeakReference<ImageView> imageViewWeakReference;

        public BitmapDownloadTask(ImageView imageView) {
            imageViewWeakReference = new WeakReference<ImageView>(imageView);
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            return downloadBitmap(url);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                return;
            }

            if (imageViewWeakReference != null) {
                ImageView imageView = imageViewWeakReference.get();
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }

        private Bitmap downloadBitmap(String url) {
            // url = "http://192.168.0.8:8000/gallery/DSC06152.JPG?size=medium";
            url = url + "?size=medium";

            final AndroidHttpClient client = AndroidHttpClient.newInstance("Android");
            final HttpGet getRequest = new HttpGet(url);

            try {
                HttpResponse response = client.execute(getRequest);
                final int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    Log.w(IndexPage.TAG, "Error " + statusCode + " while retrieving bitmap from " + url);
                    return null;
                }

                final HttpEntity entity = response.getEntity();
                if (entity == null) {
                    Log.w(IndexPage.TAG, "Error failed to get entity");
                    return null;
                }

                InputStream inputStream = null;
                try {
                    inputStream = entity.getContent();
                    final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    Log.d(IndexPage.TAG, "task: " + this + ", download finished: " + url);
                    return bitmap;
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    entity.consumeContent();
                }
            } catch (Exception e) {
                // Could provide a more explicit error message for IOException or IllegalStateException
                getRequest.abort();
                Log.w(IndexPage.TAG, "Error while retrieving bitmap from " + url, e);
            } finally {
                if (client != null) {
                    client.close();
                }
            }

            Log.w(IndexPage.TAG, "Error failed to download bitmap: " + url);
            return null;
        }
    }
}
