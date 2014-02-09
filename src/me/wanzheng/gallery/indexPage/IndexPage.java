package me.wanzheng.gallery.indexPage;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import me.wanzheng.gallery.FileEntry;
import me.wanzheng.gallery.GalleryPreference;
import me.wanzheng.gallery.R;
import me.wanzheng.gallery.detailPage.DetailFragment;
import me.wanzheng.gallery.detailPage.DetailPage;

import java.util.List;

public class IndexPage extends Activity implements LoaderManager.LoaderCallbacks<List<FileEntry>>,AdapterView.OnItemClickListener {
    private DirectoryAdapter adapter;
    private EditText urlView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_page);

        urlView = (EditText)findViewById(R.id.url);
        final SharedPreferences preferences = getSharedPreferences(GalleryPreference.NAME_PREFERENCE, MODE_PRIVATE);
        urlView.setText(preferences.getString(GalleryPreference.PREFERENCE_URL, "http://192.168.0.8:8000/gallery"));

        final GridView gridView = (GridView) findViewById(R.id.gridView);
        adapter = new DirectoryAdapter(this);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

        final Loader<List<FileEntry>> loader = getLoaderManager().initLoader(0, null, this);

        loader.forceLoad(); // TODO: 为什么没有自动load?

//        {
//            FileEntry entry;
//            entry = new FileEntry("http://192.168.0.8:8000/gallery/", "DSC06152.JPG");
//            adapter.add(entry);
//            entry = new FileEntry("http://192.168.0.8:8000/gallery/", "DSC06146.JPG");
//            adapter.add(entry);
//            entry = new FileEntry("http://192.168.0.8:8000/gallery/", "DSC06007.JPG");
//            adapter.add(entry);
//            entry = new FileEntry("http://192.168.0.8:8000/gallery/", "DSC05999.JPG");
//            adapter.add(entry);
//            entry = new FileEntry("http://192.168.0.8:8000/gallery/", "DSC05988.JPG");
//            adapter.add(entry);
//            entry = new FileEntry("http://192.168.0.8:8000/gallery/", "DSC05979.JPG");
//            adapter.add(entry);
//            entry = new FileEntry("http://192.168.0.8:8000/gallery/", "DSC05975.JPG");
//            adapter.add(entry);
//        }

        final Button goButton = (Button)findViewById(R.id.go);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DirectoryLoader loader = (DirectoryLoader) getLoaderManager().restartLoader(0, null, IndexPage.this);
                loader.forceLoad();

                final SharedPreferences preferences = getSharedPreferences(GalleryPreference.NAME_PREFERENCE, MODE_PRIVATE);
                preferences.edit().putString(GalleryPreference.PREFERENCE_URL, loader.url).commit();
            }
        });
    }

    @Override
    public Loader<List<FileEntry>> onCreateLoader(int id, Bundle args) {
        DirectoryLoader loader = new DirectoryLoader(this);
        loader.url = urlView.getText().toString();
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<FileEntry>> loader, List<FileEntry> data) {
        adapter.clear();
        if (data != null) {
            adapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<FileEntry>> loader) {
        adapter.clear();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final FileEntry entry = adapter.getItem(position);
        final String url = entry.baseDir + entry.filename;
        final Intent intent = new Intent(this, DetailPage.class);
        intent.putExtra(DetailFragment.IMAGE_DATA_EXTRA, url);
        startActivity(intent);
    }
}
