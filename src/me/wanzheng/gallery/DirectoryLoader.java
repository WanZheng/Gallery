package me.wanzheng.gallery;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.util.JsonReader;
import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 18/1/14 by cos
 */
public class DirectoryLoader extends AsyncTaskLoader<List<FileEntry>> {
    public String url;
    public AndroidHttpClient client = AndroidHttpClient.newInstance("gallery");

    public DirectoryLoader(Context context) {
        super(context);
    }

    @Override
    public List<FileEntry> loadInBackground() {
        Log.d(Gallery.TAG, "loadInBackground: " + url);

        HttpUriRequest request = new HttpGet(url);
        try {
            HttpResponse response = client.execute(request);
            JsonReader reader = new JsonReader(new InputStreamReader(response.getEntity().getContent()));

            ArrayList<FileEntry> list = new ArrayList<FileEntry>();
            reader.beginArray();
            while (reader.hasNext()) {
                reader.beginObject();
                while (reader.hasNext()) {
                    String name = reader.nextName();
                    if (name.equals("Url")) {
                        FileEntry entry = new FileEntry();
                        entry.baseDir = url;
                        entry.filename = reader.nextString();
                        entry.isDir = false;
                        list.add(entry);
                    }else{
                        reader.skipValue();
                    }
                }
                reader.endObject();
            }
            reader.endArray();

            reader.close();
            return list;

        } catch (IOException e) {
            Log.e(Gallery.TAG, "Failed to load " + url + ": " + e);
            return null;
        }
    }
}
