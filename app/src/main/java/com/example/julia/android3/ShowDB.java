package com.example.julia.android3;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Lawrence on 2015-03-29.
 */
public class ShowDB extends ListActivity{

    private Context             myCon;
    private PhotoDataSource     pds;
    private PhotoData           db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_db);

        myCon   = this;
        pds     = new PhotoDataSource(myCon);

        try {
            pds.open();
        } catch (SQLException e) {
            Toast.makeText(getApplicationContext(), "failed to start db", Toast.LENGTH_SHORT).show();
        }

        List<PhotoData> pdl = pds.getAllPics();
        ArrayAdapter<PhotoData> adapt = new ArrayAdapter<PhotoData>(this, android.R.layout.simple_list_item_1, pdl);
        setListAdapter(adapt);

    }

    @Override
    protected void onResume() {
        try {
            pds.open();
        } catch (SQLException e) {
            Toast.makeText(getApplicationContext(), "failed to start db", Toast.LENGTH_SHORT).show();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        pds.close();
        super.onPause();
    }

}
