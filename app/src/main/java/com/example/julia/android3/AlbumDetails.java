package com.example.julia.android3;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.GridView;


public class AlbumDetails extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_details);

        //handleIntent(getIntent());

        // GridView Settings
        GridView gridView = (GridView) findViewById(R.id.gridview);

        // Instance of ImageAdapter Class
        gridView.setAdapter(new ImageAdapter_adgv(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_album_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // When Add Album action menu item is clicked
        if (id == R.id.addalbum){
            // Create Intent for Adding Activity
            Intent addAlbIntent = new Intent (this, AddPhoto.class);
            // Start Add Album Activity
            startActivity(addAlbIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void homepage(final View view) {
        Intent intent = new Intent(AlbumDetails.this, MainActivity.class);
        startActivity(intent);
    }

    public void photoDetail(final View view) {
        Intent intent = new Intent(AlbumDetails.this, PhotoDetails.class);
        startActivity(intent);
    }

}
