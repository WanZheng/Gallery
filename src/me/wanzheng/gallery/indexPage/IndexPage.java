package me.wanzheng.gallery.indexPage;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.GridView;
import me.wanzheng.gallery.FileEntry;
import me.wanzheng.gallery.R;

import java.util.List;

public class IndexPage extends Activity implements LoaderManager.LoaderCallbacks<List<FileEntry>>
{
    public static final String TAG = "Gallery";

    private DirectoryAdapter adapter;
    private EditText urlView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        urlView = (EditText)findViewById(R.id.url);

        final GridView gridView = (GridView) findViewById(R.id.gridView);
        adapter = new DirectoryAdapter(this);
        gridView.setAdapter(adapter);

        Loader<List<FileEntry>> loader = getLoaderManager().initLoader(0, null, this);

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

}
