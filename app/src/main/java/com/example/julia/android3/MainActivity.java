package com.example.julia.android3;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.app.SearchManager;
import android.content.Context;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.SearchView;




public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handleIntent(getIntent());
    }

    protected void onNewIntent(Intent intent){
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
        if (id == R.id.addalbum){
            // Create Intent for Adding Activity
            Intent addAlbIntent = new Intent (this, AddAlbum.class);
            // Start Add Album Activity
            startActivity(addAlbIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addAlbum(final View view) {
        Intent intent = new Intent(MainActivity.this, AddPhoto.class);
        startActivity(intent);
    }

    public void albumDetails(final View view) {
        Intent intent = new Intent(MainActivity.this, AlbumDetails.class);
        startActivity(intent);
    }
}
