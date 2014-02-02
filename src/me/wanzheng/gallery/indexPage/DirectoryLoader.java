package me.wanzheng.gallery.indexPage;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.util.JsonReader;
import android.util.Log;
import me.wanzheng.gallery.FileEntry;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 18/1/14 by cos
 */
public class DirectoryLoader extends AsyncTaskLoader<List<FileEntry>> {
    public String url;
    public final AndroidHttpClient client = AndroidHttpClient.newInstance("gallery");

    public DirectoryLoader(Context context) {
        super(context);
    }

    @Override
    public List<FileEntry> loadInBackground() {
        final HttpUriRequest request = new HttpGet(url);
        try {
            final HttpResponse response = client.execute(request);
            final JsonReader reader = new JsonReader(new InputStreamReader(response.getEntity().getContent()));

            final ArrayList<FileEntry> list = new ArrayList<FileEntry>();
            reader.beginArray();
            while (reader.hasNext()) {
                reader.beginObject();
                while (reader.hasNext()) {
                    String name = reader.nextName();
                    if (name.equals("Url")) {
                        String filename = reader.nextString();
                        FileEntry entry = new FileEntry(url, filename.substring(9));
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
            Log.e(IndexPage.TAG, "Failed to load " + url + ": " + e);
            return null;
        }
    }
}
