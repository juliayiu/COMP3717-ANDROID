package com.example.julia.android3;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();

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

        return super.onOptionsItemSelected(item);
    }

    public class SearchResultsActivity extends Activity {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            handleIntent(getIntent());
        }

        @Override
        protected void onNewIntent(Intent intent) {
            handleIntent(intent);
        }

        private void handleIntent(Intent intent) {

            if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
                String query = intent.getStringExtra(SearchManager.QUERY);
                //use the query to search your data somehow
            }
        }
    }

    public void albumPressed(final View view){
        Toast.makeText(getApplication(), "Yiu pressed on an album", Toast.LENGTH_SHORT).show();
    }

    public void bottomPressed(final View view){
        Toast.makeText(getApplication(), "Thanks for pressing me!", Toast.LENGTH_SHORT).show();
    }

    public void newAlbum(final View view){
        Toast.makeText(getApplication(), "Add an Album!", Toast.LENGTH_SHORT).show();
    }

    public void nextActivity(final View view) {
        Intent intent = new Intent(MainActivity.this, AddAlbum.class);
        startActivity(intent);
    }
}
