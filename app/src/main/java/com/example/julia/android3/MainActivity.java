package com.example.julia.android3;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;


public class MainActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        handleIntent(getIntent());

        // GridView Settings
        GridView gridView = (GridView) findViewById(R.id.gridview);

        // Instance of ImageAdapter Class
        gridView.setAdapter(new ImageAdapter_mgv(this));
    }

    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        //searchView.setSearchableInfo(
        //      searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}

        // When Add Album action menu item is clicked
        if ( id == R.id.addalbum ) {
            // Create Intent for Adding Activity
            Intent addAlbIntent = new Intent(this, AddPhoto.class);
            // Start Add Album Activitys
            startActivity(addAlbIntent);
            return true;
        } else if( id == R.id.search ) {
            Intent showDB = new Intent(this, ShowDB.class);
            startActivity(showDB);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addAlbum(final View view) {
        Intent intent = new Intent(MainActivity.this, AddPhoto.class);
        startActivity(intent);
    }

    public void albumDetails(final View view) {
        Intent intent = new Intent(MainActivity.this, PhotoDetails.class);
        startActivity(intent);
    }
}
