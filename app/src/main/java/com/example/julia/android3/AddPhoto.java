package com.example.julia.android3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;


public class AddPhoto extends ActionBarActivity {
    static final int REQUEST_CAMERA = 200;
    static final int SELECT_FILE = 201;
    private ImageView mImage;
    private PhotoDataSource photosource;
    String titleVal, picVal, descVal = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        mImage = (ImageView) findViewById(R.id.addPhoto);

        ((ImageView)findViewById(R.id.addPhoto)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        photosource = new PhotoDataSource(this);
        try {
            photosource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cancelAdd(final View view) {
        Intent intent = new Intent(AddPhoto.this, MainActivity.class);
        startActivity(intent);
    }

    //Displays a dialog box with Take Photo, Choose from Library, and Cancel as the options
    //Selecting a certain option will create an intent which will open the camera function or image gallery
    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(AddPhoto.this);
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //File f = new File(android.os.Environment
                    //.getExternalStorageDirectory(), "temp.jpg");
                    //intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //If the camera is selected, a new jpeg will be created in the image gallery
            if (requestCode == REQUEST_CAMERA) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                int nh = (int) ( thumbnail.getHeight() * (512.0 / thumbnail.getWidth()) );
                thumbnail = Bitmap.createScaledBitmap(thumbnail, 512, nh, true);
                mImage.setImageBitmap(thumbnail);

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

                Uri tempUri = getImageUri(this, thumbnail);
                String tempPath = getPath(tempUri, AddPhoto.this);
                picVal = tempPath;

                File file = new File(Environment.getExternalStorageDirectory().toString()+File.separator + "image");
                try {
                    file.createNewFile();
                    FileOutputStream fo = new FileOutputStream(file);
                    //5
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                //Grabs the selected image from the gallery and formats it to a smaller size. If the orientation is incorrect,
                //the method rotateBitmap will fix it.
            } else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();
                String tempPath = getPath(selectedImageUri, AddPhoto.this);
                picVal = tempPath;
                BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
                Bitmap thumbnail = BitmapFactory.decodeFile(tempPath, btmapOptions);
                int nh = (int) ( thumbnail.getHeight() * (512.0 / thumbnail.getWidth()) );
                thumbnail = Bitmap.createScaledBitmap(thumbnail, 512, nh, true);

                //ExifInterface gets the selected image's orientation
                ExifInterface exif = null;
                try {
                    exif = new ExifInterface(tempPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);

                thumbnail = rotateBitmap(thumbnail, orientation);

                //Set the image in the imageView
                mImage.setImageBitmap(thumbnail);
            }
        }
    }

    //Get file path
    public String getPath(Uri uri, Activity activity) {
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

    //Corrects the orientation depending on the orientation value in the method's parameter
    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            int nh = (int) ( bmRotated.getHeight() * (512.0 / bmRotated.getWidth()) );
            bmRotated = Bitmap.createScaledBitmap(bmRotated, 512, nh, true);
            bitmap.recycle();
            return bmRotated;
        }
        catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public void addToDb(View view){
        EditText title = (EditText)findViewById(R.id.titleInput);
        ImageView image = (ImageView)findViewById(R.id.imageView);
        EditText desc = (EditText)findViewById(R.id.photoDesc);
        TextView box1 = (TextView)findViewById(R.id.textView);

        titleVal = title.getText().toString();
        descVal = desc.getText().toString();

        PhotoData photo = photosource.addPic(titleVal, picVal, descVal);
        box1.setText("The image path after picking a image: " + picVal);
        //title.setText(photo.getTitle());
        //image.setText(photo.getImage());
        //desc.setText(photo.getDesc());
        Toast.makeText(this, photo.getTitle() + ' ' + photo.getImage() + ' ' + photo.getDesc(), Toast.LENGTH_SHORT).show();
    }
}
