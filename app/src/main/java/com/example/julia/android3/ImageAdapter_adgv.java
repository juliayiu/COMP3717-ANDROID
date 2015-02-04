package com.example.julia.android3;

/**
 * Created by Julia on 2015-02-04.
 */
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by Julia on 2015-02-04.
 */
public class ImageAdapter_adgv extends BaseAdapter {
    private Context mContext;

    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.groot, R.drawable.groot,
            R.drawable.groot, R.drawable.groot,
            R.drawable.groot, R.drawable.groot,
    };

    // Constructor
    public ImageAdapter_adgv(Context c) {
        mContext = c;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageButton imageButton;
        imageButton = new ImageButton(mContext);
        imageButton.setImageResource(mThumbIds[position]);
        imageButton.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageButton.setLayoutParams(new GridView.LayoutParams(500, 500));

        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(v.getContext(), PhotoDetails.class);
                mContext.startActivity(intent);
            }
        });

        return imageButton;

    }

}
