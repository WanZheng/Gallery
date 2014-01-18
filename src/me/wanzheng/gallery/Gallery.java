package me.wanzheng.gallery;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;

import java.util.List;

public class Gallery extends Activity implements LoaderManager.LoaderCallbacks<List<FileEntry>>
{
    public static final String TAG = "Gallery";

    private ArrayAdapter<FileEntry> adapter;
    private EditText urlView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        urlView = (EditText)findViewById(R.id.url);

        GridView gridView = (GridView)findViewById(R.id.gridView);
        adapter = new ArrayAdapter<FileEntry>(this, R.layout.text_node, R.id.text);
        gridView.setAdapter(adapter);

        Loader<List<FileEntry>> loader = getLoaderManager().initLoader(0, null, this);

        loader.forceLoad(); // TODO: 为什么没有自动load?
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
