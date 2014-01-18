package me.wanzheng.gallery;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class Gallery extends Activity
{
    private ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        GridView gridView = (GridView)findViewById(R.id.gridView);
        adapter = new ArrayAdapter<String>(this, R.layout.text_node, R.id.text);
        gridView.setAdapter(adapter);

        for (int i=0; i<10; i++) {
            adapter.add("123");
        }
    }
}
